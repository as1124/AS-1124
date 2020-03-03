package com.as1124.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

/**
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Aspect
public class AspectTicketSaler {

	@DeclareParents(value = "com.as1124.spring.aop.IPerformance+", defaultImpl = TicketSalerImpl.class)
	public static ITicketSaler tickInfo = null;

	public static class TicketSalerImpl implements ITicketSaler {

		@Override
		public int getAvailableTickets(String ticketNo) {
			System.out.println(ticketNo + " 的余票数量 == 0");
			return 0;
		}
	}
}
