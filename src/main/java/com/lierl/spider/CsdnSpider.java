package com.lierl.spider;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by lierl on 2017/9/3.
 */
public class CsdnSpider implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

    public static final String URL_LIST = "http://blog\\.csdn\\.net\\?page=\\d{1,4}";

    public static int pageNum = 1;

    @Override
    public void process(Page page) {
        if (page.getUrl().regex("^http://blog\\.csdn\\.net$").match()) {
            List<String> uris = page.getHtml().xpath("//*div[@class='blog_home_main']/div[@class='blog_l fl']/div[2]/dl/dt/a/@href").all();
            page.addTargetRequests(uris);
            pageNum++;
            page.addTargetRequest("http://blog.csdn.net?page=2");
        }else if(page.getUrl().regex(URL_LIST).match() && pageNum <= 430){
            System.out.println("进来了");
        }else{

        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new CsdnSpider()).addUrl("http://blog.csdn.net").thread(5).run();
    }
}
