package com.volume2.ch3.net.interruptible;

import java.io.IOException;
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
		PrintWriter out = null;
		try {
			OutputStream outStream = socket.getOutputStream();
			out = new PrintWriter(outStream, true);
			while (counter < 20) {
				counter++;
				if (counter <= 10)
					out.println(counter);
				Thread.sleep(100);
				message.append("Closing server\n");
			}
		} catch (Exception e) {
			message.append("\nTestServerHandle.run: " + e);
		} finally {
			if (out != null)
				out.close();
			try {
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
