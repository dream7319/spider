package com.lierl.spider;

import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * Created by lierl on 2017/9/3.
 */
public class CsdnSpider implements PageProcessor{

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");

    public static final String URL_LIST = "http://blog\\.csdn\\.net\\?page=\\d{1,4}";

    public static int pageNum = 1;

    static Iterable<String> urls = Lists.newArrayList();

    @Override
    public void process(Page page) {
//        blogMain(page);//博客首页
        expertsList(page);
    }

    @Override
    public Site getSite() {
        return site;
    }

    static List<String> errorUrls = Lists.newArrayList();

    public static void main(String[] args) {
        //博客首页
//        Spider.create(new CsdnSpider()).addUrl("http://blog.csdn.net").setDownloader(new CustomerDownloader(errorUrls)).thread(5).run();
        Spider.create(new CsdnSpider()).addUrl("http://blog.csdn.net/peoplelist.html?channelid=0&page=1").setDownloader(new CustomerDownloader(errorUrls)).thread(5).run();
        System.out.println("当前页："+pageNum);
        try {
            Files.write(Paths.get("E:\\intellJWork\\spider\\csdn_urls.txt"),urls, StandardOpenOption.APPEND);
            Files.write(Paths.get("E:\\intellJWork\\spider\\error_url.txt"),errorUrls, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void expertsList(Page page){
        if(pageNum <= 127) {
            List<String> uris = page.getHtml().xpath("//*dl/dt/a/@href").all();
            urls = Iterables.concat(urls, uris);
            pageNum++;
            page.addTargetRequest("http://blog.csdn.net/peoplelist.html?channelid=0&page=" + pageNum);
        }
    }

    public void blogMain(Page page){
        if (page.getUrl().regex("^http://blog\\.csdn\\.net$").match()) {
            List<String> uris = page.getHtml().xpath("//*div[@class='blog_home_main']/div[@class='blog_l fl']/div[2]/dl/dt/a[1]/@href").all();
            List<String> listBot1 = page.getHtml().xpath("//*div[@id='listBot']/dl/dt/a[1]/@href").all();
            List<String> listBot2 = page.getHtml().xpath("//*div[@id='listBot2']/dl/dt/a[1]/@href").all();
            urls = Iterables.concat(urls,uris,listBot1,listBot2);
            pageNum++;
            page.addTargetRequest("http://blog.csdn.net?page=2");
        }else if(page.getUrl().regex(URL_LIST).match() && pageNum <= 430){
            List<String> uris = page.getHtml().xpath("//*div[@class='blog_home_main']/div[@class='blog_l fl']/div[2]/dl/dt/a[1]/@href").all();
            urls = Iterables.concat(urls,uris);
            page.addTargetRequest("http://blog.csdn.net?page="+ ++pageNum);
        }
    }
}
