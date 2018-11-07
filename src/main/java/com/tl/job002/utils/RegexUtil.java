package com.tl.job002.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����ƥ�乤����
 * 
 * @author lihonghao
 * @date 2018��11��7��
 */
public class RegexUtil {
	public static String getMatchText(String input, String regex, int groupNum) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			return matcher.group(groupNum);
		}
		return null;
	}
}
