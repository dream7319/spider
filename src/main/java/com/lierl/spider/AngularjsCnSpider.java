package com.lierl.spider;

import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import java.util.List;

/**
 * @author lierl
 * @create 2017-08-31 9:31
 **/
public class AngularjsCnSpider implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	private static final String LIST_URL = "http://www\\.angularjs\\.cn/api/article/latest.*";
	public static int pageNum = 1;

	@Override
	public void process(Page page) {

		if (page.getUrl().regex(LIST_URL).match() && pageNum <=109) {
			List<String> ids = new JsonPathSelector("$.data[*]._id").selectList(page.getRawText());
			if (CollectionUtils.isNotEmpty(ids)) {
				for (String id : ids) {
					page.addTargetRequest("http://www.angularjs.cn/api/article/" + id);
				}
			}
			pageNum++;
			page.addTargetRequest("http://www.angularjs.cn/api/article/latest?p="+ pageNum +"&s=20");
		} else {
//			page.putField("title", new JsonPathSelector("$.data.title").select(page.getRawText()));
//			page.putField("content", new JsonPathSelector("$.data.content").select(page.getRawText()));
			System.out.println(new JsonPathSelector("$.data").select(page.getRawText()));
		}
	}


	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new AngularjsCnSpider()).addUrl("http://www.angularjs.cn/api/article/latest?p=1&s=20").thread(3).run();
	}
}
