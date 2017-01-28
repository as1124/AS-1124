package com.primeton.mobile.thirdparty.wechat.pay;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import com.primeton.mobile.thirdparty.access.HttpExecuter;
import com.primeton.mobile.thirdparty.wechat.IWechatConstants;
import com.primeton.mobile.thirdparty.wechat.model.pay.PayMetadata;
import com.primeton.mobile.thirdparty.wechat.model.pay.PrePayInfo;
import com.primeton.mobile.thirdparty.wechat.model.pay.WechatTradeStatus;

/**
 * 
 * 微信支付
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 * 
 */
public class WechatPay {

	/**
	 * 查询微信订单状态, <code>tradeNo、transactionID</code> 不能同时为空
	 * 
	 * @param tradeNo
	 *            商户订单号
	 * @param transactionID
	 *            微信订单号
	 * @return
	 */
	public static WechatTradeStatus queryTradeStatus(String tradeNo, String transactionID, PayMetadata metadata) {
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		if (StringUtils.isNotBlank(tradeNo))
			nodes.add("out_trade_no=" + tradeNo);

		if (StringUtils.isNotBlank(transactionID))
			nodes.add("transaction_id=" + transactionID);

		// 随机数
		String nonceStr = generateNonceStr();
		nodes.add("nonce_str=" + nonceStr);

		String[] array = nodes.toArray(new String[] {});
		String paySecret = metadata.getPaySecret();

		String sign = generateSign(array, paySecret, IWechatConstants.DEFAULT_CHARSET);
		String postContent = getPostContent(array, sign);
		String requestURL = "https://api.mch.weixin.qq.com/pay/orderquery";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(postContent, ContentType.create(IWechatConstants.CONTENT_TYPE_JSON, 
					IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr,
				requestEntity);

		JSONObject json = parseResult2JSON(result);
		WechatTradeStatus instance = JSONObject.parseObject(
				json.toJSONString(), WechatTradeStatus.class);
		instance.setDataMap(json.toJSONString());
		return instance;
	}

	/**
	 * 请求接口
	 * 
	 * @param tradeNo
	 *            商户订单号
	 * @param body
	 *            订单描述信息
	 * @param totalCost
	 *            总金额
	 * @param deviceIP
	 *            设备IP
	 * @param expirMinutes
	 *            订单超时时间
	 * @return
	 */
	public static PrePayInfo getPrePayInfo(PayMetadata metadata, String tradeNo, String body,
			int totalCost, String deviceIP, int expirMinutes) {
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		nodes.add("out_trade_no=" + tradeNo);
		nodes.add("body=" + body);
		nodes.add("total_fee=" + totalCost);
		nodes.add("spbill_create_ip=" + deviceIP);

		/*
		 * //可选 String deviceInfo = ""; nodes.add("device_info="+deviceInfo);
		 */

		// 随机数
		String nonceStr = generateNonceStr();
		nodes.add("nonce_str=" + nonceStr);

		/*
		 * //商品详细 String detail = ""; nodes.add("detail="+detail);
		 */

		/*
		 * //附加数据 String attach = ""; nodes.add("attach="+attach);
		 */

		// 币种
		String feeType = "CNY";
		nodes.add("fee_type=" + feeType);

		// 交易起始时间
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		String timeStart = (new SimpleDateFormat("yyyyMMddHHmmss"))
				.format(date);
		nodes.add("time_start=" + timeStart);

		// 订单失效时间
		Date expirDate = new Date(currentTime + expirMinutes * 60 * 1000);
		String timeExpire = (new SimpleDateFormat("yyyyMMddHHmmss"))
				.format(expirDate);
		nodes.add("time_expire=" + timeExpire);

		/*
		 * //商品标记 String goodsTag = ""; nodes.add("goods_tag"+goodsTag);
		 */

		String notifyURL = metadata.getCallbackURL();
		nodes.add("notify_url=" + notifyURL);

		// 交易类型, 必须
		String tradeType = "APP";
		nodes.add("trade_type=" + tradeType);

		/*
		 * //商品ID String productID = ""; nodes.add("product_id="+productID);
		 * 
		 * //指定支付方式 String limitPay = ""; nodes.add("limit_pay="+limitPay);
		 * 
		 * //用户标识 String openID = ""; array.add("openid="+openID);
		 */

		String[] array = nodes.toArray(new String[] {});
		String key = metadata.getPaySecret();
		String sign = generateSign(array, key, IWechatConstants.DEFAULT_CHARSET);
		String postContent = getPostContent(array, sign);

		String requestURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(postContent, ContentType.create(IWechatConstants.CONTENT_TYPE_XML, 
				IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr, requestEntity);

		JSONObject json = parseResult2JSON(result);

		PrePayInfo instance = JSONObject.parseObject(json.toJSONString(),
				PrePayInfo.class);
		instance.setDataMap(json.toJSONString());

		return instance;

	}

	/**
	 * 调起微信支付
	 * 
	 * @param tradeNo
	 * @param body
	 * @param totalCost
	 * @param deviceIP
	 * @param expirMinutes
	 */
	public static HashMap<String, String> pay(PayMetadata metadata, String tradeNo, String body,
			int totalCost, String deviceIP, int expirMinutes) {
		HashMap<String, String> result = new HashMap<String, String>();
		ArrayList<String> nodes = new ArrayList<String>();

		String appid = metadata.getAppID();
		String partnerid = metadata.getMchID();

		nodes.add("appid=" + appid);
		nodes.add("partnerid=" + partnerid);
		String prepayid = getPrePayInfo(metadata, tradeNo, body, totalCost, deviceIP,
				expirMinutes).getPrepay_id();
		if (StringUtils.isBlank(prepayid)) {
			try {
				throw new Exception("无法获取预支付订单信息");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
		String sign = generateSign(array, paySecret, IWechatConstants.DEFAULT_CHARSET);
		nodes.add("sign=" + sign);

		result.put("apppId", appid);
		result.put("partnerId", partnerid);
		result.put("prepayId", prepayid);
		result.put("packageValue", "Sign=WXPay");
		result.put("nonceStr", noncestr);
		result.put("timeStamp", timeStamp);
		result.put("sign", sign);

		return result;
	}

	/**
	 * 生成支付签名
	 * 
	 * @param array
	 *            节点数组
	 * @param key
	 *            商户支付密钥
	 * @param charset 编码格式
	 * @return
	 */
	public static String generateSign(String[] array, String key, String charset) {
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
			sign = DigestUtils.md5Hex(tempStr.getBytes("UTF-8")).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return sign;
	}

	/**
	 * 生成随机字符串
	 * 
	 * @return
	 */
	public static String generateNonceStr() {
		return RandomStringUtils
				.random(20,
						"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	}

	/**
	 * 拼接xml报文
	 * 
	 * @return
	 */
	public static String getPostContent(String[] list, String sign) {
		String xmlContent = "<xml>";
		for (String str : list) {
			String[] tag = str.split("[=]");
			xmlContent = xmlContent + "<" + tag[0] + ">" + tag[1] + "</"
					+ tag[0] + ">";
		}
		xmlContent = xmlContent + "<sign>" + sign + "</sign></xml>";

		return xmlContent;
	}

	/**
	 * 将xml返回结果转成java对象
	 * 
	 * @param result
	 * @return
	 * @return
	 */
	public static JSONObject parseResult2JSON(String result) {
		SAXReader reader = new SAXReader(false);
		JSONObject json = new JSONObject();
		try {
			Document doc = reader.read(new ByteArrayInputStream(result.getBytes(IWechatConstants.DEFAULT_CHARSET)));
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

	/**
	 * 
	 * 以下情况需要调用关单接口：商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；系统下单后，
	 * 用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。<br>
	 * <strong>注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟</strong>
	 * 
	 * @param tradeNo
	 *            商户订单号
	 */
	public static String closeOrder(String tradeNo, PayMetadata metadata) {
		ArrayList<String> nodes = new ArrayList<String>();
		nodes.add("appid=" + metadata.getAppID());
		nodes.add("mch_id=" + metadata.getMchID());
		if (StringUtils.isNotBlank(tradeNo))
			nodes.add("out_trade_no=" + tradeNo);

		// 随机数
		String nonceStr = generateNonceStr();
		nodes.add("nonce_str=" + nonceStr);

		String[] array = nodes.toArray(new String[] {});

		String sign = generateSign(array, metadata.getPaySecret(), IWechatConstants.DEFAULT_CHARSET);
		String postContent = getPostContent(array, sign);
		String requestURL = "https://api.mch.weixin.qq.com/pay/closeorder";
		ArrayList<NameValuePair> queryStr = new ArrayList<NameValuePair>();
		StringEntity requestEntity = new StringEntity(postContent, ContentType.create(IWechatConstants.CONTENT_TYPE_JSON,
					IWechatConstants.DEFAULT_CHARSET));
		String result = HttpExecuter.executePostAsString(requestURL, queryStr,
				requestEntity);

		return result;
	}

}
