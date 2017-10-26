package com.mobile.thirdparty.wechat.model.pay;

/**
 * 微信支付，申请退款响应数据模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatRefundResponse extends AbstractWechatPayModel {

	/**
	 * 设备号
	 */
	String device_info;
	
	/**
	 * 随机字符串
	 */
	String nonce_str;
	
	/**
	 * 微信订单号
	 */
	String transaction_id;
	
	/**
	 * 商户订单号
	 */
	String out_trade_no;
	
	/**
	 * 商户退款单号
	 */
	String out_refund_no;
	
	/**
	 * 微信退款单号
	 */
	String refund_id;
	
	/**
	 * 退款渠道<br/>
	 * ORIGINAL—原路退款<br/>
	 * BALANCE—退回到余额
	 */
	String refund_channel;
	
	/**
	 * 退款金额
	 */
	int refund_fee;
	
	/**
	 * 订单总金额
	 */
	int total_fee;
	
	/**
	 * 币种
	 */
	String fee_type;
	
	/**
	 * 现金支付金额
	 */
	int cash_fee;
	
	/**
	 * 现金退款金额
	 */
	int cash_refund_fee;
	
	public WechatRefundResponse() {
	}

	/**
	 * @see #device_info
	 * @return device_info
	 */
	public String getDevice_info() {
		return device_info;
	}

	/**
	 * @param device_info 设置 <code>{@link #device_info}</code>字段的值
	 * @see #device_info
	 */
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}



	/**
	 * @see #nonce_str
	 * @return nonce_str
	 */
	public String getNonce_str() {
		return nonce_str;
	}

	/**
	 * @param nonce_str 设置 <code>{@link #nonce_str}</code>字段的值
	 * @see #nonce_str
	 */
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	/**
	 * @see #transaction_id
	 * @return transaction_id
	 */
	public String getTransaction_id() {
		return transaction_id;
	}

	/**
	 * @param transaction_id 设置 <code>{@link #transaction_id}</code>字段的值
	 * @see #transaction_id
	 */
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	/**
	 * @see #out_trade_no
	 * @return out_trade_no
	 */
	public String getOut_trade_no() {
		return out_trade_no;
	}

	/**
	 * @param out_trade_no 设置 <code>{@link #out_trade_no}</code>字段的值
	 * @see #out_trade_no
	 */
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	/**
	 * @see #out_refund_no
	 * @return out_refund_no
	 */
	public String getOut_refund_no() {
		return out_refund_no;
	}

	/**
	 * @param out_refund_no 设置 <code>{@link #out_refund_no}</code>字段的值
	 * @see #out_refund_no
	 */
	public void setOut_refund_no(String out_refund_no) {
		this.out_refund_no = out_refund_no;
	}

	/**
	 * @see #refund_id
	 * @return refund_id
	 */
	public String getRefund_id() {
		return refund_id;
	}

	/**
	 * @param refund_id 设置 <code>{@link #refund_id}</code>字段的值
	 * @see #refund_id
	 */
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}

	/**
	 * @see #refund_channel
	 * @return refund_channel
	 */
	public String getRefund_channel() {
		return refund_channel;
	}

	/**
	 * @param refund_channel 设置 <code>{@link #refund_channel}</code>字段的值
	 * @see #refund_channel
	 */
	public void setRefund_channel(String refund_channel) {
		this.refund_channel = refund_channel;
	}

	/**
	 * @see #refund_fee
	 * @return refund_fee
	 */
	public int getRefund_fee() {
		return refund_fee;
	}

	/**
	 * @param refund_fee 设置 <code>{@link #refund_fee}</code>字段的值
	 * @see #refund_fee
	 */
	public void setRefund_fee(int refund_fee) {
		this.refund_fee = refund_fee;
	}

	/**
	 * @see #total_fee
	 * @return total_fee
	 */
	public int getTotal_fee() {
		return total_fee;
	}

	/**
	 * @param total_fee 设置 <code>{@link #total_fee}</code>字段的值
	 * @see #total_fee
	 */
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	/**
	 * @see #fee_type
	 * @return fee_type
	 */
	public String getFee_type() {
		return fee_type;
	}

	/**
	 * @param fee_type 设置 <code>{@link #fee_type}</code>字段的值
	 * @see #fee_type
	 */
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	/**
	 * @see #cash_fee
	 * @return cash_fee
	 */
	public int getCash_fee() {
		return cash_fee;
	}

	/**
	 * @param cash_fee 设置 <code>{@link #cash_fee}</code>字段的值
	 * @see #cash_fee
	 */
	public void setCash_fee(int cash_fee) {
		this.cash_fee = cash_fee;
	}

	/**
	 * @see #cash_refund_fee
	 * @return cash_refund_fee
	 */
	public int getCash_refund_fee() {
		return cash_refund_fee;
	}

	/**
	 * @param cash_refund_fee 设置 <code>{@link #cash_refund_fee}</code>字段的值
	 * @see #cash_refund_fee
	 */
	public void setCash_refund_fee(int cash_refund_fee) {
		this.cash_refund_fee = cash_refund_fee;
	}
	
}
