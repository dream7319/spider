package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author lierl
 * @create 2017-09-01 9:56
 **/
public class IteyeSpider implements PageProcessor{
	/**
	 * process the page, extract urls to fetch, extract the data and store
	 *
	 * @param page page
	 */
	@Override
	public void process(Page page) {

	}

	/**
	 * get the site settings
	 *
	 * @return site
	 * @see Site
	 */
	@Override
	public Site getSite() {
		return null;
	}

	public static void main(String[] args) {
		//http://www.iteye.com/blogs/tag/Java
	}
}
