package com.mobile.thirdparty.wechat;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONObject;
import com.eos.system.annotation.Bizlet;
import com.mobile.thirdparty.access.AbstractAccessToken;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.wechat.model.statistics.InterfaceStatisticData;
import com.mobile.thirdparty.wechat.model.statistics.MessageStatisticsData;
import com.mobile.thirdparty.wechat.model.statistics.NewsStatisticsData;
import com.mobile.thirdparty.wechat.model.statistics.UserStatisticsData;

/**
 * WeChat 数据分析API.<br>
 * <li>日期串统一格式： yyyy-MM-dd
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
@Bizlet
public class DataAnalyzeOperations {

	/**
	 * 获取用户增减数据, 最大时间跨度是7天
	 * @param beginDate 
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<UserStatisticsData> getUserSummary(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getusersummary");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(json.getString("list"), UserStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取累计用户数据，最大时间跨度是7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<UserStatisticsData> getUserCumulate(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(),
				"https://api.weixin.qq.com/datacube/getusercumulate");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(json.getString("list"), UserStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文群发每日数据
	 * @param date
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getArticleSummary(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getarticlesummary");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文群发总数据
	 * @param date
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getArticleTotal(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getarticletotal");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文统计数据, 最大时间跨度为3天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getUserRead(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getuserread");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文统计分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getUserReadByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getuserreadhour");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文分享转发数据，最大时间跨度7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getUserShare(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getusershare");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取图文分享分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<NewsStatisticsData> getUserShareByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getusersharehour");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), NewsStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送概况数据，最大时间跨度7天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgSummary(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsg");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送分时数据
	 * @param date
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgSummaryByHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsghour");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送周数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgSummaryByWeek(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsgweek");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送月数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgSummaryByMonth(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsgmonth");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送分布数据, 最大时间跨度15天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgDistSummary(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsgdist");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送分布周数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgDistSummaryByWeek(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsgdistweek");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取消息发送分布月数据，最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<MessageStatisticsData> getMsgDistSummaryByMonth(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getupstreammsgdistmonth");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), MessageStatisticsData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取接口分析数据, 最大时间跨度30天
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<InterfaceStatisticData> getInterfaceSummary(AbstractAccessToken token, String beginDate, String endDate) {
		String result = getReturnJson(beginDate, endDate, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getinterfacesummary");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), InterfaceStatisticData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 获取接口分析分时数据
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @return
	 */
	@Bizlet
	public List<InterfaceStatisticData> getInterfaceSummaryHour(AbstractAccessToken token, String date) {
		String result = getReturnJson(date, date, token.getAccess_token(), 
				"https://api.weixin.qq.com/datacube/getinterfacesummaryhour");
		JSONObject json = JSONObject.parseObject(result);
        String returnCode = json.getString(IWechatConstants.ERROR_CODE);
        if(returnCode == null || IWechatConstants.RETURN_CODE_SUCCESS.equals(returnCode)){
        	return JSONObject.parseArray(JSONObject.parseObject(result).getString("list"), InterfaceStatisticData.class);
		} else {
			System.err.println(IWechatConstants.MSG_TAG+result);
			return null;
		}
	}
	
	/**
	 * 复写，减少重复代码
	 * @param beginDate
	 * @param endDate
	 * @param accessToken
	 * @param uri
	 * @return
	 */
	private String getReturnJson(String beginDate, String endDate, String accessToken, String uri) {
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		queryStr.add(new BasicNameValuePair("access_token", accessToken));
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("begin_date", beginDate);
		jsonObj.put("end_date", endDate);
		String postData = jsonObj.toString();
		StringEntity requestEntity = new StringEntity(postData, ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
				IWechatConstants.DEFAULT_CHARSET));
		return HttpExecuter.executePostAsString(uri, queryStr, requestEntity);
	}
	
}
