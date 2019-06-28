package com.volume2.ch10.script;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * 
 * @author huangjw (mailto:as1124huang@gmail.com)
 */
public class ButtonFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_WIDTH = 300;

	private static final int DEFAULT_HEIGHT = 400;

	private JPanel panel;

	private JButton yellowButton;

	private JButton blueButton;

	private JButton redButton;

	public ButtonFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		panel = new JPanel();
		panel.setName("panel");
		add(panel);

		yellowButton = new JButton("Yellow");
		yellowButton.setName("yellowButton");
		blueButton = new JButton("Blue");
		blueButton.setName("blueButton");
		redButton = new JButton("Red");
		redButton.setName("redButton");

		panel.add(yellowButton);
		panel.add(blueButton);
		panel.add(redButton);
	}

}
