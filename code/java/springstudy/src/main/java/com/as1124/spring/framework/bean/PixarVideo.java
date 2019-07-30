package com.as1124.spring.framework.bean;

import java.util.List;

import org.springframework.util.StringUtils;

import com.as1124.spring.framework.IMedia;

/**
 * Pixar动画电影描述
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class PixarVideo implements IMedia {

	/**
	 * Comment for <code>pictureTitle</code>：片名
	 */
	private String pictureTitle;

	/**
	 * Comment for <code>roles</code>: 角色列表
	 */
	private List<String> roles;

	private int publishYear = -1;

	private String director = "";

	public PixarVideo() {
		// default constructor
	}

	public PixarVideo(String title) {
		this.setPictureTitle(title);
	}

	public PixarVideo(String title, List<String> roleList) {
		this.setPictureTitle(title);
		this.roles = roleList;
	}

	@Override
	public void getMediaContent() {
		if (StringUtils.isEmpty(pictureTitle)) {
			System.out.println("This is Pixar, I like movie <Toy Story>");
		} else {
			System.out.println("Wowooo, U like the movie named " + pictureTitle);
		}

		if (this.roles != null && !this.roles.isEmpty()) {
			StringBuilder strBuilder = new StringBuilder();
			for (String str : this.roles) {
				strBuilder.append(str).append(',');
			}
			System.out.println("Roles are : " + strBuilder.toString());
		}

		System.out.println("This movice publish on " + this.publishYear + ", direct by " + this.director);
	}

	public void setPictureTitle(String pictureTitle) {
		this.pictureTitle = pictureTitle;
	}

	/**
	 * @param publishYear The {@lin #publishYear} to set.
	 */
	public void setPublishYear(int publishYear) {
		this.publishYear = publishYear;
	}

	/**
	 * @param director The {@link #director} to set.
	 */
	public void setDirector(String director) {
		this.director = director;
	}
}
