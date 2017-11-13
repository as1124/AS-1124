package com.mobile.thirdparty.wechat.message;

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
import com.mobile.thirdparty.wechat.model.AbstractDataPackage;

/**
 * 图文消息，仅用作回复。
 * @author huangjw(mailto:huangjw@primeton.com)
 *
 */
public class ArticleMessage extends AbstractMessage {

	/**
	 * 图文消息数量
	 */
	protected int articleCount;
	
	/**
	 * 图文消息列表
	 */
	private List<ArticleMessage.Article> articles = new ArrayList<ArticleMessage.Article>();
	
	public ArticleMessage() {
		super("news");
	}

	public String toSendText() {
		String result = "<xml><ToUserName><![CDATA["+getToUser()+"]]></ToUserName>"
				 +"<FromUserName><![CDATA["+getFromUser()+"]]></FromUserName>"
				 +"<CreateTime>"+this.getSentTime()+"</CreateTime>"
				 +"<MsgType><![CDATA["+getMsgType()+"]]></MsgType>"
				 +"<ArticleCount>"+getArticleCount()+"</ArticleCount>"
				 +"<Articles>"+getArticleStr()+"</Articles></xml>";
		return result;
	}

	/**
	 * {@link AbstractDataPackage#decodeFromXML(String)}
	 * @param xmlContent
	 * @return
	 */
	public Document decodeFromXML(String xmlContent) {
		SAXReader reader = new SAXReader(false);
		try {
			Document document = reader.read(new ByteArrayInputStream(xmlContent.getBytes(WechatConstants.CHARSET_UTF8)));
			Element root = document.getRootElement();
			this.setToUser(root.element("ToUserName").getText());
			this.setFromUser(root.element("FromUserName").getText());
			int createTime = Integer.parseInt(root.element("CreateTime").getText());
			this.setCreateTime(createTime);
			this.setMsgType(root.element("MsgType").getText());
			this.setArticleCount( Integer.parseInt(root.element("ArticleCount").getText()) );
			Element articleElement = root.element("Articles");
			if(this.articles == null){
				this.articles = new ArrayList<Article>();
			}else this.articles.clear();
			if(articleElement != null){
				Iterator<?> items = articleElement.elementIterator("item");
				while(items.hasNext()){
					Element item = (Element) items.next();
					Article newArticle = new Article();
					if(item.element("Title") != null)
						newArticle.title = item.element("Title").getText();
					if(item.element("Description") != null)
						newArticle.description = item.element("Description").getText();
					if(item.element("PicUrl") != null)
						newArticle.picurl = item.element("PicUrl").getText();
					if(item.element("Url") != null)
						newArticle.url = item.element("Url").getText();
					this.articles.add(newArticle);
				}
			}
			
			return document;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	private String getArticleStr(){
		String result = "";
		for(int i=0; i<this.articles.size(); i++)
		{
			Article article = articles.get(i);
			result = result + "<item><Title><![CDATA["+article.title+"]]></Title>"
					+ "<Description><![CDATA["+article.description+"]]></Description>"
					+ "<PicUrl><![CDATA["+article.picurl+"]]></PicUrl>"
					+ "<Url><![CDATA["+article.url+"]]></Url></item>";
		}
		return result;
	}
	
	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void addArticle(String title, String description, String picUrl, String url){
		Article article = new Article();
		article.title = title;
		article.description = description;
		article.picurl = picUrl;
		article.url = url;
		this.articles.add(article);
	}

	public class Article{
		private String title;
		
		private String description;
		
		private String picurl;
		
		private String url;
		
		public Article() {
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getPicurl() {
			return picurl;
		}

		public void setPicurl(String picurl) {
			this.picurl = picurl;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
		
	}
}
