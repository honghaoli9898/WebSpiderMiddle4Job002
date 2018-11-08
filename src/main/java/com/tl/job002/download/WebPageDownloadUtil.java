package com.tl.job002.download;

import java.io.BufferedReader;
import java.io.IOException;

import com.tl.job002.iface.download.DownloadInterface;
import com.tl.job002.utils.IOUtil;
import com.tl.job002.utils.StaticValue;
import com.tl.job002.utils.WebCharsetDetecorUtil;

/**
 * 用于下载给定任意网址对应的html代码
 * 
 * @author lihonghao
 * @date 2018年11月6日
 */
public class WebPageDownloadUtil implements DownloadInterface {
	public static String downloadStatic(String url) throws IOException {
		String charset = WebCharsetDetecorUtil.getCharset(url);
		BufferedReader br = IOUtil.getBR(url, charset);
		StringBuilder stringBuilder = new StringBuilder();
		String line = null;
		int lineCounter = 0;
		while ((line = br.readLine()) != null) {
			if (lineCounter > 0) {
				stringBuilder.append(StaticValue.sep_next_line);
			}
			stringBuilder.append(line);
			lineCounter++;
		}
		br.close();
		return stringBuilder.toString();
	}

	@Override
	public String download(String url) {
		try {
			return downloadStatic(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
