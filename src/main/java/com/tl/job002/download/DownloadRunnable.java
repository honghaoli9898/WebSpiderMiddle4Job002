package com.tl.job002.download;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.tl.job002.iface.download.DownloadInterface;
import com.tl.job002.parse.HtmlParserManager;
import com.tl.job002.pojos.UrlTaskPojo;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.schedule.TaskScheduleManager;
import com.tl.job002.ui.UIManager;
import com.tl.job002.utils.SystemConfigParas;
import com.tl.job002.utils.WebpageDownloadUtil4HttpClient;

public class DownloadRunnable implements Runnable {
	private boolean enableRunning = true;
	private String name;
	public static Logger logger = Logger.getLogger(DownloadRunnable.class);

	public DownloadRunnable(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		DownloadInterface downloadInterface = new WebpageDownloadUtil4HttpClient();
		while (enableRunning) {
			UrlTaskPojo taskPojo = TaskScheduleManager.take();
			if (taskPojo != null) {
				// 1.打印下载的内容
				String htmlSource = downloadInterface.download(taskPojo.getUrl());
				logger.info(this.name + "将进入解析环节");
				if (htmlSource != null) {
					try {
						List<NewsItemEntity> itemEntityList = HtmlParserManager.parserHtmlSource(htmlSource);
						for (NewsItemEntity newsItemEntity : itemEntityList) {
							System.out.println(newsItemEntity);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
					// logger.info(htmlSource);
					logger.info("一页解析完成,即将下一页");
				} else {
					// 如果htmlSource==null,代表下载出错了
					logger.error(this.name + "下载出错,该任务为=" + taskPojo.getUrl());
				}
			} else {
				logger.info(
						this.name + "没有带采集的任务,线程将睡眠" + SystemConfigParas.once_sleep_time_for_empty_task / 1000 + "秒");
				try {
					Thread.sleep(2000);
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

	public static void main(String[] args) throws Exception {
		// 将带采集的url加入到Task任务当中
		UIManager.addSeedUrlsToTaskSchedule();
		//启动线程
		DownloadManager.start();
	}
}
