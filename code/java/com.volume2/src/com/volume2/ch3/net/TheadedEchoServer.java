package com.volume2.ch3.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
		int i =1;
		Socket socket = null;
		ServerSocket server = null;
		try {
			server = new ServerSocket(8189);
			
			while(true){
				socket = server.accept();
				System.out.println("Spawning " + i);
				Runnable runner = new ThreadedHandler(socket);
				
				Thread thread = new Thread(runner);
				thread.start();
				i++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket.isConnected()){
				socket.close();
			}
			if(server.isClosed() == false){
				server.close();
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
class ThreadedHandler implements Runnable{

	Socket socket = null;
	
	public ThreadedHandler(Socket s) {
		this.socket = s;
	}
	
	@Override
	public void run() {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {
			inStream = socket.getInputStream();
			outStream = socket.getOutputStream();
			
			Scanner in = new Scanner(inStream);
			PrintWriter out = new PrintWriter(outStream, true);
			
			out.println("Hello! Enter BYE to exit.");
			
			//echo client input
			boolean done = false;
			while(!done && in.hasNextLine()){
				String line = in.nextLine();
				out.println("Echo: " + line);
				if(line.trim().equals("BYE")){
					done = true;
				}
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(socket.isConnected()){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
