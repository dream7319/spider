package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author lierl
 * @create 2017-08-29 15:10
 **/
public class CsdnSpiderTest implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");


	public void process(Page page) {
		Selectable select = page.getHtml().xpath("//*[@id=\"topics\"]/div[@class=\"post\"]");
		System.out.println("标题："+select.xpath("//h1/a[@id=\"cb_post_title_url\"]/text()"));
		System.out.println("内容："+select.xpath("//div[@class=\"postBody\"]/div[@id=\"cnblogs_post_body\"]/tidyText()").toString());
	}

	public Site getSite() {
		return site;
	}
	public static void main(String[] args) {
		Spider.create(new CsdnSpiderTest()).addUrl("http://www.cnblogs.com/huanglei-/p/7071202.html").run();
	}
}
