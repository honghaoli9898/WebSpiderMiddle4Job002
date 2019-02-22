package com.tl.job002.parse;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tl.job002.iface.parser.JDGoodsParseInterface;
import com.tl.job002.pojos.entity.JDGoodsCommentsEntriy;
import com.tl.job002.pojos.entity.JDGoodsEntriy;
import com.tl.job002.utils.IOUtil;
import com.tl.job002.utils.JSONObjectUtil;
import com.tl.job002.utils.JsoupUtil;
import com.tl.job002.utils.JsoupUtil.ContentSelectType;

public class JDGoodsParser4JsoupImpl implements JDGoodsParseInterface {
	public static Logger logger = Logger
			.getLogger(JDGoodsParser4JsoupImpl.class);

	@Override
	public Map<String, JDGoodsEntriy> parserHtmlSource(String htmlSource) {
		Map<String, JDGoodsEntriy> jdGoodsEntriyMap = new HashMap<String, JDGoodsEntriy>();
		String selector = "#J_goodsList > ul > li";
		// 即将解析商品信息
		Elements ulElements = JsoupUtil.getElementsBySelector(htmlSource,
				selector);
		for (Element element : ulElements) {
			JDGoodsEntriy jdGoodsEntrity = new JDGoodsEntriy();
			// 商品id号
			String goodsSKU = JsoupUtil.getAttributeValue(element, "data-sku");
			Element childElement = element.child(0);
			// 商品标题
			String goodsTitle = JsoupUtil.getAttributeValue(
					childElement.child(0).child(0), "title");
			Element childElementSecond = childElement.child(1);
			Elements elements = JsoupUtil.getElementsBySelector(
					childElementSecond.toString(), "a");
			String goodsStyle = null;
			int temp = 0;
			for (Element element2 : elements) {
				if (temp == 0) {
					// 商品风格颜色
					goodsStyle = element2.attr("title");
				} else {
					goodsStyle = goodsStyle + " " + element2.attr("title");
				}
				temp++;
			}
			temp = 0;
			childElementSecond = childElement.child(2);
			// 商品价格
			String goodsPrice = JsoupUtil.getChildElementValue(
					childElementSecond, 0, ContentSelectType.TEXT);
			goodsPrice = goodsPrice.substring(1) + goodsPrice.charAt(0);
			childElementSecond = childElement.child(3);
			elements = JsoupUtil.getElementsBySelector(
					childElementSecond.toString(), "em");
			// 商品名称
			String goodsName = elements.text();
			childElementSecond = childElement.child(4);
			// 商品评论数
			String goodsCommentCount = childElementSecond.text().split("\\+")[0]
					+ "+";
			childElementSecond = childElement.child(6);
			elements = JsoupUtil.getElementsBySelector(
					childElementSecond.toString(), "a");
			// 商店信息
			String goodsStoreInfo = elements.text();
			childElementSecond = childElement.child(7);
			// 商品标签
			String goodsPag = childElementSecond.text();
			// jdGoodsEntrity赋值
			jdGoodsEntrity.setGoodsCommentCount(goodsCommentCount);
			jdGoodsEntrity.setGoodsTitle(goodsTitle);
			jdGoodsEntrity.setGoodsName(goodsName);
			jdGoodsEntrity.setGoodsPag(goodsPag);
			jdGoodsEntrity.setGoodsPrice(goodsPrice);
			jdGoodsEntrity.setGoodsSKU(goodsSKU);
			jdGoodsEntrity.setGoodsStoreInfo(goodsStoreInfo);
			jdGoodsEntrity.setGoodsStyle(goodsStyle);
			jdGoodsEntriyMap.put(goodsSKU, jdGoodsEntrity);
		}
		return jdGoodsEntriyMap;
	}

	@Override
	public List<JDGoodsCommentsEntriy> parserHtmlSource(String htmlSource,
			Map<String, JDGoodsEntriy> JDGoodsEntriyMap) {
		List<JDGoodsCommentsEntriy> jdGoodsCommentsEntriyList = new ArrayList<JDGoodsCommentsEntriy>();
		Map<String, String> kvMap = new HashMap<String, String>();
		JSONObject jsonComment = JSON.parseObject(htmlSource);
		// 晒图数量
		JSONObjectUtil
				.getJSONObjectValues(kvMap, jsonComment, "imageListCount");
		// 得到评论汇总信息
		JSONObject productCommentSummary = (JSONObject) jsonComment
				.get("productCommentSummary");
		String[] args = { "skuId", "commentCountStr", "goodRateShow",
				"goodCountStr", "generalCountStr", "poorCountStr",
				"videoCountStr", "afterCountStr" };
		JSONObjectUtil.getJSONObjectValues(kvMap, productCommentSummary, args);
		JDGoodsEntriy jdGoodsEntriy = JDGoodsEntriyMap.get(kvMap.get(args[0]));
		jdGoodsEntriy.setAttribute(jdGoodsEntriy, kvMap, args);
		jdGoodsEntriy.setImageListCount(kvMap.get("imageListCount"));
		// 得到商品评论类型
		JSONArray jsonArray = jsonComment
				.getJSONArray("hotCommentTagStatistics");
		JSONArray commentTypeArray = new JSONArray();
		for (Object object : jsonArray) {
			JSONObject commentObject = new JSONObject();
			JSONObject jsonObject = (JSONObject) object;
			// 评论类型
			String commentType = jsonObject.getString("name");
			// 对应次数
			String commentCount = jsonObject.getString("count");
			commentObject.put(commentType, commentCount);
			commentTypeArray.add(commentObject);
		}
		jdGoodsEntriy.setCommentTypeArrayStr(commentTypeArray.toJSONString());
		logger.info("解析商品" + jdGoodsEntriy.getGoodsSKU() + "完成");
		logger.info("即将解析评论");
		// 得到评论详情
		jsonArray = jsonComment.getJSONArray("comments");
		for (Object object : jsonArray) {
			JDGoodsCommentsEntriy jdGoodsCommentsEntriy = new JDGoodsCommentsEntriy();
			jdGoodsCommentsEntriy.setCommentSourceSku(jdGoodsEntriy
					.getGoodsSKU());
			JSONObject jsonObject = (JSONObject) object;
			String[] argsArray = { "nickname", "userLevelName", "userLevelId",
					"userClientShow", "mobileVersion", "referenceId", "score",
					"content", "productColor", "productSize", "referenceTime",
					"creationTime", "days", "afterDays" };
			JSONObjectUtil.getJSONObjectValues(kvMap, jsonObject, argsArray);
			jdGoodsCommentsEntriy.setAttribute(jdGoodsCommentsEntriy, kvMap,
					argsArray);
			jdGoodsCommentsEntriyList.add(jdGoodsCommentsEntriy);
			kvMap.clear();
		}
		logger.info("解析商品评论" + jdGoodsEntriy.getGoodsSKU() + "完成");
		return jdGoodsCommentsEntriyList;
	}

	public static void main(String[] args) throws IOException, ParseException {
		JDGoodsParser4JsoupImpl jd = new JDGoodsParser4JsoupImpl();
		String htmlSource = IOUtil.readFile("1605322110.txt");
		Map<String, JDGoodsEntriy> map = jd.parserHtmlSource(htmlSource);
		for (Entry<String, JDGoodsEntriy> string : map.entrySet()) {
			System.out.println(string.getValue());
		}
		htmlSource = IOUtil.readFile("1007063779.txt");
		List<JDGoodsCommentsEntriy> list = jd.parserHtmlSource(htmlSource, map);
		System.out.println(list);
	}
}
