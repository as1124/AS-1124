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
public class TestServer implements Runnable {

	private JTextArea message = null;

	public TestServer(JTextArea text) {
		this.message = text;
	}

	@Override
	public void run() {
		boolean isContinue = true;
		int threadCount = 0;

		// 在Java7中，try-with-resource方式是会强制结束时关闭 closeable类型的资源
		try (ServerSocket server = new ServerSocket(8189)) {
			while (isContinue) {
				Socket socket = server.accept();
				Thread t = new Thread(new TestServerHandle(socket, message));
				t.start();
				threadCount++;
				if (threadCount > 3)
					isContinue = false;
			}
		} catch (IOException e) {
			message.append("\nTestServer.run: " + e);
		}
	}

}
