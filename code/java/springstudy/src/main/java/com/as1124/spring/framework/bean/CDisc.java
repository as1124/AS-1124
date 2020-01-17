package com.as1124.spring.framework.bean;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.as1124.spring.framework.IMedia;

/**
 * 我朴大爷的单片CD
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component("puShuCD")
@Primary
public class CDisc implements IMedia {

	@Override
	public void getMediaContent() {
		System.out.println("正在播放 朴树 大爷的《我想好好的》专辑");
	}

}
