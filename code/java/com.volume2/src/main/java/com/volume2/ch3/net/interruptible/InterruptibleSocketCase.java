package com.volume2.ch3.net.interruptible;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * This program shows how to interrupt a socket channel.
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class InterruptibleSocketCase {

	public static void main(String[] args) {
		// Java8 lambda表达式
		// 结构'()->{}'，其中 ->左侧是参数列表，右侧是方法运行体
		EventQueue.invokeLater(() -> {
			JFrame frame = new InterruptibleSocketFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}