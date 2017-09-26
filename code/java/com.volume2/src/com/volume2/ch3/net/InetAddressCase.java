package com.volume2.ch3.net;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		Logger logger = Logger.getLogger(InetAddressCase.class.getName());
		try {
			if (args.length > 0) {
				String host = args[0];
				InetAddress[] address = InetAddress.getAllByName(host);
				for (InetAddress a : address)
					System.out.println(a);
			} else {
				InetAddress localHostAddress = InetAddress.getLocalHost();
				System.out.println(localHostAddress);
			}
		} catch (UnknownHostException ex) {
			logger.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

}
