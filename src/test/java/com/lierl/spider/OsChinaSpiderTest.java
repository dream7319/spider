package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

/**
 * @author lierl
 * @create 2017-08-29 17:22
 **/
public class OsChinaSpiderTest implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");



	@Override
	public void process(Page page) {
		Selectable selects = page.getHtml();

		System.out.println(selects.toString());
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new OsChinaSpiderTest()).addUrl("https://my.oschina.net/zzjweb").run();
	}
}
