package com.volume2.ch5.local;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * This program demonstrates formatting dates under various locals.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class DateFormatCase {

	public static void main(String[] args) {
		EventQueue.invokeLater(()->{
			JFrame frame = new DateFormatFrame();
			frame.setTitle("DateFormatTest");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}

