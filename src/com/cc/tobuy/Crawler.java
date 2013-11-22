package com.cc.tobuy;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
	
	public Good getGoodFrom(String url) throws MalformedURLException, IOException{
				
		Document doc = Jsoup.parse((new URL(url)), 3*1000);
        Element goods_action_form = doc.getElementsByClass("goods-action").first();
        
        Element goodsname_h = goods_action_form.getElementsByClass("goodsname").first();
        String goodsname = goodsname_h.ownText();
        
        Element goodsprops_clearfix = goods_action_form.select("ul").first();        
        Elements goodsprops_clearfix_ul = goodsprops_clearfix.select("li");        
        Element goodsid_li = goodsprops_clearfix_ul.get(0);
        String goodsid = goodsid_li.ownText();
        
        Element goodsBn_li = goodsprops_clearfix_ul.get(1);
        Element goodsBn_span = goodsBn_li.getElementById("goodsBn");
        String goodsbn = goodsBn_span.ownText();
        
        Element goodsunit_li = goodsprops_clearfix_ul.get(2);
        String goodsunit = goodsunit_li.ownText();
        
        Element goods_price_list = goods_action_form.select("ul").get(1);
        Elements goods_price_list_ul = goods_price_list.select("li");
        Element goodsprice_li = goods_price_list_ul.get(1);
        Element goodsprice_span = goodsprice_li.getElementsByClass("price1").first();
        String goodsprice = goodsprice_span.ownText();
        
        Element goods_intro_div = doc.getElementById("goods-intro");
        Element goodsdesc_font = goods_intro_div.select("font").first();
        String goodsdesc = goodsdesc_font.ownText();
        
        Element goodsimg_img = goods_intro_div.select("img").get(2);
        String goodsimg_url = goodsimg_img.attr("src");
        
        return (new Good(goodsname, goodsid, goodsbn, goodsunit, goodsprice, goodsdesc, goodsimg_url));
	}
	
	public void Test() throws Exception{
		URL url = new URL("http://www.cansens.com/?product-2999.html");
		System.out.println("Fetching " + url.toExternalForm());   
		   
        Document doc = Jsoup.parse(url, 10*1000);
        Element goods_action_form = doc.getElementsByClass("goods-action").first();
        
        Element goodsname_h = goods_action_form.getElementsByClass("goodsname").first();
        String goodsname = goodsname_h.ownText();
        
        Element goodsprops_clearfix = goods_action_form.select("ul").first();        
        Elements goodsprops_clearfix_ul = goodsprops_clearfix.select("li");        
        Element goodsid_li = goodsprops_clearfix_ul.get(0);
        String goodsid = goodsid_li.ownText();
        
        Element goodsBn_li = goodsprops_clearfix_ul.get(1);
        Element goodsBn_span = goodsBn_li.getElementById("goodsBn");
        String goodsbn = goodsBn_span.ownText();
        
        Element goodsunit_li = goodsprops_clearfix_ul.get(2);
        String goodsunit = goodsunit_li.ownText();
        
        Element goods_price_list = goods_action_form.select("ul").get(1);
        Elements goods_price_list_ul = goods_price_list.select("li");
        Element goodsprice_li = goods_price_list_ul.get(1);
        Element goodsprice_span = goodsprice_li.getElementsByClass("price1").first();
        String goodsprice = goodsprice_span.ownText();
        
        Element goods_intro_div = doc.getElementById("goods-intro");
        Element goodsdesc_font = goods_intro_div.select("font").first();
        String goodsdesc = goodsdesc_font.ownText();
        
        Element goodsimg_img = goods_intro_div.select("img").get(2);
        String goodsimg_url = goodsimg_img.attr("src");
        
        System.out.println(goodsname);
        System.out.println(goodsid);
        System.out.println(goodsbn);
        System.out.println(goodsunit);
        System.out.println(goodsprice);
        System.out.println(goodsdesc);
        System.out.println(goodsimg_url);
	}

}
