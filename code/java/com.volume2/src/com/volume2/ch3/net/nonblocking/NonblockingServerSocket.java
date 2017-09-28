/*******************************************************************************
 * Copyright (c) 2001-2017 Primeton Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2017年9月27日
 *******************************************************************************/

package com.volume2.ch3.net.nonblocking;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 非阻塞模式的ServerSocket
 *
 * @author huangjw (mailto:as1124huang@primeton.com)
 */

public class NonblockingServerSocket {

	public static void main(String[] args) {
		Selector sel = null;
		try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
			serverChannel.configureBlocking(false);

			// 在本地的8189端口上绑定了一个ServerSocket并监听
			InetAddress ia = InetAddress.getLocalHost();
			InetSocketAddress isa = new InetSocketAddress(ia, 8189);
			serverChannel.socket().bind(isa);

			sel = Selector.open();
			serverChannel.register(sel, SelectionKey.OP_ACCEPT);

			// select() is a blocking operation
			while (sel.select() > 0) {
				Iterator<SelectionKey> it = sel.selectedKeys().iterator();
				doSelec(it, sel);
			}
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			try {
				if (sel != null)
					sel.close();
			} catch (IOException e) {
				JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private static void doSelec(Iterator<SelectionKey> it, Selector sel) {
		while (it.hasNext()) {
			SocketChannel socket = null;
			SelectionKey key = it.next();
			it.remove();

			try {
				if (key.isAcceptable()) {
					// a connection was accepted by a ServerSocketChannel
					JavaCoreLogger.log(Level.INFO, "Acceptabel Key");
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					socket = ssc.accept();
					socket.configureBlocking(false);
					socket.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
				} else if (key.isConnectable()) {
					// a connection was established with a remote server

				} else if (key.isReadable()) {
					// a channel is ready for reading
					JavaCoreLogger.log(Level.INFO, "Readable Key");
					String ret = readMessage(key);
					if (ret.length() > 0) {
						writeMessage(socket, ret);
					}
				} else if (key.isWritable()) {
					// a channel is ready for writing
					JavaCoreLogger.log(Level.INFO, "Writable Key");
					String ret = readMessage(key);
					socket = (SocketChannel) key.channel();
					if (ret.length() > 0) {
						writeMessage(socket, ret);
					}
				}
			} catch (IOException e) {
				JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	private static String readMessage(SelectionKey selKey) {
		int nBytes = 0;
		SocketChannel socket = (SocketChannel) selKey.channel();
		ByteBuffer buff = ByteBuffer.allocate(1024);
		try {
			nBytes = socket.read(buff);
			buff.flip();
			Charset charset = Charset.forName("us-ascii");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuff = decoder.decode(ByteBuffer.wrap(buff.array(), 0, nBytes));
			return charBuff.toString();
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
		return "";
	}

	private static void writeMessage(SocketChannel socket, String message) {
		JavaCoreLogger.log(Level.INFO, "Inside the loop");
		if (message.equalsIgnoreCase("quit")) {
			return;
		}
		File file = new File(message);

		try (RandomAccessFile rdm = new RandomAccessFile(file, "r");) {
			FileChannel fc = rdm.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fc.read(buffer);
			buffer.flip();

			Charset charset = Charset.forName("us-ascii");
			CharsetDecoder dec = charset.newDecoder();
			CharBuffer charBuff = dec.decode(buffer);
			JavaCoreLogger.log(Level.INFO, charBuff.toString());

			buffer = ByteBuffer.wrap(charBuff.toString().getBytes());
			int nBytes = socket.write(buffer);
			JavaCoreLogger.log(Level.INFO, "nBytes = " + nBytes);
		} catch (IOException e) {
			JavaCoreLogger.log(Level.SEVERE, e.getMessage(), e);
		}
	}

}
