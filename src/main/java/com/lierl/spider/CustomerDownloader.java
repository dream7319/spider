package com.lierl.spider;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.List;

/**
 * Created by lierl on 2017/9/3.
 */
public class CustomerDownloader extends HttpClientDownloader{

    List<String> errorUrls;

    public CustomerDownloader(){}

    public CustomerDownloader(List<String> errorUrls){
        this.errorUrls = errorUrls;
    }

    @Override
    protected void onError(Request request) {
        errorUrls.add(request.getUrl());
    }
}
