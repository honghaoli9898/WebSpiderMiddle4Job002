package com.tl.job002.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 读取配置文件的工具类，既支持直接读取classpath下的，也支持读取外置配置文件
 * 
 * @author lihonghao
 * @date 2018年11月6日
 */
public class ReadConfigUtil {
	// 初始化javase自带的配置文件读取工具类
	private Properties configObj = null;
	private String configFilePath;
	private static Logger logger = Logger.getLogger(ReadConfigUtil.class);

	public ReadConfigUtil(String configFilePath) throws IOException {
		this.configFilePath = configFilePath;
		InputStream is = null;
		File configFileObj = null;
		// 优先读取当前目录下的配置文件路径，如果不存在，则读取jar包内部的
		if ((configFileObj = new File(configFilePath)).exists()) {
			is = new FileInputStream(configFileObj);
			logger.info("从文件路径中读取配置文件!!");
		} else {
			is = ReadConfigUtil.class.getClassLoader().getResourceAsStream(this.configFilePath);
			logger.info("从jar包中读取配置文件!!");
		}
		Reader reader = new InputStreamReader(is);
		configObj = new Properties();
		configObj.load(reader);
		reader.close();
	}

	public String getValue(String key) {
		return configObj.getProperty(key);
	}
}
