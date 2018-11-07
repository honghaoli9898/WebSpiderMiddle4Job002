package com.tl.job002.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.tl.job002.utils.IOUtil;
import com.tl.job002.utils.StaticValue;
import com.tl.job002.utils.WebCharsetDetecorUtil;

/**
 * �������ظ���������ַ��Ӧ��html����
 * @author lihonghao
 * @date 2018��11��6��
 */
public class WebPageDownloadUtil {
	public static String download(String url) throws IOException{
		String charset=WebCharsetDetecorUtil.getCharset(url);
		BufferedReader br=IOUtil.getBR(url, charset);
		StringBuilder stringBuilder=new StringBuilder();
		String line=null;
		int lineCounter=0;
		while((line=br.readLine())!=null){
			if(lineCounter>0){
				stringBuilder.append(StaticValue.sep_next_line);
			}
			stringBuilder.append(line);
			lineCounter++;
		}
		br.close();
		return stringBuilder.toString();
	}
	public static void main(String[] args) throws IOException {
		String url="http://news.youth.cn/gn/";
//		String url= "https://www.baidu.com/";
//		String url= "https://www.sina.com.cn";
		
	}
}
