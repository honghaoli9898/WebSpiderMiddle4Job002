package com.tl.job002.utils;

import java.util.Random;

public class RandomNumberUtil {
	// 得到一个随机数
	public static int getRandomNumber() {
		Random random = new Random();
		int number = random.nextInt(SystemConfigParas.sleep_wait_time) * 1000;
		if (number < 1000) {
			number = random.nextInt(SystemConfigParas.sleep_wait_time) * 1000;
		}
		return number;
	}
}
