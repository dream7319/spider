package com.lierl.spider;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

/**
 * @author lierl
 * @create 2017-08-31 16:08
 **/
public class FileTest {
	public static void main(String[] args) throws IOException {
		List<String> datas = Lists.newArrayList();
		datas.add("1");
		datas.add("2");
		datas.add("1");
		System.out.println(Joiner.on(",").join(new HashSet<String>(datas)));
	}
}
