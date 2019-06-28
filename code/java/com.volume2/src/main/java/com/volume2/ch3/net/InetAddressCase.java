package com.volume2.ch3.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 
 * This program demonstrates the InetAddress class.
 * Supply a host name as command line argument, or run without command line 
 * arguments to see the address of the local host.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class InetAddressCase {

	public static void main(String[] args) {
		try {
			if (args.length > 0) {
				String host = args[0];
				InetAddress[] address = InetAddress.getAllByName(host);
				for (InetAddress a : address)
					JavaCoreLogger.log(Level.INFO, a.toString());
			} else {
				InetAddress localHostAddress = InetAddress.getLocalHost();
				JavaCoreLogger.log(Level.INFO, localHostAddress.toString());
			}
		} catch (UnknownHostException ex) {
			JavaCoreLogger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

}
