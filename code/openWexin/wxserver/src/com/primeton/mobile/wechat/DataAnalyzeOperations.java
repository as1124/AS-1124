package com.primeton.mobile.wechat;

import java.io.IOException;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.alibaba.fastjson.JSONObject;
import com.primeton.mobile.wechat.exception.WechatExceprion;
import com.primeton.mobile.wechat.model.statistics.InterfaceStatisticData;
import com.primeton.mobile.wechat.model.statistics.MessageStatisticsData;
import com.primeton.mobile.wechat.model.statistics.NewsStatisticsData;
import com.primeton.mobile.wechat.model.statistics.UserStatisticsData;

/**
 * WeChat 数据分析API.<br>
 * <li>日期串统一格式： yyyy-MM-dd
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class DataAnalyzeOperations {

	/**
	 * 获取用户增减数据, 最大时间跨度是7天
	 * @param beginDate 
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	public static UserStatisticsData getUserSummary(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getusersummary");
		return JSONObject.parseObject(result, UserStatisticsData.class);
	}
	
	/**
	 * 获取累计用户数据，最大时间跨度是7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	public static UserStatisticsData getUserCumulate(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getusercumulate");
		return JSONObject.parseObject(result, UserStatisticsData.class);
	}
	
	/**
	 * 获取图文群发每日数据
	 * @param date
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getArticleSummary(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, "https://api.weixin.qq.com/datacube/getarticlesummary");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}
	
	/**
	 * 获取图文群发总数据
	 * @param date
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getArticleTotal(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, "https://api.weixin.qq.com/datacube/getarticletotal");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}
	
	/**
	 * 获取图文统计数据, 最大时间酷大都3天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getUserRead(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getuserread");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}
	
	/**
	 * 获取图文统计分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getUserReadHour(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, "https://api.weixin.qq.com/datacube/getuserreadhour");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}
	
	/**
	 * 获取图文分享转发数据，最大时间跨度7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getUserShare(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getusershare");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}	
	/**
	 * 获取图文分享分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static NewsStatisticsData[] getUserShareHour(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, "https://api.weixin.qq.com/datacube/getusersharehour");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class)
				.toArray(new NewsStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送概况数据，最大时间跨度7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsg(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getupstreammsg");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgHour(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, "https://api.weixin.qq.com/datacube/getupstreammsghour");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送周数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgWeek(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getupstreammsgweek");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送月数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgMonth(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getupstreammsgmonth");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送分布数据, 最大时间跨度15天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgDist(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, "https://api.weixin.qq.com/datacube/getupstreammsgdist");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送分布周数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgDistWeek(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, 
				"https://api.weixin.qq.com/datacube/getupstreammsgdistweek");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取消息发送分布月数据，最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static MessageStatisticsData[] getUpstreamMsgDistMonth(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, 
				"https://api.weixin.qq.com/datacube/getupstreammsgdistmonth");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class)
				.toArray(new MessageStatisticsData[]{});
	}
	
	/**
	 * 获取接口分析数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static InterfaceStatisticData[] getInterfaceSummary(String beginDate, String endDate, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(beginDate, endDate, accessToken, 
				"https://api.weixin.qq.com/datacube/getinterfacesummary");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), InterfaceStatisticData.class)
				.toArray(new InterfaceStatisticData[]{});
	}
	
	/**
	 * 获取接口分析分时数据
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws WechatExceprion 
	 */
	public static InterfaceStatisticData[] getInterfaceSummaryHour(String date, String accessToken) throws IOException, WechatExceprion{
		String result = getReturnJson(date, date, accessToken, 
				"https://api.weixin.qq.com/datacube/getinterfacesummaryhour");
		return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), InterfaceStatisticData.class)
				.toArray(new InterfaceStatisticData[]{});
	}
	
	/**
	 * 复写，减少重复代码
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @param uri
	 * @return
	 * @throws IOException 
	 * @throws WechatExceprion 
	 */
	private static String getReturnJson(String beginDate, String endDate, String accessToken, String uri) throws IOException, WechatExceprion{
		NameValuePair[] queryStr = new NameValuePair[1];
		queryStr[0] = new NameValuePair("access_token",accessToken);
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date", beginDate);
		jsonObj.put("end_date", endDate);
		String postData = jsonObj.toString();
		RequestEntity requestEntity = new StringRequestEntity(postData, IWechatConstants.CONTENT_TYPE_JSON, IWechatConstants.DEFAULT_CHARSET);
		String result = HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
        String returnCode = JSONObject.parseObject(result).getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
			return result;
		}else throw new WechatExceprion("[AccountOperations#createTempQRImage]"+result);
	}
	
}
