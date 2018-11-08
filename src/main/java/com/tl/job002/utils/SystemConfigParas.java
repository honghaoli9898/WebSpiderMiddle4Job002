package com.tl.job002.utils;

import java.io.IOException;

/**
 * 系统配置参数工具类,集中读取
 * @author lihonghao
 * @date 2018年11月8日
 */
public class SystemConfigParas {
	public static ReadConfigUtil configUtil = null;
	static{
		try {
			configUtil = new ReadConfigUtil("spider.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//初始化的下载消费线程数量
	public static int init_consumer_number = Integer.parseInt(configUtil.getValue("init_consumer_number"));
	//每次遇到空任务时的睡眠时间单位为秒
	public static int once_sleep_time_for_empty_task = Integer.parseInt(configUtil.getValue("once_sleep_time_for_empty_task"))*1000;
	
}
