package com.volume2.ch3.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 * This program makes a socket connection to the atomic clock in Boulder,
 * Colorado, and prints the time that the server sends.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SocketTest {

	public static void main(String[] args) {
		try {
			
			//HUANG 这种构造行数是默认进行connect的, 因此如果目标地址不可到达, 则会一直阻塞直至系统超市
			Socket socket = new Socket("time-A.timefreq.bldrdoc.gov", 13);
			
			/**
			 * equals:<br>
			 * Socket s = new Socket();
			 * s.connect(new InetSocketAddress(hostname, port), timeout);
			 * 
			 */
			InputStream inStream = socket.getInputStream();
			Scanner in = new Scanner(inStream);
			
			while(in.hasNextLine()){
				String line = in.nextLine();
				System.out.println(line);
			}
			
			if(in != null)
				in.close();
			if(socket != null)
				socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
