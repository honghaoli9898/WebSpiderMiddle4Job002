package com.tl.job002.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取配置文件的工具类，既支持直接读取classpath下的，也支持读取外置配置文件
 * 
 * @author lihonghao
 * @date 2018年11月6日
 */
public class ReadConfigUtil {
	public static void main(String[] args) throws IOException {
		// 1.配置文件路径
		String configPath = "seed.txt";
		// 2.配置文件的读取模式
		boolean isClassPath = true;
		// 3.正式读取文件
		InputStream is = ReadConfigUtil.class.getClassLoader().getResourceAsStream(configPath);
		InputStreamReader isr = new InputStreamReader(is, StaticValue.ENCODING);
		BufferedReader br = new BufferedReader(isr);
		String line=null;
		List<String> lineList=new ArrayList<String>();
		while((line=br.readLine())!=null){
			lineList.add(line);
		}
		br.close();
	}
}
