package com.cc.tobuy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBManager {

	private static DBManager instance;
	private DBConnectionPool mDBConnectionPool;
	
    static synchronized public DBManager getInstance() 
    { 
    	if(instance == null){ 
    		try {
				instance = new DBManager();
			} catch (Exception e) {
				e.printStackTrace();
				instance = null;
			} 
    	}
    	return instance;
    }
    
	private DBManager() throws Exception {
		if(this.init() < 0){
			throw new Exception();
		}
	}
	
	private int init() {
		return this.createConnectionPool();
	}
	
    private int createConnectionPool(){
    	this.mDBConnectionPool = new DBConnectionPool(
    			CrawlerMain.db_url, 
    			CrawlerMain.db_driver, 
    			CrawlerMain.db_user, 
    			CrawlerMain.db_psw, 
    			CrawlerMain.db_dbname, 2);
    	if(this.mDBConnectionPool != null){
    		return 1;
    	}else{
    		return -1;
    	}
    }
	
	public void storeGood(Good good) throws SQLException, IOException{
		Connection conn = this.mDBConnectionPool.getConnection();
    	if(conn == null){
    		throw new SQLException("failed to connect db");
    	}
    	
	    String sql = "INSERT INTO goods"
	    		   + "(good_name, good_id, good_bn, good_unit, good_price, good_desc, good_img_url, good_img_filename) VALUES"
	    		   + "(?,?,?,?,?,?,?,?)";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setString(1, good.name);
	    ps.setString(2, good.id);
	    ps.setString(3, good.bn);
	    ps.setString(4, good.unit);
	    ps.setString(5, good.price);
	    ps.setString(6, good.desc);
	    ps.setString(7, good.img_url);
	    
	    String[] strs = good.img_url.split("\\.");
	    String img_filename = "img_" + good.bn + "." + strs[strs.length-1];
	    ps.setString(8, img_filename);
	    
	    if( ps.executeUpdate() < 0 ){
	    	throw new SQLException("failed to executeUpdate");
	    }
    	
    	this.mDBConnectionPool.freeConn(conn);
    	    	
    	this.saveFile(good.img_url, CrawlerMain.imgs_dir + img_filename);
	}
	
	public int saveFile(String url, String filepath) throws IOException {
		int bytesum = 0;
		int byteread = 0;

		URLConnection conn = (new URL(url)).openConnection();
		InputStream is = conn.getInputStream();
		FileOutputStream fs = new FileOutputStream(filepath);

		byte[] buffer = new byte[1204];
		while ((byteread = is.read(buffer)) != -1) {
			bytesum += byteread;
			fs.write(buffer, 0, byteread);
		}
		fs.close();
		return bytesum;
	}	
}

