package com.tl.job002.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebCharsetDetecorUtil {
	public static String regex = "charset=[\"]?([\\s\\S]*?)\"?\\s?/?[\">]";

	public static String getCharset(String url) throws IOException {
		URL urlObj = new URL(url);
		URLConnection urlconnection = urlObj.openConnection();
		Map<String, List<String>> headerMap = urlconnection.getHeaderFields();
		Set<String> keySet = headerMap.keySet();
		String findCharset = null;
		for (String key : keySet) {
			if (key != null && key.equalsIgnoreCase("Content-Type")) {
				List<String> valueList = headerMap.get(key);
				String line = valueList.get(0);
				String[] valueArray = line.split(StaticValue.sep_semicolon);
				if (valueArray.length == 2) {
					String charsetString = valueArray[1];
					charsetString = charsetString.trim();
					valueArray = charsetString.split(StaticValue.sep_equales);
					if (valueArray.length == 2) {
						findCharset = valueArray[1].trim();
					}
				}
			}
		}
		// 如果findCharset在header当中没找到则在meta charset找
		if (findCharset == null) {
			BufferedReader br = IOUtil.getBR(urlconnection, StaticValue.defaultENCODING);
			;
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim().toLowerCase();
				if (line.contains("<meta")) {
					findCharset = RegexUtil.getMatchText(line, regex, 1);
					if (findCharset != null) {
						break;
					}
				} else if (line.contains("</head>")) {
					//如果到了</head>还没找到就不用找了
					break;
				}
			}
			br.close();
		}
		return findCharset == null ? StaticValue.defaultENCODING : findCharset;
	}

	public static void main(String[] args) throws IOException {
		String url = "https://www.baidu.com/";
		System.out.println(WebCharsetDetecorUtil.getCharset(url));
	}
}
