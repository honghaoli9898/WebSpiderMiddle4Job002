package com.tl.job002.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date工具类
 * 
 * @author lihonghao
 * @date 2018年11月9日
 */
public class DateUtil {
	public static String yyyyMMDDHHmmss = "yyyy-MM-dd HH:mm:ss";
	static SimpleDateFormat sdf_yyyyMMDDHHmmss = new SimpleDateFormat(yyyyMMDDHHmmss);

	// 拿到当前所属的date对象
	public static Date getDate() {
		return new Date();
	}

	public static Date parseStringToDate(String dateString) throws ParseException {
		return sdf_yyyyMMDDHHmmss.parse(dateString);
	}

	public static String formatStringToDate(Date date) throws ParseException {
		return sdf_yyyyMMDDHHmmss.format(date);
	}
	public static void main(String[] args) throws ParseException {
		String s= "2018-11-08 03:04:00";
		System.out.println(parseStringToDate(s));
	}
}
