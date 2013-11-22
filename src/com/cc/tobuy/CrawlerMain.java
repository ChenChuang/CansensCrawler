package com.cc.tobuy;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class CrawlerMain {
	
	static int from = 0;
	static int to = 0;
	static String url_prefix = "";
	static String imgs_dir = "";
	
	static String db_url = "j";
	static String db_driver = "";
	static String db_user = "";
	static String db_psw = "";
	static String db_dbname = "";
	
	public static void main(String[] args) {
		//Test();
		
		loadProperties("cansens.properties");
		
		int goods_succeed = 0;
		String url = "";
		
		Crawler crawler = new Crawler();
		
		for (int i=from; i<=to; i++) {
			url = url_prefix + i;
			System.out.print(i + ": ");
			try {
				DBManager.getInstance().storeGood(crawler.getGoodFrom(url));
				System.out.print("succeed.");
				goods_succeed ++;
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.print(e.getMessage());
			} 
			System.out.print("\r\n");
		}
		
		System.out.println("-----------------------------------------");
		System.out.println("goods_from: " + from + " to: " + to);
		System.out.println("goods_succeed: " + goods_succeed);
	}
	
	public static void Test(){
		Crawler mCrawler = new Crawler();
		try {
			mCrawler.Test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void loadProperties(String filepath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filepath));
			props.load(in);
			from = Integer.parseInt(props.getProperty ("from"));
			to = Integer.parseInt(props.getProperty ("to"));
			imgs_dir = props.getProperty ("imgs_dir");
			url_prefix = props.getProperty ("url_prefix");
			
			db_url = props.getProperty ("db_url");
			db_driver = props.getProperty ("db_driver");
			db_user = props.getProperty ("db_user");
			db_psw = props.getProperty ("db_psw");
			db_dbname = props.getProperty ("db_dbname");
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
}
