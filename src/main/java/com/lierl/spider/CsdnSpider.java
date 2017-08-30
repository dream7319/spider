package com.lierl.spider;

import org.assertj.core.util.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author lierl
 * @create 2017-08-29 14:14
 **/
public class CsdnSpider implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	public static final String URL_LIST = "https://www.cnblogs.com/sitehome/p/\\d{1,3}";

	public static int pageNum = 1;

	static List<Object> uris = Lists.newArrayList();

	@Override
	public void process(Page page) {
		if (page.getUrl().regex("^https://www\\.cnblogs\\.com$").match()) {
			try {
				List<String> urls = page.getHtml().xpath("//*[@id=\"post_list\"]/div/div[@class='post_item_body']/h3/a/@href").all();
				uris.addAll(urls);
				page.addTargetRequests(urls);
				pageNum++;
				page.addTargetRequest("https://www.cnblogs.com/sitehome/p/2");
				System.out.println("1111="+pageNum);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(page.getUrl().regex(URL_LIST).match() && pageNum <= 200){
			try {
				List<String> urls = page.getHtml().xpath("//*[@class='post_item']//div[@class='post_item_body']/h3/a/@href").all();
				uris.addAll(urls);
				page.addTargetRequests(urls);
				page.addTargetRequest("https://www.cnblogs.com/sitehome/p/" + ++pageNum);
				System.out.println("CurrPage:" + pageNum + "#######################################");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			// 获取页面需要的内容,这里只取了标题，其他信息同理。
			System.out.println("抓取的内容：" + page.getHtml().xpath("//a[@id='cb_post_title_url']/text()").get());
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new CsdnSpider()).addUrl("https://www.cnblogs.com").thread(3).run();

		for (int i = 0; i < uris.size(); i++) {
			System.out.println(uris.get(i));
		}
	}
}
