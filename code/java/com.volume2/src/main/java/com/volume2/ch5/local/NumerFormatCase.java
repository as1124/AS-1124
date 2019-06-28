package com.volume2.ch5.local;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * 利用Local格式化数字、货币
 * This program demonstrates formatting numbers under various locals.
 *
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class NumerFormatCase {

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new NumberFormatFrame();
			frame.setTitle("NumberFormatCase");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}
