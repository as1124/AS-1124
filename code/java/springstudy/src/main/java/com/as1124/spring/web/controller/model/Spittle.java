package com.as1124.spring.web.controller.model;

import java.util.Date;

/**
 * 基础的POJO测试模型
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Spittle {

	private Long id;
	private String message;
	private Date time;
	private Double latitude;
	private Double longtitude;

	public Spittle(String message, Date time) {
		this.message = message;
		this.time = time;
	}

	/**
	 * @return Returns the {@link #id}.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id The {@link #id} to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return Returns the {@link #message}.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The {@link #message} to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the {@link #time}.
	 */
	public Date getTime() {
		return time;
	}

	/**
	 * @param time The {@link time} to set.
	 */
	public void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return Returns the {@link #latitude}.
	 */
	public Double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude The {@link #latitude} to set.
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return Returns the {@link #longtitude}.
	 */
	public Double getLongtitude() {
		return longtitude;
	}

	/**
	 * @param longtitude The {@link #longtitude} to set.
	 */
	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

}
