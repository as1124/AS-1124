package com.huangjw.javax.mail.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 * 简化的邮件模型
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class EMailMessage {
	
	private String messageID;
	
	/**
	 * 在邮件列表中下标
	 */
	private int msgNum = 0;
	
	/**
	 * 主题
	 */
	private String subject;
	
	/**
	 * 收件日期
	 */
	private String receiveDate;
	
	/**
	 * 发送日期
	 */
	private String sentDate;
	
	/**
	 * 发件人
	 */
	private EMailAccount fromUser;
	
	/**
	 * 收件人
	 */
	private EMailAccount[] to;
	
	/**
	 * 抄送人
	 */
	private EMailAccount[] cc;
	
	/**
	 * 密送人
	 */
	private EMailAccount[] bcc;
	
	/**
	 * 正文内容
	 */
	private String content;
	
	/**
	 * 已读/未读
	 */
	private boolean read = false;
	
	/**
	 * 红旗标注否
	 */
	private boolean flaged = false;
	
	private boolean hasAttachment = false;
	
	private String[] attachmentName;
	
	public EMailMessage() {
	}
	
	/**
	 * @return the {@link #messageID}
	 */
	public String getMessageID() {
		return messageID;
	}

	/**
	 * @param messageID the {@link #messageID} to set
	 */
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	
	/**
	 * @return the {@link #msgNum}
	 */
	public int getMsgNum() {
		return msgNum;
	}

	/**
	 * @param msgNum the {@link #msgNum} to set
	 */
	public void setMsgNum(int msgNum) {
		this.msgNum = msgNum;
	}

	/**
	 * @return the {@link #subject}
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the {@link #subject} to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the {@link #receiveDate}
	 */
	public String getReceiveDate() {
		return receiveDate;
	}

	/**
	 * @param receiveDate the {@link #receiveDate} to set
	 */
	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = formatDate(receiveDate);
	}

	/**
	 * @return the {@link #sentDate}
	 */
	public String getSentDate() {
		return sentDate;
	}

	/**
	 * @param sentDate the {@link #sentDate} to set
	 */
	public void setSentDate(Date sentDate) {
		this.sentDate = formatDate(sentDate);
	}

	/**
	 * @return the {@link #fromUser}
	 */
	public EMailAccount getFromUser() {
		return fromUser;
	}

	/**
	 * @param fromUser the {@link #fromUser} to set
	 */
	public void setFromUser(EMailAccount fromUser) {
		this.fromUser = fromUser;
	}

	/**
	 * @return the {@link #content}
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the {@link #content} to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the {@link #read}
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * @param read the {@link #read} to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}

	/**
	 * @return the {@link #flaged}
	 */
	public boolean isFlaged() {
		return flaged;
	}

	/**
	 * @param flaged the {@link #flaged} to set
	 */
	public void setFlaged(boolean flaged) {
		this.flaged = flaged;
	}

	/**
	 * @return the {@link #hasAttachment}
	 */
	public boolean isHasAttachment() {
		return hasAttachment;
	}

	/**
	 * @param hasAttachment the {@link #hasAttachment} to set
	 */
	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	/**
	 * @return the {@link #attachmentName}
	 */
	public String[] getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName the {@link #attachmentName} to set
	 */
	public void setAttachmentName(String[] attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the {@link #to}
	 */
	public EMailAccount[] getTo() {
		return to;
	}

	/**
	 * @return the {@link #cc}
	 */
	public EMailAccount[] getCc() {
		return cc;
	}

	/**
	 * @return the {@link #bcc}
	 */
	public EMailAccount[] getBcc() {
		return bcc;
	}

	/**
	 * @param to the {@link #to} to set
	 */
	public void setTo(Address[] to) {
		if(to==null || to.length<=0){
			return;
		}
		this.to = new EMailAccount[to.length];
		for(int i=0; i<to.length; i++){
			String name = ((InternetAddress)to[i]).getPersonal();
			String address = ((InternetAddress)to[i]).getAddress();
			this.to[i] = new EMailAccount(name, address);
		}
	}

	/**
	 * @param bcc the {@link #bcc} to set
	 */
	public void setBcc(Address[] bcc) {
		if(bcc==null || bcc.length<=0){
			return;
		}
		this.bcc = new EMailAccount[bcc.length];
		for(int i=0; i<bcc.length; i++){
			String name = ((InternetAddress)bcc[i]).getPersonal();
			String address = ((InternetAddress)bcc[i]).getAddress();
			this.bcc[i] = new EMailAccount(name, address);
		}
	}

	/**
	 * @param cc the {@link #cc} to set
	 */
	public void setCc(Address[] cc) {
		if(cc==null || cc.length<=0){
			return;
		}
		this.cc = new EMailAccount[cc.length];
		for(int i=0; i<cc.length; i++){
			String name = ((InternetAddress)cc[i]).getPersonal();
			String address = ((InternetAddress)cc[i]).getAddress();
			this.cc[i] = new EMailAccount(name, address);
		}
	}

	private String formatDate(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(date);
	}
	
	public static void main(String[] args){
		
	}
	
}

