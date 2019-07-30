package com.as1124.spring.framework.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import com.as1124.spring.framework.IMedia;
import com.as1124.spring.framework.IMediaPlayer;
import com.as1124.spring.framework.condition.ConditionUsage;

/**
 * 演示Spring自动装配, 当前的CDPlayer依赖于bean：PuShuDisc-bean
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component("cdPlayer")
@Conditional(ConditionUsage.class)
public class CDPlayer implements IMediaPlayer {

	@Autowired(required = true)
	@Qualifier("puShu")
	private IMedia cd;

	public CDPlayer() {
		// default constructor
	}

	public CDPlayer(IMedia cdP) {
		this.cd = cdP;
	}

	@Override
	public void startPlay() {
		cd.getMediaContent();
	}
}
