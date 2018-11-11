package com.tl.job002.utils;

import java.io.IOException;

/**
 * ϵͳ���ò���������,���ж�ȡ
 * 
 * @author lihonghao
 * @date 2018��11��8��
 */
public class SystemConfigParas {
	public static ReadConfigUtil configUtil = null;
	public static ReadConfigUtil configUtil_db = null;
	static {
		try {
			configUtil = new ReadConfigUtil("spider.properties");
			configUtil_db = new ReadConfigUtil("jdbc.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ��ʼ�������������߳�����
	public static int init_consumer_number = Integer.parseInt(configUtil.getValue("init_consumer_number"));
	// ÿ������������ʱ��˯��ʱ�䵥λΪ��
	public static int once_sleep_time_for_empty_task = Integer
			.parseInt(configUtil.getValue("once_sleep_time_for_empty_task")) * 1000;
	// ����߳�˯��ʱ��
	public static int monitor_sleep_time = Integer.parseInt(configUtil.getValue("monitor_sleep_time")) * 1000;
	//ÿҳ����ظ�����
	public static int max_repeat_number_in_one_page = Integer.parseInt(configUtil.getValue("max_repeat_number_in_one_page"));
	//һ��������ӵ�ʱ����
	public static int add_seed_time_one_circle = Integer.parseInt(configUtil.getValue("add_seed_time_one_circle"))*1000;
	// ���ݿ�
	public static String db_driver = configUtil_db.getValue("db_driver");
	public static String db_url = configUtil_db.getValue("db_url");
	public static String db_username = configUtil_db.getValue("db_username");
	public static String db_password = configUtil_db.getValue("db_password");
}
