package com.volume2.ch5.local;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * This program shows a retirement calculator. The UI is displayed in English, 
 * German, and Chinese.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class RetireCase {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new RetireFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
