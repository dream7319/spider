package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author lierl
 * @create 2017-08-30 17:42
 **/
public class Cto51Spider implements PageProcessor{

	static String time = String.valueOf(System.currentTimeMillis()).substring(0,10);

	private Site site = new Site().setRetryTimes(3).setSleepTime(100)
			//添加cookie之前一定要先设置主机地址，否则cookie信息不生效
			.setDomain("blog.51cto.com")
			//添加抓包获取的cookie信息
			.addCookie("www51cto","23B0AE4105E8E2B136A116C4D1AD5F62rhfA")
			.addCookie("pub_smile","1D1D0D9")
			.addCookie("Cto_lvt_d2cb9d6ccefce8d1e89582c2a2a24cc0","1493192293,1493193329,1493704573,1495440140")
			.addCookie("UM_distinctid","15dcf1066e710b-0cb9aef9997d5-414a0229-100200-15dcf1066e8117")
			.addCookie("Hm_lvt_110fc9b2e1cae4d110b7959ee4f27e3b","1502417545")
			.addCookie("acw_tc","AQAAAEL7NmH49wcA0tV8e1oUx/RrzQKx")
			.addCookie("_csrf","8c663fd4a25d7b1205a2fb4656826f8eaed11e39bbaf76656f1efd49f99bb255a%3A2%3A%7Bi%3A0%3Bs%3A5%3A%22_csrf%22%3Bi%3A1%3Bs%3A32%3A%22IRHNwSNr4edAhZZeOb4tJOz1iIGJmKy6%22%3B%7D")
			.addCookie("acw_tc","59a6850c|ec005ab978f76a56742a7b4128e08499")
			.addCookie("pub_cookietime","0")
			.addCookie("_ourplusFirstTime","117-8-30-17-47-4")
			.addCookie("_ourplusReturnCount","1")
			.addCookie("_ourplusReturnTime","117-8-30-17-47-4")
			.addCookie("bdshare_firstime","1504086424285")
			.addCookie("Cto_lvt_","1504086425")
			.addCookie("Cto_lpvt_","1504086425")
			.addCookie("Cto_uid_","3793066")
			.addCookie("blog_new","3qm3nfmmmrvbioi32mnoup76f6")
			.addCookie("lastvisit","0%091504143738%09%2Fmod%2Fedu_void.php%3F")
			.addCookie("Hm_lvt_2283d46608159c3b39fc9f1178809c21","1503643431,1504085260,1504085272,1504144962")
			.addCookie("Hm_lpvt_2283d46608159c3b39fc9f1178809c21","1504144962")
			//添加请求头，有些网站会根据请求头判断该请求是由浏览器发起还是由爬虫发起的
			.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36")
			.addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
			.addHeader("Accept-Encoding", "gzip, deflate, sdch")
			.addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
			.addHeader("Connection", "keep-alive")
			.addHeader("Referer", "http://blog.51cto.com/");

	@Override
	public void process(Page page) {
		System.out.println(page.getHtml().xpath("//*ul[@id='aList']").toString());
	}
	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new Cto51Spider())
				// 从"http://www.imooc.com/user/setprofile"开始抓
				.addUrl("http://blog.51cto.com/").addPipeline(new ConsolePipeline())
				// 开启5个线程抓取
				.thread(1)
				// 启动爬虫
				.run();

		System.out.println(time);
	}
}
