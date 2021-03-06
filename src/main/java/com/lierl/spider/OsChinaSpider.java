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
 * @create 2017-08-29 16:48
 **/
public class OsChinaSpider implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	public static final String URL_LIST = "https://www.oschina.net/blog/action/ajax/get_more_recommend_blog\\?classification=0&p=\\d{1,3}";

	public static int pageNum = 1;

	static List<Blog> datas = Lists.newArrayList();


	@Override
	public void process(Page page) {
		if (page.getUrl().regex("^https://www\\.oschina\\.net/blog$").match()) {
			try {
				List<String> urls = page.getHtml().xpath("//*[@id=\"topsOfRecommend\"]//div[@class='box']/div[@class='box-aw']/header/a/@href").all();
				page.addTargetRequests(urls);
				pageNum++;
				page.addTargetRequest("https://www.oschina.net/blog/action/ajax/get_more_recommend_blog?classification=0&p=2");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if(page.getUrl().regex(URL_LIST).match() && pageNum <= 417){
			try {
				List<String> urls = page.getHtml().xpath("//*div[@class='box']/div[@class='box-aw']/header/a/@href").all();
				page.addTargetRequests(urls);
				page.addTargetRequest("https://www.oschina.net/blog/action/ajax/get_more_recommend_blog?classification=0&p=" + ++pageNum);
				System.out.println("CurrPage:" + pageNum + "#######################################");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else {

			Blog blog = new Blog();
			// 获取页面需要的内容,这里只取了标题，其他信息同理。
			Selectable article = page.getHtml().xpath("//div[@class='blog blog-article']");
			String title = article.xpath("//div[@class='blog-heading']/div[@class='title']/text()").get();
			blog.setTitle(title);
			Selectable titleTag = article.xpath("//div[@class='blog-heading']/div[@class='info-opr']/div[@class='layout-column']");
			String author = titleTag.xpath("//div[@class='user-info']/div[@class='name']/a/text()").get();
			blog.setAuthor(author);
			String read = titleTag.xpath("//div[@class='data-info text-gary']/ul/li[@class='read']/span/text()").get();
			blog.setReadNum(Integer.valueOf(read.trim()));
			String vote = titleTag.xpath("//div[@class='data-info text-gary']/ul/li[@class='vote']/span/text()").get();
			blog.setVoteNum(Integer.valueOf(vote.trim()));
			String publishTime = titleTag.xpath("//div[@class='data-info text-gary']/ul/li[@class='time']/span/text()").get();
			blog.setPublishTime(publishTime);
			String collector = titleTag.xpath("//div[@class='data-info text-gary']/ul/li[@class='favor']/span/text()").get();
			blog.setCollecotrNum(Integer.valueOf(collector.trim()));
			String comment = titleTag.xpath("//div[@class='data-info text-gary']/ul/li[@class='comment']/a/span/text()").get();
			blog.setComment(Integer.valueOf(comment.trim()));
			String words = article.xpath("//span[@id='Words']/text()").get();
			blog.setWords(Integer.valueOf(words.trim()));
			String tags = Joiner.on(",").join(article.xpath("//div[@class='tags']/span[@class='tag']/a/text()").all());
			blog.setTag(tags);
			String address = article.xpath("//div[@class='user-card']").css("div.opus-info").xpath("//span[@class=card-address]/text()").get();
			blog.setAddress(address);
			String fans = article.xpath("//div[@class='user-card']").css("div.opus-opr").xpath("//div[1]/span/text()").get();
			blog.setFans(Integer.valueOf(fans.trim()));
			String blogs = article.xpath("//div[@class='user-card']").css("div.opus-opr").xpath("//div[2]/span/text()").get();
			blog.setBlogNum(Integer.valueOf(blogs.trim()));
			String blogsWord = article.xpath("//div[@class='user-card']").css("div.opus-opr").xpath("//div[3]/span/text()").get();
			blog.setBlogWords(Integer.valueOf(blogsWord.trim()));
			//			System.out.println("博客内容："+article.xpath("//div[@id='blogBody']/div[@class='BlogContent clearfix']/tidyText()").get());
			String url = article.css("div.back-list").xpath("//a[2]/@href").get();
			blog.setUrl(url);
			datas.add(blog);
			System.out.println(blog.toString());
		}

	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new OsChinaSpider()).addUrl("https://www.oschina.net/blog").thread(5).run();

		System.out.println("搜集总数："+datas.size());
	}
}
