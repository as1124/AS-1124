package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.model.statistics.InterfaceStatisticData;
import com.mobile.thirdparty.wechat.model.statistics.MessageStatisticsData;
import com.mobile.thirdparty.wechat.model.statistics.NewsStatisticsData;
import com.mobile.thirdparty.wechat.model.statistics.UserStatisticsData;

/**
 * WeChat 数据分析API.<br>
 * <li>日期串统一格式： yyyy-MM-dd
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class DataAnalyzeOperations {

	static Logger logger = Logger.getLogger(DataAnalyzeOperations.class.getName());

	private DataAnalyzeOperations() {
	}

	/**
	 * 获取用户增减数据, 最大时间跨度是7天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<UserStatisticsData> getUserSummary(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getusersummary");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(json.getString("list"), UserStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<UserStatisticsData>();
		}
	}

	/**
	 * 获取累计用户数据，最大时间跨度是7天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<UserStatisticsData> getUserCumulate(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getusercumulate");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(json.getString("list"), UserStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<UserStatisticsData>();
		}
	}

	/**
	 * 获取图文群发每日数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<NewsStatisticsData> getArticleSummary(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getarticlesummary");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取图文群发总数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<NewsStatisticsData> getArticleTotal(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getarticletotal");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取图文统计数据, 最大时间跨度为3天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<NewsStatisticsData> getUserRead(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getuserread");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取图文统计分时数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<NewsStatisticsData> getUserReadByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getuserreadhour");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取图文分享转发数据，最大时间跨度7天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<NewsStatisticsData> getUserShare(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getusershare");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取图文分享分时数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<NewsStatisticsData> getUserShareByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getusersharehour");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<NewsStatisticsData>();
		}
	}

	/**
	 * 获取消息发送概况数据，最大时间跨度7天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgSummary(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsg");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送分时数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgSummaryByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsghour");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送周数据, 最大时间跨度30天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgSummaryByWeek(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsgweek");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送月数据, 最大时间跨度30天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgSummaryByMonth(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsgmonth");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送分布数据, 最大时间跨度15天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgDistSummary(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsgdist");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送分布周数据, 最大时间跨度30天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgDistSummaryByWeek(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsgdistweek");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取消息发送分布月数据，最大时间跨度30天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<MessageStatisticsData> getMsgDistSummaryByMonth(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getupstreammsgdistmonth");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<MessageStatisticsData>();
		}
	}

	/**
	 * 获取接口分析数据, 最大时间跨度30天
	 * 
	 * @param token
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static List<InterfaceStatisticData> getInterfaceSummary(AbstractAccessToken token, String beginDate,
			String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getinterfacesummary");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"),
				InterfaceStatisticData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<InterfaceStatisticData>();
		}
	}

	/**
	 * 获取接口分析分时数据
	 * 
	 * @param token
	 * @param date
	 * @return
	 */
	public static List<InterfaceStatisticData> getInterfaceSummaryHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccessToken(),
			"https://api.weixin.qq.com/datacube/getinterfacesummaryhour");
		JSONObject json = JSONObject.parseObject(result);
		String returnCode = json.getString(WechatConstants.ERROR_CODE);
		if (returnCode == null || WechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)) {
			return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"),
				InterfaceStatisticData.class);
		} else {
			logger.log(Level.SEVERE, result);
			return new ArrayList<InterfaceStatisticData>();
		}
	}

	/**
	 * 复写，减少重复代码
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @param uri
	 * @return
	 */
	private static String getReturnJson(String beginDate, String endDate, String accessToken, String uri) {
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair(WechatConstants.KEY_ACCESS_TOKEN, accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date", beginDate);
		jsonObj.put("end_date", endDate);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData,
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		return HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
	}

}
