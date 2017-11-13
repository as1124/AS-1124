package com.mobile.thirdparty.wechat;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSONObject;
import com.mobile.thirdparty.access.HttpExecuter;
import com.mobile.thirdparty.access.exception.ThirdPartyRequestExceprion;
import com.mobile.thirdparty.wechat.model.pay.WechatPayMetadata;
import com.mobile.thirdparty.wechat.model.pay.WechatPrePayInfo;
import com.mobile.thirdparty.wechat.model.pay.WechatRefundResponse;
import com.mobile.thirdparty.wechat.model.pay.WechatTradeStatus;

/**
 * 
 * 微信支付操作接口，包含了APP支付以及公众号支付。
 * 部分接口调用需要安装微信商户操作证书，请前往微信商户平台进行下载安装
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class WechatPayOperations {
	
	/**
	 * 代表微信APP支付
	 */
	public static String WX_TRADE_TYPE_APP = "APP";
	
	/**
	 * 公众号支付
	 */
	public static String WX_TRADE_TYPE_JSAPI = "JSAPI";
	
	/**
	 * 原生扫码支付
	 */
	public static String WX_TRADE_TYPE_NATIVE = "NATIVE";
	
	/**
	 * 刷卡支付
	 */
	public static String WX_TRADE_TYPE_MICROPAY = "MICROPAY";
	
	/**
	 * 微信支付统一下单接口。
	 * 请求微信后台生成预支付订单信息<br/>
	 * 请参考<a href="https://pay.weixin.qq.com/wiki/doc/api/index.html">微信支付官方文档</a>
	 * <br/>网页支付时，需要通过参数<code>otherNodes</code>传递 <code>openid=userOpenid</code>
	 * 
	 * @param metadata
	 * @param tradeNo 商户订单号
	 * @param body 订单描述信息
	 * @param tradeType 交易类型, {@link #WX_TRADE_TYPE_APP},{@link #WX_TRADE_TYPE_JSAPI},
	 *            {@link #WX_TRADE_TYPE_MICROPAY},{@link #WX_TRADE_TYPE_NATIVE}
	 * @param totalCost 总金额(单位：分）
	 * @param deviceIP 移动设备终端IP
	 * @param expirMinutes 订单超时时间
	 * @param otherNodes 其他字段信息, 可以为空, 字段可以参考官方统一下单文档
	 * @return
	 */
	public WechatPrePayInfo generatePrePayInfo(WechatPayMetadata metadata, String tradeNo, String body, String tradeType, 
			int totalCost, String deviceIP, int expirMinutes, Map<String, String> otherNodes) {
		String requestURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		nodes.add("nonce_str=" + generateNonceStr());
		nodes.add("body=" + body);
		nodes.add("out_trade_no=" + tradeNo);
		
		// 币种
		nodes.add("fee_type=" + "CNY");
		nodes.add("total_fee=" + totalCost);
		nodes.add("spbill_create_ip=" + deviceIP);
		
		// 交易起始时间, yyyyMMddHHmmss
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		String timeStart = (new SimpleDateFormat("yyyyMMddHHmmss")).format(date);
		nodes.add("time_start=" + timeStart);
		
		// 订单失效时间
		Date expirDate = new Date(currentTime + expirMinutes * 60 * 1000);
		String timeExpire = (new SimpleDateFormat("yyyyMMddHHmmss")).format(expirDate);
		nodes.add("time_expire=" + timeExpire);
		
		//下单成功后回调地址
		nodes.add("notify_url=" + metadata.getCallbackURL());
		
		// 交易类型, 必须
		nodes.add("trade_type=" + tradeType);

		if(otherNodes!=null && otherNodes.size()>0){
			Iterator<String> it = otherNodes.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				nodes.add(key+"="+otherNodes.get(key));
			}
		}
		
		String[] array = nodes.toArray(new String[] {});
		String key = metadata.getPaySecret();
		String sign = generateWXPaySign(array, key, WechatConstants.CHARSET_UTF8);
		String postContent = getPostContent(array, sign);

		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(postContent, 
				ContentType.create(WechatConstants.CONTENT_TYPE_XML, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);
		JSONObject json = parseXml2JSON(result);
		WechatPrePayInfo instance = JSONObject.parseObject(json.toJSONString(), WechatPrePayInfo.class);
		instance.setDataMap(json.toJSONString());

		return instance;
	}
	
	/**
	 * 调起微信APP支付, <strong>此方法会自动调用统一下单接口</strong>
	 * 
	 * @param metadata
	 * @param tradeNo 商户订单编号
	 * @param body 订单描述信息
	 * @param totalCost 总金额(单位: 分)
	 * @param deviceIP 终端设备IP
	 * @param expirMinutes 订单有效时间
	 * @param otherNodes 其他非必要字段信息,可以为空, 字段可以参考官方统一下单文档
	 * @return 调起微信APP支付所包含所必须的字段数据，<br/>
	 * key包括 <code>appid, partnerid, prepayid, package, noncestr, timestamp, sign</code>
	 * @throws ThirdPartyRequestExceprion 
	 */
	public HashMap<String, String> appPay(WechatPayMetadata metadata, String tradeNo, String body, int totalCost, 
			String deviceIP, int expirMinutes, Map<String, String> otherNodes) throws ThirdPartyRequestExceprion {
		String tradeType = WX_TRADE_TYPE_APP;
		HashMap<String, String> result = new HashMap<String, String>();
		ArrayList<String> nodes = new ArrayList<String>();

		String appid = metadata.getAppID();
		String partnerid = metadata.getMchID();

		nodes.add("appid=" + appid);
		nodes.add("partnerid=" + partnerid);
		String prepayid = generatePrePayInfo(metadata, tradeNo, body, tradeType, totalCost, deviceIP, expirMinutes, otherNodes)
				.getPrepay_id();
		if (StringUtils.isBlank(prepayid)) {
			throw new ThirdPartyRequestExceprion("无法获取预支付订单信息");
		}
		nodes.add("prepayid=" + prepayid);
		nodes.add("package=Sign=WXPay");
		
		String noncestr = generateNonceStr();
		nodes.add("noncestr=" + noncestr);

		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		String timeStamp = (new SimpleDateFormat("yyMMddHHmm")).format(date);
		nodes.add("timestamp=" + timeStamp);

		String[] array = nodes.toArray(new String[] {});
		String paySecret = metadata.getPaySecret();
		String sign = generateWXPaySign(array, paySecret, WechatConstants.CHARSET_UTF8);

		result.put("appid", appid);
		result.put("partnerid", partnerid);
		result.put("prepayid", prepayid);
		result.put("package", "Sign=WXPay");
		result.put("noncestr", noncestr);
		result.put("timestamp", timeStamp);
		result.put("sign", sign);

		return result;
	}
	
	/**
	 * 调起微信微信网页支付, <strong>此方法会自动调用统一下单接口</strong>
	 * 
	 * @param metadata
	 * @param tradeNo 商户订单编号
	 * @param body 订单描述信息
	 * @param totalCost 总金额(单位: 分)
	 * @param deviceIP 终端设备IP
	 * @param expirMinutes 订单有效时间
	 * @param otherNodes 其他非必要字段信息,可以为空, 字段可以参考官方统一下单文档
	 * @return 返回调起微信网页支付所必须的字段数据，<br/>
	 * key包括<code> appId, timeStamp, nonceStr, package, signType, paySign</code>
	 * @throws ThirdPartyRequestExceprion 
	 */
	public HashMap<String, String> h5Pay(WechatPayMetadata metadata, String tradeNo, String body, int totalCost, 
			String deviceIP, int expirMinutes, String openid) throws ThirdPartyRequestExceprion {
		String tradeType = WX_TRADE_TYPE_JSAPI;
		HashMap<String, String> result = new HashMap<String, String>();
		ArrayList<String> nodes = new ArrayList<String>();
		Map<String, String> otherNodes = new HashMap<String, String>();
		otherNodes.put("openid", openid);
		
		String appid = metadata.getAppID();
		String partnerid = metadata.getMchID();

		nodes.add("appid=" + appid);
		nodes.add("partnerid=" + partnerid);
		String prepayid = generatePrePayInfo(metadata, tradeNo, body, tradeType, totalCost, deviceIP, expirMinutes, otherNodes)
				.getPrepay_id();
		if (StringUtils.isBlank(prepayid)) {
			throw new ThirdPartyRequestExceprion("无法获取预支付订单信息");
		}
		
		nodes.clear();
		nodes.add("appId=" + appid);
		long timeStamp = System.currentTimeMillis()/1000;
		nodes.add("timeStamp=" + timeStamp);
		String nonceStr = generateNonceStr();
		nodes.add("nonceStr=" + nonceStr);
		nodes.add("package=prepay_id=" + prepayid);
		nodes.add("signType=MD5");
		String[] array = nodes.toArray(new String[] {});
		String paySecret = metadata.getPaySecret();
		String sign = generateWXPaySign(array, paySecret, WechatConstants.CHARSET_UTF8);

		result.put("appId", appid);
		result.put("timeStamp", timeStamp+"");
		result.put("nonceStr", nonceStr);
		result.put("package", "prepay_id=" + prepayid);
		result.put("signType", "MD5");
		result.put("paySign", sign);

		return result;
	}
	
