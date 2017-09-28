package com.volume2.ch3.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 
 * This program makes a socket connection to the atomic clock in Boulder,
 * Colorado, and prints the time that the server sends.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class SocketCase {

	public static void main(String[] args) {
		Scanner in = null;

		// 这种构造行数是默认进行connect的, 因此如果目标地址不可到达, 则会一直阻塞直至系统超时
		try (Socket socket = new Socket("time-A.timefreq.bldrdoc.gov", 13);) {

			/**
			 * equals:<br>
			 * Socket s = new Socket();
			 * s.connect(new InetSocketAddress(hostname, port), timeout);
			 * 
			 */
			InputStream inStream = socket.getInputStream();
			in = new Scanner(inStream);

			while (in.hasNextLine()) {
				String line = in.nextLine();
				JavaCoreLogger.log(Level.INFO, line);
			}
		} catch (IOException ex) {
			JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
		} finally {
			if (in != null)
				in.close();
		}
	}

}
