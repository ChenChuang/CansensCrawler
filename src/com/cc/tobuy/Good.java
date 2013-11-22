package com.cc.tobuy;

public class Good {
	public String name;
	public String id;
	public String bn;
	public String unit;
	public String price;
	public String desc;
	public String img_url;
	
	public Good(String name, String id, String bn, String unit, String price, String desc, String img_url){
		this.name = name;
		this.id = id;
		this.bn = bn;
		this.unit = unit;
		this.price = price;
		this.desc = desc;
		this.img_url = img_url;
	}
}
