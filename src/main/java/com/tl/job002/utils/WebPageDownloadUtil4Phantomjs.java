package com.tl.job002.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.tl.job002.iface.download.DownloadInterface;

public class WebPageDownloadUtil4Phantomjs implements DownloadInterface {
	public static String downloadStatic(String url) {
		String html = null;
		String saveFileName = url.hashCode() + ".txt";
		Runtime runtime = Runtime.getRuntime();
		String workingDir = "plugins\\phantomjs\\";
		String exePath = workingDir + "phantomjs.exe";
		String jsPath = workingDir + "js\\crawl_random_page_write_to_file.js";
		String command_line = exePath + " " + jsPath;
		String arg = url + " " + saveFileName;
		Process process;
		BufferedReader br = null;
		try {
			process = runtime.exec(command_line + " " + arg);
			InputStream is = process.getInputStream();
			process.getErrorStream().close();
			process.getOutputStream().close();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isr);
			String temp = "";
			while ((temp = br.readLine()) != null) {
				System.out.println(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br == null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		try {
			html = IOUtil.readFile(saveFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
//		} finally {
//			if (IOUtil.deleteFile(saveFileName)) {
//				System.out.println("删除临时文件" + saveFileName + "成功！");
//			} else {
//				System.err.println("删除临时文件" + saveFileName + "失败！");
//			}
//		}

		return html;
	}

	@Override
	public String download(String url) {
		String html = downloadStatic(url);
		if (html != null) {
			return html;
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		String url = "https://search.jd.com/Search?keyword=手机&enc=utf-8&psort=3&page=1";
		String html = downloadStatic(url);
		System.out.println(html);
	}
}
