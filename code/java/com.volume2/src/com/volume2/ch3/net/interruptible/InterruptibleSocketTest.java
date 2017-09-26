package com.volume2.ch3.net.interruptible;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * 
 * This program shows how to interrupt a socket channel.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class InterruptibleSocketTest {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				JFrame frame = new InterruptibleSocketFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				
			}
		});

	}

}