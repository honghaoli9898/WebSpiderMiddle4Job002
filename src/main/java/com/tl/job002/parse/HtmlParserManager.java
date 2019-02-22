package com.tl.job002.parse;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.tl.job002.iface.parser.JDGoodsParseInterface;
import com.tl.job002.iface.parser.NewsItemParserInterface;
import com.tl.job002.pojos.entity.JDGoodsCommentsEntriy;
import com.tl.job002.pojos.entity.JDGoodsEntriy;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.utils.JsoupUtil;
import com.tl.job002.utils.RandomNumberUtil;
import com.tl.job002.utils.SystemConfigParas;
import com.tl.job002.utils.WebPageDownloadUtil4ChromeDriver;

public class HtmlParserManager {
	static Logger logger = Logger.getLogger(HtmlParserManager.class);
	public static JDGoodsParseInterface parserInterface = new JDGoodsParser4JsoupImpl();
	public static NewsItemParserInterface parserInterface_1 = new NewsItemParser4JsoupImpl();

	public static List<NewsItemEntity> parserHtmlSource_1(String htmlSource)
			throws ParseException {
		return parserInterface_1.parserHtmlSource(htmlSource);
	}

	public static Map<String, JDGoodsEntriy> parserHtmlSource(String htmlSource)
			throws ParseException {
		return parserInterface.parserHtmlSource(htmlSource);
	}

	public static List<JDGoodsCommentsEntriy> parserHtmlSource(
			String htmlSource, Map<String, JDGoodsEntriy> jdGoodsEntriyMap) {
		return parserInterface.parserHtmlSource(htmlSource, jdGoodsEntriyMap);
	}

	public static List<JDGoodsCommentsEntriy> parserHtmlSource(
			WebPageDownloadUtil4ChromeDriver downloadInterface,
			Map<String, JDGoodsEntriy> jdGoodsEntriyMap) {
		int i = 0;
		List<JDGoodsCommentsEntriy> jdGoodsCommentsEntriyList = null;
		for (Entry<String, JDGoodsEntriy> entry : jdGoodsEntriyMap.entrySet()) {
			String htmlSource = downloadInterface
					.download(SystemConfigParas.comment_url.replace("()", entry
							.getValue().getGoodsSKU()));
			if (htmlSource != null) {
				htmlSource = JsoupUtil
						.getElementsBySelector(htmlSource, "body").text();
				jdGoodsCommentsEntriyList = parserInterface.parserHtmlSource(
						htmlSource, jdGoodsEntriyMap);
				int time = RandomNumberUtil.getRandomNumber();
				i++;
				logger.info("第" + i + "商品的评论也爬取完毕,即将休息" + time / 1000 + "秒");
				try {
					Thread.sleep(time);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				
			}
		}
		return jdGoodsCommentsEntriyList;
	}

}
