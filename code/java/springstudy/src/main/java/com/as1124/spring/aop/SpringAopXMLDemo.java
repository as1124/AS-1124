package com.as1124.spring.aop;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringAopXMLDemo.class)

@ImportResource("classpath:config/spring-aop.xml")
@Configuration
public class SpringAopXMLDemo {

	@Autowired(required = false)
	private ITicketSaler ticketSaler;

	@Test
	public void testAopXMLConfig() {
		Assert.assertNotNull(ticketSaler);
		System.out.println("演出已经开始了");
		ticketSaler.getAvailableTickets("断背山");
	}
}
