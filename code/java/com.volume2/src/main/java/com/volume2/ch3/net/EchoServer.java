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
 * This program implements a simple server that listens to 
 * port 8189 and echoes back all client input.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class EchoServer {

	public static void main(String[] args) throws IOException {
		try (
				//establish server socket
				ServerSocket server = new ServerSocket(8189);
				//wait for client connection
				Socket incoming = server.accept();) {
			InputStream inStream = incoming.getInputStream();
			OutputStream outStream = incoming.getOutputStream();

			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);

			out.println("Hello! Enter BYE to exit.");

			//echo client input
			boolean done = false;
			while (!done && in.hasNextLine()) {
				String line = in.nextLine();
				out.println("Echo: " + line);
				if (line.trim().equals("BYE"))
					done = true;
			}

			in.close();
		} catch (IOException ex) {
			JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}
}
