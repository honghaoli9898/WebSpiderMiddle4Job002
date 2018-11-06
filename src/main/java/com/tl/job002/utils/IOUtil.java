package com.tl.job002.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IOUtil {
	public static List<String> readFileToList(String filePath,boolean isClassPath,String charset) throws IOException {
		InputStream is=null;
		if(isClassPath){
			is = ReadConfigUtil.class.getClassLoader().getResourceAsStream(filePath);
		}
		else{
			is=new FileInputStream(filePath);
		}
		InputStreamReader isr = new InputStreamReader(is, charset);
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		List<String> lineList = new ArrayList<String>();
		while ((line = br.readLine()) != null) {
			lineList.add(line);
		}
		br.close();
		return lineList;
	}
}
