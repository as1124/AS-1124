package com.as1124.spring.framework.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.as1124.spring.framework.CompactDisc;

/**
 * 演示Spring自动装配, 当前的CDPlayer依赖于bean：PuShuDisc-bean
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component("cdPlayer")
public class CDPlayer {

	private CompactDisc cd;

	@Autowired
	public CDPlayer(CompactDisc cdP) {
		this.cd = cdP;
	}
}
