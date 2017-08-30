package com.lierl.spider;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

/**
 * @author lierl
 * @create 2017-08-29 14:06
 **/
public class SpiderMain {

	public static OkHttpClient client;

	private static OkHttpClient getClient() {
		if(client == null){
			final okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
			client = httpBuilder
					.connectTimeout(30, TimeUnit.SECONDS)
					.writeTimeout(30, TimeUnit.SECONDS)
					.build(); //设置超时
		}

		return client;
	}

	public static void main(String[] args) throws IOException {

		Request request = new Request.Builder().url("https://www.cnblogs.com/sitehome/p/1").build();
		OkHttpClient client = getClient();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			System.out.println(response.body().string());
		}
	}

}
