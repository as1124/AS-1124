package com.mobile.thirdparty.wechat.events.menu;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.mobile.thirdparty.wechat.WechatConstants;

/**
 * 微信发图操作按钮触发的事件
 * 
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ChoosePhotoMenuEvent extends AbstractWechatMenuEvent {
	
	/**
	 * 需要发送的图片数量
	 */
	int count;
	
	/**
	 * 图片的MD5值，开发者若需要，可用于验证接收到的图片
	 */
	List<String> picMd5Sum;
	
	public ChoosePhotoMenuEvent(String xmlContent) {
		super("", xmlContent);
	}
	
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(
					WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			long createTime = Long.parseLong(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setEvent(root.element("Event").getText());
			this.setEventKey(root.element("EventKey").getText());
			Element info = root.element("SendPicsInfo");
			this.setCount( Integer.parseInt(info.element("Count").getText()) );
			if(this.picMd5Sum == null)
				this.picMd5Sum = new ArrayList<String>();
			else this.picMd5Sum.clear();
			Iterator<?> items = info.element("PicList").elementIterator();
			while(items.hasNext()){
				Element item = (Element) items.next();
				this.picMd5Sum.add( item.element("PicMd5Sum").getText() );
			}
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 获取本次选中发送的图片数量
	 * @return
	 */
	public int getCount() {
		return count;
	}

	/**
	 * {@link #count}
	 * @param count
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * 选中图片的MD5值列表
	 * {@link #picMd5Sum}
	 * @return
	 */
	public List<String> getPicMd5Sum() {
		return picMd5Sum;
	}

	/**
	 * @see #picMd5Sum
	 * @param picMd5Sum
	 */
	public void setPicMd5Sum(List<String> picMd5Sum) {
		this.picMd5Sum = picMd5Sum;
	}
	
}
