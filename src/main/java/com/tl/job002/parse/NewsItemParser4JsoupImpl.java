package com.tl.job002.parse;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tl.job002.iface.parser.NewsItemParserInterface;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.utils.JsoupUtil;
import com.tl.job002.utils.JsoupUtil.ContentSelectType;
import com.tl.job002.utils.RegexUtil;
import com.tl.job002.utils.StaticValue;
import com.tl.job002.utils.WebPageDownloadUtil4HttpClient;

public class NewsItemParser4JsoupImpl implements NewsItemParserInterface {
	public static Logger logger = Logger.getLogger(NewsItemParser4JsoupImpl.class);
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
	public List<NewsItemEntity> parserHtmlSource(String htmlSource) {
		List<NewsItemEntity> itemList = new ArrayList<NewsItemEntity>();
		String selector = "ul.tj3_1>li";
		Elements liElements = JsoupUtil.getElementsBySelector(htmlSource, selector);
		NewsItemEntity itemEntity = null;
		String title = null;
		String postTime = null;
		String href = null;
		for (Element element : liElements) {
			title = JsoupUtil.getChildElementValue(element, 1, ContentSelectType.TEXT);
			postTime = JsoupUtil.getChildElementValue(element, 0, ContentSelectType.TEXT);
			href = JsoupUtil.getAttributeValue(element.child(1), "href");
			if(href.startsWith("../")){
				href = StaticValue.indexUrl+href.substring(3);
			}else if(href.startsWith("./")){
				href = StaticValue.rootUrl+href.substring(2);
			}
			try {
				itemEntity = new NewsItemEntity(title, href, postTime);
			} catch (ParseException e) {
				e.printStackTrace();
				logger.error("出错信息:"+title+'\n'+href+'\n'+postTime);
			}
			itemList.add(itemEntity);
		}
		return itemList;
	}
	public static void main(String[] args) throws IOException {
//		String url ="https://search.jd.com/Search?keyword=手机&enc=utf-8&psort=3&page=2";
		String url = "https://search.jd.com/s_new.php?keyword=手机&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&psort=3&cid2=653&cid3=655&page=2&s=31&scrolling=y&log_id=1545290437.42763&tpl=3_M&show_items=5089235,100000287145,100000177756,7694047,5821455,5089225,5089273,8735304,100000822981,100001172674,7081550,100000287133,100001464948,6946605,100000651175,7437564,100000773875,7479912,1861091,100000972490,6600258,7437788,7643003,100000820311,100000766433,100001467225,100000349372,100000822969,6733024,100001548579";
		String htmlSource = WebPageDownloadUtil4HttpClient.downloadStatic(url);
		System.out.println(htmlSource.matches("[\\s\\S]*gl-item[\\s\\S]*"));
		String selector ="li.gl-item";
		Elements elements =JsoupUtil.getElementsBySelector(htmlSource, selector);
		for (Element element : elements) {
			String s=element.outerHtml();
			System.out.println(element.text());
			String context=RegexUtil.getMatchText(s, "[\\s\\S]*?data-sku=\"([\\s\\S]*?)\"[\\s\\S]*", 1);
			System.out.println(context);
		}
	}
}
