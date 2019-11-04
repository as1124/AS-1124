package com.mobile.thirdparty.wechat.model.media;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * 新闻素材列表（news）
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class WechatNewsList extends WechatMediaList {

	private List<WechatArticle> item = Collections.emptyList();

	public WechatNewsList() {
		// default constructor
	}

	@Override
	public List getItem() {
		return item;
	}

	@Override
	public void setItem(List itemData) {
		this.item = itemData;
	}

	/**
	 * 因为获取图文素材返回的内容(content)是一个<code>WechatNews</code>的json数组，
	 * 不同单独新增获取图文素材时的(content)。因此这里提供接口方便用户调用做解析。
	 * @param index index in {@link #item}
	 * @return Map< media_id, List< WechatNews>>
	 * @see {@link http://mp.weixin.qq.com/wiki/12/2108cd7aafff7f388f41f37efa710204.html}
	 */
	public Map<String, List<WechatArticle>> parseNewsItem(int index) {
		if (this.item.isEmpty())
			return null;
		String newsItemStr = JSONObject.parseObject(this.item.get(index).getContent()).getString("news_item");
		Map<String, List<WechatArticle>> result = new HashMap<>();
		result.put(this.item.get(index).getMedia_id(), JSONObject.parseArray(newsItemStr, WechatArticle.class));
		return result;
	}
}
