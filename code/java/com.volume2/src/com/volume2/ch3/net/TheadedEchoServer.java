package com.volume2.ch3.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 
 * This program implements a multi-threaded server that listens to port
 * 8189 and echoes back all client input.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class TheadedEchoServer {

	public static void main(String[] args) throws IOException {

		int i = 1;
		Socket incoming = null;

		try (ServerSocket server = new ServerSocket(8189);) {
			boolean isContinue = true;
			while (isContinue) {
				incoming = server.accept();
				JavaCoreLogger.log(Level.INFO, "Spawning " + i);
				Runnable runner = new ThreadedHandler(incoming);
				Thread thread = new Thread(runner);
				thread.start();
				i++;
				if (i > 5)
					isContinue = false;
			}
		} catch (IOException ex) {
			JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			try {
				if (incoming != null) {
					incoming.close();
				}
			} catch (IOException ex) {
				JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
	}
}

/**
 * 
 * This class handles the client input for one server socket connection.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
class ThreadedHandler implements Runnable {

	Socket socket = null;

	public ThreadedHandler(Socket s) {
		this.socket = s;
	}

	@Override
	public void run() {

		try (InputStream inStream = socket.getInputStream(); OutputStream outStream = socket.getOutputStream();) {
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);

			out.println("Hello! Enter BYE to exit.");

			//echo client input
			boolean done = false;
			while (!done && in.hasNextLine()) {
				String line = in.nextLine();
				out.println("Echo: " + line);
				if (line.trim().equals("BYE")) {
					done = true;
				}
			}

			out.flush();
			out.close();
			in.close();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
