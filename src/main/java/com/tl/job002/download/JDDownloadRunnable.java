package com.tl.job002.download;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.eclipse.jetty.util.IO;

import com.tl.job002.iface.download.DownloadInterface;
import com.tl.job002.parse.HtmlParserManager;
import com.tl.job002.persistence.DataPersistManager;
import com.tl.job002.pojos.UrlTaskPojo;
import com.tl.job002.pojos.entity.JDGoodsCommentsEntriy;
import com.tl.job002.pojos.entity.JDGoodsEntriy;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.schedule.TaskScheduleManager;
import com.tl.job002.utils.IOUtil;
import com.tl.job002.utils.SystemConfigParas;
import com.tl.job002.utils.WebDriverUtil;
import com.tl.job002.utils.WebPageDownloadUtil4ChromeDriver;

public class JDDownloadRunnable implements Runnable {
	private boolean enableRunning = true;
	private String name;
	public static Logger logger = Logger.getLogger(DownloadRunnable.class);

	public JDDownloadRunnable(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		WebPageDownloadUtil4ChromeDriver downloadInterface = new WebPageDownloadUtil4ChromeDriver();
		downloadInterface.setWebDriver(WebDriverUtil.createWebDriver(false));
		while (enableRunning) {
			UrlTaskPojo taskPojo = TaskScheduleManager.take();
			if (taskPojo != null) {
				// 1.打印下载的内容
				String htmlSource = downloadInterface.download(taskPojo
						.getUrl());
				if (htmlSource != null) {
					try {
						Map<String, JDGoodsEntriy> itemEntityList = HtmlParserManager
								.parserHtmlSource(htmlSource);
						List<JDGoodsCommentsEntriy> jdGoodsCommentsEntriyList = HtmlParserManager
								.parserHtmlSource(downloadInterface,
										itemEntityList);
						try {
							IOUtil.writeFile(
									jdGoodsCommentsEntriyList.toString(),
									"comment.txt", true);
							logger.info("写入评论成功");
							for (Entry<String, JDGoodsEntriy> entry : itemEntityList
									.entrySet()) {
								IOUtil.writeFile(entry.getValue().toString(),
										"goods.txt", true);
							}
							logger.info("写入商品成功");
						} catch (IOException e) {
							e.printStackTrace();
						}
						// 将解析结果进行持久化存储
						// boolean findRepeatFlag = DataPersistManager
						// .persist(itemEntityList);
						// if (findRepeatFlag) {
						// TaskScheduleManager.cleanTodoTaskList();
						// logger.info("已发现重复采集的数据,将清空本轮的带采集的任务,待下一轮开启");
						// }
						// // 将已成功采集完成的URL加入到donetask 中
						// TaskScheduleManager.addDoneUrlPojo(taskPojo);
						// logger.info(taskPojo.getUrl() + "解析持久化完成,即将下一页");
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					// 如果htmlSource==null,代表下载出错了
					logger.error(this.name + "下载出错,该任务为=" + taskPojo.getUrl());
				}
			} else {
				logger.info(this.name + "没有带采集的任务,线程将睡眠"
						+ SystemConfigParas.once_sleep_time_for_empty_task
						/ 1000 + "秒");
				try {
					Thread.sleep(SystemConfigParas.once_sleep_time_for_empty_task);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info(this.name + "本次睡眠结束");
			}
		}
		this.enableRunning = false;
		logger.info(this.name + "线程结束");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnableRunning() {
		return enableRunning;
	}

	public void setEnableRunning(boolean enableRunning) {
		this.enableRunning = enableRunning;
	}
}
