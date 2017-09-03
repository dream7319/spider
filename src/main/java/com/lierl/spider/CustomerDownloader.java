package com.lierl.spider;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

/**
 * Created by lierl on 2017/9/3.
 */
public class CustomerDownloader extends HttpClientDownloader{

    @Override
    protected void onError(Request request) {
        System.out.println("-----------异常:"+request.getUrl());
    }
}
