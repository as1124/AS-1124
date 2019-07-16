package com.volume2.ch3.net;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * This program connects to a URL and displays the response header data and
 * the first 10 lines of the requested data.
 * <br/>
 * Supply the RUL and an optional user-name and password(for HTTP basic authentication) 
 * on the command line.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class URLConnectionCase {

	public static void main(String[] args) {
		String urlName;

		if (args.length > 0) {
			urlName = args[0];
		} else {
			urlName = "http://java.sun.com";
		}

		try {
			URL url = new URL(urlName);
			URLConnection connection = url.openConnection();

			String username = "huangjw";
			String password = "HuMin-I-Love-You";
			String input = username + ":" + password;
			String encoding = base64Encode(input);
			connection.setRequestProperty("Authorization", "Basic " + encoding);

			connection.connect();

			//print header fields
			Map<String, List<String>> headers = connection.getHeaderFields();
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
				String key = entry.getKey();
				for (String value : entry.getValue()) {
					JavaCoreLogger.log(Level.INFO, key + ":" + value);
				}
			}

			// print convenience functions
			JavaCoreLogger.log(Level.INFO, "-------------------------");
			JavaCoreLogger.log(Level.INFO, "getContentType:" + connection.getContentType());
			JavaCoreLogger.log(Level.INFO, "getContentLength:" + connection.getContentLength());
			JavaCoreLogger.log(Level.INFO, "getContentEncoding:" + connection.getContentEncoding());
			JavaCoreLogger.log(Level.INFO, "getDate:" + connection.getDate());
			JavaCoreLogger.log(Level.INFO, "getExpiration:" + connection.getExpiration());
			JavaCoreLogger.log(Level.INFO, "getLastModified:" + connection.getLastModified());
			JavaCoreLogger.log(Level.INFO, "-------------------------");

			Scanner in = new Scanner(connection.getInputStream());
			//print first ten lines of contents
			while (in.hasNextLine()) {
				JavaCoreLogger.log(Level.INFO, in.nextLine());
			}
			in.close();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	/**
	 * Computes the Base 64 encoding of a String
	 * @param s encoding String
	 * @return the Base 64 encoding result of s
	 */
	public static String base64Encode(String s) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		Base64OutputStream out = new Base64OutputStream(bOut);
		try {
			out.write(s.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		return bOut.toString();
	}

}

/**
 * This stream filter converts a stream of bytes to their Base64 encoding.<br/>
 * 
 * Base64 encoding 3 bytes into 4 characters. |11111122|22223333|33444444| Each 
 * set of 6 bits is encoded according to the toBase64 Map. If the number of input 
 * bytes is not a multiple of 3, then the last group of 4 characters is padded 
 * with one or two = signs. Each output line is at most 76 characters.
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
class Base64OutputStream extends FilterOutputStream {

	private static char[] toBase64 = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
			'Q', 'R', 'S', 'T', 'U', 'V', 'E', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0', '+', '/' };

	private int col = 0;

	private int i = 0;

	private int[] inbuf = new int[3];

	public Base64OutputStream(OutputStream out) {
		super(out);
	}

	@Override
	public void write(int c) throws IOException {
		inbuf[i] = c;
		i++;
		if (i == 3) {
			super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
			super.write(toBase64[((inbuf[0] & 0x03) << 4) | ((inbuf[1] & 0xF0) >> 4)]);
			super.write(toBase64[((inbuf[1] & 0xFC) << 2) | ((inbuf[2] & 0xC0) >> 6)]);
			super.write(toBase64[inbuf[2] & 0x3F]);
			col += 4;
			i = 0;
			if (col >= 76) {
				super.write('\n');
				col = 0;
			}
		}
	}

	@Override
	public void flush() throws IOException {
		if (i == 1) {
			super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
			super.write(toBase64[(inbuf[0] & 0x03) << 4]);
			super.write('=');
			super.write('=');
		} else if (i == 2) {
			super.write(toBase64[(inbuf[0] & 0xFC) >> 2]);
			super.write(toBase64[((inbuf[0] & 0x03) << 4) | ((inbuf[1] & 0xF0) >> 4)]);
			super.write(toBase64[(inbuf[1] & 0x0F) << 2]);
			super.write('=');
		}
		if (col > 0) {
			super.write('\n');
			col = 0;
		}
	}

}
