package com.mobile.thirdparty.wechat.model.pay;

/**
 * 微信支付，交易结果状态模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class WechatTradeStatus extends AbstractWechatPayModel {
	
	String nonce_str;
	
	/**
	 * 设备号
	 */
	String device_info;
	
	/**
	 * 用户openid
	 */
	String openid;
	
	/**
	 * 是否关注了公众号
	 */
	String is_subscribe;
	
	/**
	 * 交易类型, <code>JSAPI | NATIVE | APP</code>
	 */
	String trade_type;
	
	/**
	 * 交易状态, <ul><li>SUCCESS—支付成功 
	 * <li>REFUND—转入退款 
	 * <li>NOTPAY—未支付 
	 * <li>CLOSED—已关闭 
	 * <li>REVOKED—已撤销（刷卡支付） 
	 * <li>USERPAYING--用户支付中 
	 * <li>PAYERROR--支付失败</ul>
	 */
	String trade_state;
	
	/**
	 * 付款银行
	 */
	String bank_type;
	
	/**
	 * 总金额（单位：分）
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
	 * 现金支付币种
	 */
	String cash_fee_type;
	
	/**
	 * 微信支付订单号
	 */
	String transaction_id;
	
	/**
	 * 商户订单号
	 */
	String out_trade_no;
	
	/**
	 * 附加数据
	 */
	String attach;
	
	/**
	 * 支付完成时间
	 */
	String time_end;
	
	/**
	 * 交易状态描述
	 */
	String trade_state_desc;
	
	public WechatTradeStatus() {
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
	 * @see #openid
	 * @return openid
	 */
	public String getOpenid() {
		return openid;
	}

	/**
	 * @param openid 设置 <code>{@link #openid}</code>字段的值
	 * @see #openid
	 */
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	/**
	 * @see #is_subscribe
	 * @return is_subscribe
	 */
	public String getIs_subscribe() {
		return is_subscribe;
	}

	/**
	 * @param is_subscribe 设置 <code>{@link #is_subscribe}</code>字段的值
	 * @see #is_subscribe
	 */
	public void setIs_subscribe(String is_subscribe) {
		this.is_subscribe = is_subscribe;
	}

	/**
	 * @see #trade_type
	 * @return trade_type
	 */
	public String getTrade_type() {
		return trade_type;
	}

	/**
	 * @param trade_type 设置 <code>{@link #trade_type}</code>字段的值
	 * @see #trade_type
	 */
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	/**
	 * @see #trade_state
	 * @return trade_state
	 */
	public String getTrade_state() {
		return trade_state;
	}

	/**
	 * @param trade_state 设置 <code>{@link #trade_state}</code>字段的值
	 * @see #trade_state
	 */
	public void setTrade_state(String trade_state) {
		this.trade_state = trade_state;
	}

	/**
	 * @see #bank_type
	 * @return bank_type
	 */
	public String getBank_type() {
		return bank_type;
	}

	/**
	 * @param bank_type 设置 <code>{@link #bank_type}</code>字段的值
	 * @see #bank_type
	 */
	public void setBank_type(String bank_type) {
		this.bank_type = bank_type;
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
	 * @see #cash_fee_type
	 * @return cash_fee_type
	 */
	public String getCash_fee_type() {
		return cash_fee_type;
	}

	/**
	 * @param cash_fee_type 设置 <code>{@link #cash_fee_type}</code>字段的值
	 * @see #cash_fee_type
	 */
	public void setCash_fee_type(String cash_fee_type) {
		this.cash_fee_type = cash_fee_type;
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
	 * @see #attach
	 * @return attach
	 */
	public String getAttach() {
		return attach;
	}

	/**
	 * @param attach 设置 <code>{@link #attach}</code>字段的值
	 * @see #attach
	 */
	public void setAttach(String attach) {
		this.attach = attach;
	}

	/**
	 * @see #time_end
	 * @return time_end
	 */
	public String getTime_end() {
		return time_end;
	}

	/**
	 * @param time_end 设置 <code>{@link #time_end}</code>字段的值
	 * @see #time_end
	 */
	public void setTime_end(String time_end) {
		this.time_end = time_end;
	}

	/**
	 * @see #trade_state_desc
	 * @return trade_state_desc
	 */
	public String getTrade_state_desc() {
		return trade_state_desc;
	}

	/**
	 * @param trade_state_desc 设置 <code>{@link #trade_state_desc}</code>字段的值
	 * @see #trade_state_desc
	 */
	public void setTrade_state_desc(String trade_state_desc) {
		this.trade_state_desc = trade_state_desc;
	}
	
}
