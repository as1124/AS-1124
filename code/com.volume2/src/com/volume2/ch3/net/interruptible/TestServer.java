package com.volume2.ch3.net.interruptible;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

/**
 * 
 * A multi-threaded server that listens to port 8189 and sends numbers to the client,
 * simulating a hanging server after 10 numbers.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class TestServer implements Runnable{

	private JTextArea message = null;
	
	public TestServer(JTextArea text) {
		this.message = text;
	}
	
	@Override
	public void run() {
		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(8189);
			while (true) {
				socket = server.accept();
				Runnable r = new TestServerHandle(socket, message);
				Thread t = new Thread(r);
				t.start();
			}
		} catch (IOException e) {
			message.append("\nTestServer.run: "+e);
		} finally {
			try {
				if(socket.isConnected()){
					socket.close();
				}
				if(server.isClosed() == false){
					server.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
