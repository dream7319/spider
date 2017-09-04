package com.lierl.spider;

import java.io.IOException;

/**
 * @author lierl
 * @create 2017-08-29 14:06
 **/
public class SpiderMain {

	public static void main(String[] args) throws IOException {
		String s = " 97条  共7页";
		int index = s.indexOf("共");
		System.out.println(s.substring(index+1).replace("页",""));
		System.out.println(s.substring(0,s.indexOf("条")).trim());
	}

}
