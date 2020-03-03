package com.as1124.spring.aop;

/**
 * 票务信息
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public interface ITicketSaler {

	/**
	 * 获取剩余票量
	 * 
	 * @param ticketNo
	 * @return
	 */
	public int getAvailableTickets(String ticketNo);
}
