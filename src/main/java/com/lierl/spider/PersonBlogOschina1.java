package com.lierl.spider;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.lierl.spider.bean.Blog;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author lierl
 * @create 2017-08-31 13:35
 **/
public class PersonBlogOschina1 implements PageProcessor{

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
			.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

	public static final String URL_LIST = "https://my.oschina.net/yumg/blog\\?sort=time&p=\\d{1,4}";
	public static int pageNum = 1;

	public String blogPageUrl;

	public PersonBlogOschina1(){}

	private String homeUrl;

	private int totalPageNum = 0;

	static List<Blog> blogsList = Lists.newArrayList();

	public PersonBlogOschina1(String homeUrl){
		this.homeUrl = homeUrl;
		this.blogPageUrl = homeUrl+"\\?sort=time&p=\\d{1,4}";
	}

	@Override
	public void process(Page page) {
		if(page.getUrl().regex("^"+homeUrl+"$").match()){
			List<String> uris = page.getHtml().xpath("//*div[@id='list']").css("div.title").xpath("//a/@href").all();

			List<String> pages = page.getHtml().xpath("//*div[@id='list']").css("ul.paging").xpath("//li/a/text()").all();

			int size = pages.size() - 1;
			String num = page.getHtml().xpath("//*div[@id='list']").css("ul.paging").xpath("//li["+size+"]/a/text()").toString();

			totalPageNum = Integer.valueOf(num);

			page.addTargetRequests(uris);
			pageNum++;
			page.addTargetRequest(homeUrl+"?sort=time&p=2");
		}else if(page.getUrl().regex(blogPageUrl).match() && pageNum <= totalPageNum){
			List<String> uris = page.getHtml().xpath("//*div[@id='list']").css("div.title").xpath("//a/@href").all();
			page.addTargetRequests(uris);
			page.addTargetRequest(homeUrl+"?sort=time&p=" + ++pageNum);
		}else{
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
			blogsList.add(blog);
			System.out.println(blog.toString());
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new PersonBlogOschina1("https://my.oschina.net/OutOfMemory")).addUrl("https://my.oschina.net/OutOfMemory").thread(5).run();
		System.out.println("总数："+blogsList.size());
	}
}
