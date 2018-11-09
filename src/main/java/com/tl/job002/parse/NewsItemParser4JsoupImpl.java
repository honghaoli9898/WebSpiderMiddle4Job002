package com.tl.job002.parse;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tl.job002.iface.parser.NewsItemParserInterface;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.utils.JsoupUtil;
import com.tl.job002.utils.JsoupUtil.ContentSelectType;
import com.tl.job002.utils.StaticValue;

public class NewsItemParser4JsoupImpl implements NewsItemParserInterface {

	// 此方法逻辑不合常理固抛弃
	// @Override
	// public List<NewsItemEntity> parserHtmlSource(String htmlSource) throws
	// ParseException {
	// List<NewsItemEntity> itemList = new ArrayList<NewsItemEntity>();
	// String liSelector = "ul.tj3_1>li>a";
	// List<String> titleList = JsoupUtil.getElementsBySelector(htmlSource,
	// liSelector, ContentSelectType.TEXT);
	// String hrefSelector = "ul.tj3_1>li>a[href]";
	// List<String> hrefList = JsoupUtil.getElementsBySelector(htmlSource,
	// hrefSelector, "href");
	// String postTimeSelector = "ul.tj3_1>li>font";
	// List<String> postTimeList = JsoupUtil.getElementsBySelector(htmlSource,
	// postTimeSelector,
	// ContentSelectType.TEXT);
	//
	// return null;
	// }
	@Override
	public List<NewsItemEntity> parserHtmlSource(String htmlSource) throws ParseException {
		List<NewsItemEntity> itemList = new ArrayList<NewsItemEntity>();
		// 取得li所有元素
		String selector = "ul.tj3_1>li";
		Elements liElements = JsoupUtil.getElementsBySelector(htmlSource, selector);
		NewsItemEntity itemEntity = null;
		String title = null;
		String postTime = null;
		String href = null;
		// 遍历li element 通过child和attribuate
		for (Element element : liElements) {
			title = JsoupUtil.getChildElementValue(element, 1, ContentSelectType.TEXT);
			postTime = JsoupUtil.getChildElementValue(element, 0, ContentSelectType.TEXT);
			href = JsoupUtil.getAttributeValue(element.child(1), "href");
			href = StaticValue.rootUrl+href.substring(2);
			itemEntity = new NewsItemEntity(title, href, postTime);
			itemList.add(itemEntity);
		}
		for (NewsItemEntity element : itemList) {
			System.out.println(element);
		}
		return itemList;
	}
}
