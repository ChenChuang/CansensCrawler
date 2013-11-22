package com.cc.tobuy;
 
import java.sql.Connection; 
import java.sql.DriverManager;
import java.util.ArrayList; 
import java.util.Iterator; 
/** 
* @author chenyanlin 
* 
*/ 
public class DBConnectionPool{  

	private ArrayList<Connection> freeConns = new ArrayList<Connection>();
	
	private int usedConnNum;
	private int maxConnNum;
		
	private String url;
	private String driver;
	private String user;
	private String password;
	private String dbname;

	public DBConnectionPool(String url, String driver, String user, String password, String dbname, int maxConnNum) { 
		this.url = url; 
		this.driver = driver; 
		this.user = user; 
		this.password = password;
		this.dbname = dbname; 
		this.maxConnNum = maxConnNum;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		this.releaseAll();
	}



	public synchronized void freeConn(Connection conn) {
		if(conn != null){
			this.freeConns.add(conn);
			this.usedConnNum --;
		}
	}
 
	public Connection getConnection(int initTimeout, int maxTry) {
		Connection conn = null;
		int i = 0;
		long timeout = initTimeout;
		while( i < maxTry ){
			conn = this.getConnection();
			if(conn != null){
				return conn;
			}
			i ++;
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
			timeout = timeout * 2;
			//System.out.println("timeout: " + timeout);
		}
		return conn;
	}
	
	public synchronized Connection getConnection() { 
		Connection conn = null;
		if(this.freeConns.size() > 0) 
		{ 
			conn = this.freeConns.get(0);
			this.freeConns.remove(0);
			if(conn != null){
				this.usedConnNum ++;
				return conn;
			}
		}
		if(this.usedConnNum < this.maxConnNum){ 
			conn = newConnection();
			if(conn != null){
				this.usedConnNum ++;
				return conn;
			}
		}
		return null;
	} 

	public synchronized int releaseAll() { 
		Iterator<Connection> conns = this.freeConns.iterator(); 
		while(conns.hasNext()){ 
			Connection con=(Connection)conns.next(); 
			try{ 
				con.close(); 
			}catch(Exception e){ 
				e.printStackTrace();
				return -1;
			}
		} 
		this.freeConns.clear();
		return 1;
	} 

	private Connection newConnection() { 
		try{ 
			Class.forName(this.driver).newInstance(); 
			return DriverManager.getConnection(this.url + this.dbname, this.user, this.password); 
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
