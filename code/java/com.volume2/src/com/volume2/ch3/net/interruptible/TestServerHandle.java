package com.volume2.ch3.net.interruptible;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * 
 * This class handles the client input for one server socket connection.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class TestServerHandle implements Runnable {

	private JTextArea message = null;
	
	private Socket socket = null;
	
	private int counter;
	
	public TestServerHandle(Socket s, JTextArea text) {
		socket = s;
		this.message = text;
	}
	
	@Override
	public void run() {
		try {
			OutputStream outStream = socket.getOutputStream();
			PrintWriter out = new PrintWriter(outStream);
			while(counter < 100){
				counter++;
				if(counter <= 10)
					out.println(counter);
				Thread.sleep(100);
				message.append("Closing server\n");
			}
		} catch (Exception e) {
			message.append("\nTestServerHandle.run: "+e);
		}
		
		
	}

}
