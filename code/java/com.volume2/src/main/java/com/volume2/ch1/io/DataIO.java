package com.volume2.ch1.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 工具类
 * 
 * @author as1124huang@gmail.com
 *
 */
public class DataIO {

	private DataIO() {
	}

	public static String readFixedString(int size, DataInput in) throws IOException {
		StringBuilder b = new StringBuilder();
		int i = 0;
		boolean more = true;
		while (more && i < size) {
			char ch = in.readChar();
			i++;
			if (ch == 0)
				more = false;
			else
				b.append(ch);
		}

		in.skipBytes(2 * (size - i));
		return b.toString();
	}

	public static void writeFixedString(String s, int size, DataOutput out) throws IOException {
		for (int i = 0; i < size; i++) {
			char ch = 0;
			if (i < s.length())
				ch = s.charAt(i);
			out.writeChar(ch);
		}
	}
}