//	/**
//	 * 微信支付，扫码支付
//	 * @param metadata
//	 * @param productID
//	 */
//	public void scanCodePay(WechatPayMetadata metadata, String productID){
//		String wechatPayURL = "wexin://wxpay/bizpayurl?";
//		ArrayList<String> nodes = new ArrayList<String>();
//
//		String appid = metadata.getAppID();
//		String partnerid = metadata.getMchID();
//		long timeStamp = System.currentTimeMillis()/1000;
//		String nonceStr = generateNonceStr();
//		
//		nodes.add("appid=" + appid);
//		nodes.add("mch_id=" + partnerid);
//		nodes.add("product_id=" + productID);
//		nodes.add("time_stamp=" + timeStamp);
//		nodes.add("nonce_str=" + nonceStr);
//		
//		String[] array = nodes.toArray(new String[] {});
//		String paySecret = metadata.getPaySecret();
//		String sign = generateWXPaySign(array, paySecret, IWechatConstants.DEFAULT_CHARSET);
//		
//		
//	}
	
	/**
	 * 根据商户订单号查询微信订单状态
	 * 
	 * @param tradeNo 商户订单号
	 * @param metadata 微信支付相关参数
	 * @return
	 */
	public WechatTradeStatus getStatusByTradeNo(String tradeNo, WechatPayMetadata metadata){
		return this.queryTradeStatus(tradeNo, null, metadata);
	}
	
	/**
	 * 根据微信交易单号查询微信订单状态
	 * 
	 * @param transactionID 微信的订单号（交易时微信生成的，不是商户自定义的）
	 * @param metadata 微信支付相关的参数
	 * @return
	 */
	public WechatTradeStatus getStatusByTransactionID(String transactionID, WechatPayMetadata metadata){
		return this.queryTradeStatus(null, transactionID, metadata);
	}
	
	/**
	 * 查询微信订单状态, <code>tradeNo、transactionID</code> 不能同时为空
	 * 
	 * @param tradeNo 商户订单号
	 * @param transactionID 微信订单号
	 * @param metadata
	 * @return
	 */
	private WechatTradeStatus queryTradeStatus(String tradeNo, String transactionID, WechatPayMetadata metadata) {
		String requestURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		if (StringUtils.isNotBlank(tradeNo))
			nodes.add("out_trade_no=" + tradeNo);
		if (StringUtils.isNotBlank(transactionID))
			nodes.add("transaction_id=" + transactionID);
		nodes.add("nonce_str=" + generateNonceStr());

		String[] array = nodes.toArray(new String[] {});
		String paySecret = metadata.getPaySecret();

		String sign = generateWXPaySign(array, paySecret, WechatConstants.CHARSET_UTF8);
		String postContent = getPostContent(array, sign);
		
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(postContent, 
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);
		JSONObject json = parseXml2JSON(result);
		WechatTradeStatus instance = JSONObject.parseObject(json.toJSONString(), WechatTradeStatus.class);
		instance.setDataMap(json.toJSONString());
		return instance;
	}

	/**
	 * 关闭微信订单
	 * <pre>
	 * 以下情况需要调用关单接口：
	 * 1、商户订单支付失败需要生成新单号重新发起支付, 要对原订单号调用关单，避免重复支付
	 * 2、系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口
	 * <strong>注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟</strong></pre>
	 * 
	 * @param tradeNo 商户订单号
	 * @param metadata
	 * @return 
	 */
	public boolean closeOrder(String tradeNo, WechatPayMetadata metadata) {
		String requestURL = "https://api.mch.weixin.qq.com/pay/closeorder";
		
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		if (StringUtils.isNotBlank(tradeNo))
			nodes.add("out_trade_no=" + tradeNo);

		nodes.add("nonce_str=" + generateNonceStr());

		String[] array = nodes.toArray(new String[] {});

		String sign = generateWXPaySign(array, metadata.getPaySecret(), WechatConstants.CHARSET_UTF8);
		
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(getPostContent(array, sign), 
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);
		JSONObject json = parseXml2JSON(result);
		if("SUCCESS".equals(json.getString("return_code")) &&
				(json.getString("err_code")==null || json.getString("err_code").trim().equals(""))){
			return true;
		} else {
			System.err.println(WechatConstants.MSG_TAG+result);
			return false;
		}
	}

	/**
	 * 申请微信支付退款
	 * @param metadata
	 * @param tradeNO 商户订单号, 不能为空
	 * @param refundNO 商户退款单号，不能为空
	 * @param totalFee 订单总金额
	 * @param refundFee 退款金额
	 * @return
	 */
	public WechatRefundResponse requestRefund(WechatPayMetadata metadata, String tradeNO, String refundNO, int totalFee, int refundFee){
		String requestURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		nodes.add("nonce_str=" + generateNonceStr());
		nodes.add("out_trade_no=" + tradeNO);
		nodes.add("out_refund_no=" + refundNO);
		nodes.add("total_fee=" + totalFee);
		nodes.add("refund_fee=" + refundFee);
		nodes.add("op_user_id=" + metadata.getMchID());
		
		String[] array = nodes.toArray(new String[] {});

		String sign = generateWXPaySign(array, metadata.getPaySecret(), WechatConstants.CHARSET_UTF8);
		
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(getPostContent(array, sign), 
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);
		JSONObject json = parseXml2JSON(result);
		String jsonData = json.toJSONString();
		WechatRefundResponse returnObj = JSONObject.parseObject(jsonData, WechatRefundResponse.class);
		returnObj.setDataMap(jsonData);
		return returnObj;
	}
	
	/**
	 * 根据商户订单号查询退款状态
	 * @param metadata
	 * @param tradeNO 商户订单号
	 * @return
	 */
	public JSONObject getRefundStatusByTradeNO(WechatPayMetadata metadata, String tradeNO){
		return this.getRefundStatus(metadata, "out_trade_no="+tradeNO);
	}
	
	/**
	 * 根据商户退款单号查询退款状态
	 * @param metadata
	 * @param refundNO 商户退款单号
	 * @return
	 */
	public JSONObject getRefundStatusByRefundNO(WechatPayMetadata metadata, String refundNO){
		return this.getRefundStatus(metadata, "out_refund_no="+refundNO);
	}
	
	/**
	 * 根据微信退款单id查询退款状态
	 * @see #requestRefund(WechatPayMetadata, String, String, int, int)
	 * @param metadata
	 * @param WXRefundID 通过{@link #requestRefund(WechatPayMetadata, String, String, int, int)}申请退款接口获取
	 * @return
	 */
	public JSONObject getRefundStatusByWXRefundID(WechatPayMetadata metadata, String WXRefundID){
		return this.getRefundStatus(metadata, "refund_id="+WXRefundID);
	}
	
	private JSONObject getRefundStatus(WechatPayMetadata metadata, String nodeStr){
		String requestURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
		
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		nodes.add("nonce_str=" + generateNonceStr());
		nodes.add(nodeStr);
		
		String[] array = nodes.toArray(new String[] {});

		String sign = generateWXPaySign(array, metadata.getPaySecret(), WechatConstants.CHARSET_UTF8);
		
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(getPostContent(array, sign), 
				ContentType.create(WechatConstants.CONTENT_TYPE_JSON, WechatConstants.CHARSET_UTF8));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);
		JSONObject json = parseXml2JSON(result);
		return JSONObject.parseObject(json.toJSONString());
	}
	
	/**
	 * 生成支付签名
	 * 
	 * @param array 节点数组
	 * @param key 商户支付密钥
	 * @param charset 编码格式
	 * @return
	 */
	public static String generateWXPaySign(String[] array, String key, String charset) {
		Arrays.sort(array, new Comparator<String>() {

			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		String keyStr = "key=" + key;
		String tempStr = "";
		for (String str : array) {
			tempStr = tempStr + str + "&";
		}
		tempStr = tempStr + keyStr;

		String sign = "";
		try {
			sign = DigestUtils.md5Hex(tempStr.getBytes(charset)).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return sign;
	}

	/**
	 * 生成微信支付随机字符串参数
	 * 
	 * @return
	 */
	public static String generateNonceStr() {
		return RandomStringUtils.random(20,
						"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	/**
	 * 拼接节点生成支付xml报文
	 * 
	 * @param list 节点数组，eg：<code>appid=1322120</code>
	 * @param sign
	 * @return
	 */
	private static String getPostContent(String[] list, String sign) {
		String xmlContent = "<xml>";
		for (String str : list) {
			String[] tag = str.split("[=]");
			xmlContent = xmlContent + "<" + tag[0] + ">" + tag[1] + "</" + tag[0] + ">";
		}
		xmlContent = xmlContent + "<sign>" + sign + "</sign></xml>";
		return xmlContent;
	}

	/**
	 * 将返回的xml报文转成{@link JSONObject}对象
	 * 
	 * @param result
	 * @return
	 */
	public static JSONObject parseXml2JSON(String result) {
		SAXReader reader = new SAXReader(false);
		JSONObject json = new JSONObject();
		try {
			Document doc = reader.read(new ByteArrayInputStream(result.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = doc.getRootElement();
			List<?> returnNodes = root.elements();
			for (int i = 0; i < returnNodes.size(); i++) {
				Element child = (Element) returnNodes.get(i);
				json.put(child.getName(), child.getData());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return json;
	}

}
