package com.volume1.ch14.threads.single;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Show an animated bouncing ball controled by a single thread.
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class BounceFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int STEPS = 1600;
	public static final int DELAY = 5;

	public BounceFrame() {
		setTitle("弹跳球");
		BallComponent comp = new BallComponent();
		add(comp, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", (ActionEvent e) -> {
			addBall(comp);
		});
		addButton(buttonPanel, "Close", (ActionEvent e) -> {
			System.exit(0);
		});
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
	}

	private void addButton(Container container, String title, ActionListener listener) {
		JButton button = new JButton(title);
		button.addActionListener(listener);
		container.add(button);
	}

	/**
	 * Adds a bouncing ball to panel and makes it bounce 1000 times.
	 * 
	 * @param comp
	 */
	private void addBall(BallComponent comp) {
		Ball ball = new Ball();
		comp.add(ball);
		try {
			for (int i = 1; i <= STEPS; i++) {
				ball.move(comp.getBounds());
				comp.paint(comp.getGraphics());
				Thread.sleep(DELAY);
			}
		} catch (InterruptedException e) {
			// don't care about
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame frame = new BounceFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}

}

class BallComponent extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_WIDTH = 650;
	private static final int DEFAULT_HEIGHT = 450;

	private List<Ball> balls = new ArrayList<>();

	public void add(Ball b) {
		balls.add(b);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Ball b : balls) {
			g2d.fill(b.getShape());
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

}

class Ball {
	private static final int XSIZE = 15;
	private static final int YSIZE = 15;
	private double x = 0;
	private double y = 0;
	private double dx = 1;
	private double dy = 1;

	public void move(Rectangle2D bounds) {
		x += dx;
		y += dy;
		if (x < bounds.getMinX()) {
			x = bounds.getMinX();
			dx = -dx;
		}
		if (x + XSIZE >= bounds.getMaxX()) {
			x = bounds.getMaxX() - XSIZE;
			dx = -dx;
		}
		if (y < bounds.getMinY()) {
			y = bounds.getMinY();
			dy = -dy;
		}
		if (y + YSIZE >= bounds.getMaxY()) {
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}
	}

	/**
	 * Gets the shape of the ball at its current position.
	 * 
	 * @return
	 */
	public Ellipse2D getShape() {
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}
}
