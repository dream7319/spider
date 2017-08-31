package com.lierl.spider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author lierl
 * @create 2017-08-31 16:08
 **/
public class FileTest {
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("E:\\data.txt"));
		lines.forEach(line->{
			if(!line.startsWith("get page:")){
				System.out.println(line);
			}

		});
	}
}
