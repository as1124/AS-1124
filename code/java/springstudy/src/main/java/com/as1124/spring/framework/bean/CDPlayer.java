package com.as1124.spring.framework.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.as1124.spring.framework.IMedia;
import com.as1124.spring.framework.IMediaPlayer;

/**
 * 演示Spring自动装配, 当前的CDPlayer依赖于bean：PuShuDisc-bean
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component("cdPlayer")
public class CDPlayer implements IMediaPlayer {

	private IMedia cd;

	@Autowired(required = true)
	public CDPlayer(IMedia cdP) {
		this.cd = cdP;
	}

	@Override
	public void startPlay() {
		cd.getMediaContent();
	}
}
