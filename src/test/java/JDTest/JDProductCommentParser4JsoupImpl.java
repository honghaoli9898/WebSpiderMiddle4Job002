package JDTest;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tl.job002.iface.parser.NewsItemParserInterface;
import com.tl.job002.pojos.entity.NewsItemEntity;
import com.tl.job002.utils.IOUtil;
import com.tl.job002.utils.JsoupUtil;

public class JDProductCommentParser4JsoupImpl implements
		NewsItemParserInterface {
	@Override
	public List<NewsItemEntity> parserHtmlSource(String htmlSource)
			throws ParseException {
		JSONObject jsonComment = JSON.parseObject(htmlSource);
		// 晒图数量
		String imageListCount = jsonComment.getString("imageListCount");
		// 得到评论汇总信息
		JSONObject productCommentSummary = (JSONObject) jsonComment
				.get("productCommentSummary");
		// 商品id
		String skuId = productCommentSummary.getString("skuId");
		// 全部评价
		String commentCountStr = productCommentSummary
				.getString("commentCountStr");
		// 好评度
		String goodRateShow = productCommentSummary.getString("goodRateShow");
		// 好评
		String goodCountStr = productCommentSummary.getString("goodCountStr");
		// 中评
		String generalCountStr = productCommentSummary
				.getString("generalCountStr");
		// 差评
		String poorCountStr = productCommentSummary.getString("poorCountStr");
		// 视频晒单
		String videoCountStr = productCommentSummary.getString("videoCountStr");
		// 追评
		String afterCountStr = productCommentSummary.getString("afterCountStr");
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
		// 得到评论详情
		jsonArray = jsonComment.getJSONArray("comments");
		for (Object object : jsonArray) {
			JSONObject jsonObject = (JSONObject) object;
			// 账号id
			String nickName = jsonObject.getString("nickname");
			// 用户级别
			String userLevel = jsonObject.getString("userLevelName");
			// 账号等级
			String accountLevel = jsonObject.getString("userLevelId");
			// 用户来源
			String userSource = jsonObject.getString("userClientShow");
			// 用户手机版本
			String phoneVersion = jsonObject.getString("mobileVersion");
			// 评论商品id
			String commentProductId = jsonObject.getString("referenceId");
			// 评论内容
			String commentContext = jsonObject.getString("content").trim();
			// 手机颜色
			String productColer = jsonObject.getString("productColor");
			// 商品配置
			String productConfig = jsonObject.getString("productSize");
			// 参考时间购买时间
			String referenceTime = jsonObject.getString("referenceTime");
			// 评论时间
			String creationTime = jsonObject.getString("creationTime");
			// 购买到评论时间
			String buyToCommentDays = jsonObject.getString("days");
			// 几天后评论
			String afterDays = jsonObject.getString("afterDays");
		}
		return null;
	}

	public static void main(String[] args) throws IOException, ParseException {
//		JDProductCommentParser4JsoupImpl s = new JDProductCommentParser4JsoupImpl();
//		String htmlSource = IOUtil.readFile("1007063779.txt");
//		htmlSource = JsoupUtil.getElementsBySelector(htmlSource, "body").html();
//		System.out.println(htmlSource);
		String s1 = new String ("1");
		String s2 = new String ("1");
		System.out.println(s1.equals(s2));
		System.out.println(System.currentTimeMillis());
	}

}
