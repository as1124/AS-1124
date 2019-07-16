package com.volume2.ch3.net.nonblocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 非阻塞模式的Socket
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class NoblockingSocket {

	static SocketChannel client = null;

	static RecvThread rt = null;

	public static void main(String[] args) {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			InetSocketAddress socketAddress = new InetSocketAddress(ia, 8189);
			client = SocketChannel.open();

			// in nonblocking mode, connect will immediately return, so need to make
			// sure that the connection was established.
			if (!client.connect(socketAddress)) {
				boolean isConnected = false;
				while (!isConnected) {
					isConnected = client.finishConnect();
				}
			}
			client.configureBlocking(false);

			receiveMessage();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}

		while (sendMessage() != -1) {
			// just a loop
		}

		try {
			client.close();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private static void receiveMessage() {
		rt = new RecvThread("Receive Thread", client);
		rt.start();
	}

	public static int sendMessage() {
		JavaCoreLogger.log(Level.INFO, "Inside SendMessage");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String msg = "";
		ByteBuffer bytebuff = ByteBuffer.allocate(1024);
		int nBytes = 0;
		try {
			msg = reader.readLine();
			JavaCoreLogger.log(Level.INFO, "msg is " + msg);
			bytebuff = ByteBuffer.wrap(msg.getBytes());
			nBytes = client.write(bytebuff);
			JavaCoreLogger.log(Level.INFO, "nBytes is " + nBytes);
			if (msg.equalsIgnoreCase("quit")) {
				JavaCoreLogger.log(Level.INFO, "time to stop the client");
				interruptThread();
				Thread.sleep(3000);
				client.close();
				return -1;
			}
		} catch (Exception e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}

		JavaCoreLogger.log(Level.INFO, "Wrote " + nBytes + " bytes to the server");
		return nBytes;
	}

	private static void interruptThread() {
		rt.setVal(false);
	}
}

class RecvThread extends Thread {

	private SocketChannel sc = null;

	private boolean val = true;

	public RecvThread(String str, SocketChannel client) {
		super(str);
		sc = client;
	}

	@Override
	public void run() {
		JavaCoreLogger.log(Level.INFO, "Inside receivemsg");
		int nBytes = 0;
		ByteBuffer buf = ByteBuffer.allocate(2048);
		try {
			while (val) {
				while ((nBytes = sc.read(buf)) > 0) {
					buf.flip();
					Charset charset = Charset.forName("us-ascii");
					CharsetDecoder decoder = charset.newDecoder();
					CharBuffer charBuffer = decoder.decode(ByteBuffer.wrap(buf.array(), 0, nBytes));
					String result = charBuffer.toString();
					JavaCoreLogger.log(Level.INFO, result);
					buf.flip();
				}
			}

		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * @return Returns the val.
	 */
	public boolean isVal() {
		return val;
	}

	/**
	 * @param val The val to set.
	 */
	public void setVal(boolean val) {
		this.val = val;
	}
}
