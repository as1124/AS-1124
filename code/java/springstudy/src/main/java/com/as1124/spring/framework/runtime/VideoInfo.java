package com.as1124.spring.framework.runtime;

import com.as1124.spring.framework.IMedia;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class VideoInfo implements IMedia {

	private String title;

	private String desc;

	/* (non-Javadoc)
	 * @see com.as1124.spring.framework.IMedia#getMediaContent()
	 */
	@Override
	public void getMediaContent() {
		System.out.println("-------电影信息如下---------");
		System.out.println(this.title + ",  " + this.desc);
	}

	/**
	 * @return Returns the {@link title}.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title The {@link title} to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return Returns the {@link desc}.
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc The {@link desc} to set.
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

}
