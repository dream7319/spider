package com.lierl.spider;

import com.google.common.base.Joiner;
import com.lierl.spider.bean.Blog;
import org.assertj.core.util.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author lierl
 * @create 2017-08-29 17:22
 **/
public class OsChinaSpiderTest implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	public static final String URL_LIST = "https://www.oschina.net/blog/action/ajax/get_more_recommend_blog\\?classification=0&p=\\d{1,3}";

	public static int pageNum = 1;

	static List<String> homes = Lists.newArrayList();


	@Override
	public void process(Page page) {
		if (page.getUrl().regex("^https://www\\.oschina\\.net/blog$").match()) {
			try {
				List<String> urls = page.getHtml().xpath("//*[@id=\"topsOfRecommend\"]//div[@class='box item']/div[@class='box-fl']/a/@href").all();
				page.addTargetRequests(urls);
				pageNum++;
				page.addTargetRequest("https://www.oschina.net/blog/action/ajax/get_more_recommend_blog?classification=0&p=2");
			} catch (Exception e) {
				e.printStackTrace();
			}//417
		} else if(page.getUrl().regex(URL_LIST).match() && pageNum <=1 ){
			try {
				List<String> urls = page.getHtml().xpath("//*div[@class='box item']/div[@class='box-fl']/a/@href").all();
				page.addTargetRequests(urls);
				page.addTargetRequest("https://www.oschina.net/blog/action/ajax/get_more_recommend_blog?classification=0&p=" + ++pageNum);
				System.out.println("CurrPage:" + pageNum + "#######################################");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			String homeUrl = page.getUrl().toString();
			homes.add(homeUrl);
			PersonBlogOschina os = new PersonBlogOschina(homeUrl);
			Spider.create(os).addUrl(homeUrl).thread(5).run();
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new OsChinaSpiderTest()).addUrl("https://www.oschina.net/blog").thread(5).run();
//		for (int i = 0; i < homes.size(); i++) {
//			System.out.println(homes.get(i));
//		}
	}
}
