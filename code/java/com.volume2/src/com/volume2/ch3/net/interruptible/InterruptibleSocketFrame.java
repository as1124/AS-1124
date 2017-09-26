package com.volume2.ch3.net.interruptible;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class InterruptibleSocketFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private Scanner in;
	
	private JButton interruptibleButton;
	
	private JButton blockingButton;
	
	private JButton cancelButton;
	
	private JTextArea message;
	
	private TestServer server;
	
	private Thread connectThread;
	
	public InterruptibleSocketFrame() {
		setSize(WIDTH, HEIGHT);
		setTitle("InterruptibleSocketTest");
		
		JPanel northPanel = new JPanel();
		this.add(northPanel, BorderLayout.NORTH);
		
		message = new JTextArea();
		add(new JScrollPane(message), BorderLayout.CENTER);
		
		interruptibleButton = new JButton("Interruptible");
		blockingButton = new JButton("Blocking");
		
		cancelButton = new JButton("Cancel");
		cancelButton.setEnabled(false);
		
		northPanel.add(interruptibleButton);
		northPanel.add(blockingButton);
		northPanel.add(cancelButton);
		
		interruptibleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				interruptibleButton.setEnabled(false);
				blockingButton.setEnabled(false);
				cancelButton.setEnabled(true);
				connectThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							connectInterruptibly();
						} catch (IOException e) {
							message.append("\nInterruptibleSocketTest.connectInterruptibly: "+e);
						}
					}
				});
				connectThread.start();
			}
		});
		
		blockingButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				interruptibleButton.setEnabled(false);
				blockingButton.setEnabled(false);
				cancelButton.setEnabled(true);
				connectThread = new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							connectBlocking();
						} catch (IOException e) {
							message.append("\nInterruptibleSocketTest.connectBlocking: "+e);
						}
					}
				});
				connectThread.start();
			}
		});
		
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(connectThread != null){
					connectThread.interrupt();
					cancelButton.setEnabled(false);
				}
			}
		});
		
		server = new TestServer(message);
		new Thread(server).start();
	}
	
	/**
	 * Connects to the test server, using interruptible I/O
	 * @throws IOException
	 */
	public void connectInterruptibly() throws IOException {
		message.append("Interruptible:\n");
		SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8189));
		
		try {
			in = new Scanner(channel);
			while(!Thread.currentThread().isInterrupted()){
				message.append("Reading ");
				if(in.hasNextLine()){
					String line = in.nextLine();
					message.append(line);
					message.append("\n");
				}
			}
		} finally {
			channel.close();
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					message.append("Channel closed\n");
					interruptibleButton.setEnabled(true);
					blockingButton.setEnabled(true);
				}
			});
		}
		
	}
	
	/**
	 * Connects to the test server, using blocking I/O
	 * @throws IOException
	 */
	public void connectBlocking() throws IOException {
		message.append("Blockig:\n");
		Socket socket = new Socket("localhost", 8189);
		try {
			in = new Scanner(socket.getInputStream());
			while(!Thread.currentThread().isInterrupted()){
				message.append("Reading ");
				if(in.hasNextLine()){
					String line = in.nextLine();
					message.append(line);
					message.append("\n");
				}
			}
		} finally {
			socket.close();
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					message.append("Socket closed\n");
					interruptibleButton.setEnabled(true);
					blockingButton.setEnabled(true);
					
				}
			});
		}
		
	}
	
}
