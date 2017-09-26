package com.volume2.ch3.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This program implements a simple server that listens to 
 * port 8189 and echoes back all client input.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class EchoServer {

	public static void main(String[] args) throws IOException {
		Socket incoming = null;
		ServerSocket server = null;
		try {
			//establish server socket
			server = new ServerSocket(8189);
			
			//wait for client connection
			incoming = server.accept();
			InputStream inStream = incoming.getInputStream();
			OutputStream outStream = incoming.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);
			
			out.println("Hello! Enter BYE to exit.");
			
			//echo client input
			boolean done = false;
			while(!done && in.hasNextLine()){
				String line = in.nextLine();
				out.println("Echo: " + line);
				if(line.trim().equals("BYE"))
					done = true;
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(incoming.isConnected()){
				incoming.close();
			}
			if(server.isClosed() == false){
				server.close();
			}
		}
	}

}
