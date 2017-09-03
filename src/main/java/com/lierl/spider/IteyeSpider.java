package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author lierl
 * @create 2017-09-01 9:56
 **/
public class IteyeSpider implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	public int pageNum = 1;

	private int totalPageNum = 0;

	public String blogPageUrl = "";

	public String homeUrl;

	public IteyeSpider(){}

	public IteyeSpider(String homeUrl,String blogPageUrl){
		this.blogPageUrl = blogPageUrl;
		this.homeUrl = homeUrl;
	}

	private static int count = 0;


	@Override
	public void process(Page page) {
		if(page.getUrl().regex("^"+homeUrl+"$").match()){

			List<String> uris = page.getHtml().xpath("//*div[@id='index_main']/div[@class='blog clearfix']").css("div.content").xpath("//h3/a/@href").all();

			page.addTargetRequests(uris);

			List<String> as = page.getHtml().xpath("//*div[@id='index_main']/div[@class='pagination']/a/text()").all();
			if(as != null && !as.isEmpty()){
				int size = as.size() - 1;
				String num = page.getHtml().xpath("//*div[@id='index_main']/div[@class='pagination']/a["+size+"]/text()").toString();
				totalPageNum = Integer.valueOf(num);
			}else{
				totalPageNum = 1;
			}

			pageNum++;
			page.addTargetRequest(homeUrl+"?page=2");
		}else if(page.getUrl().regex(blogPageUrl).match() && pageNum <= totalPageNum){
			List<String> uris = page.getHtml().xpath("//*div[@id='index_main']/div[@class='blog clearfix']").css("div.content").xpath("//h3/a/@href").all();
			page.addTargetRequests(uris);
			page.addTargetRequest(homeUrl+"?page="+ ++pageNum);
		}else{
			System.out.println(page.getHtml().xpath("//*[@id='main']").css("div.blog_title").xpath("//h3/a/text()").get());
			count++;
		}
	}


	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		String url = "http://www.iteye.com/blogs/tag/C";
		Spider.create(new IteyeSpider(url,url+"\\?page=\\d{1,4}")).setDownloader(new CustomerDownloader()).thread(5).addUrl(url).run();

		//http://www.iteye.com/blogs/tag/Java
		//http://www.iteye.com/blogs/tag/C
		//http://www.iteye.com/blogs/tag/C++
		//http://www.iteye.com/blogs/tag/SQL
		//http://www.iteye.com/blogs/tag/C%23
		//http://www.iteye.com/blogs/tag/XML
		//http://www.iteye.com/blogs/tag/HTML
		//http://www.iteye.com/blogs/tag/JavaScript
		//http://www.iteye.com/blogs/tag/.net
		//http://www.iteye.com/blogs/tag/Web
		//http://www.iteye.com/blogs/tag/工作
		//http://www.iteye.com/blogs/tag/Linux
		//http://www.iteye.com/blogs/tag/应用服务器
		//http://www.iteye.com/blogs/tag/Oracle
		//http://www.iteye.com/blogs/tag/Spring
		//http://www.iteye.com/blogs/tag/编程
		//http://www.iteye.com/blogs/tag/Windows
		//http://www.iteye.com/blogs/tag/JSP
		//http://www.iteye.com/blogs/tag/MySQL
		//http://www.iteye.com/blogs/tag/数据结构
		//http://www.iteye.com/blogs/tag/MySQL?page=1000

		System.out.println("总数："+count);
	}
}
