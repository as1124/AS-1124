package com.as1124.spring.framework.bean;

import org.springframework.stereotype.Component;

import com.as1124.spring.framework.CompactDisc;

/**
 * 我朴大爷的单片CD
 * 
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
@Component("puShu")
public class PuShuDisc implements CompactDisc {

	@Override
	public void play() {
		System.out.println("正在播放 朴树 大爷的《我想好好的》专辑");
	}

}
