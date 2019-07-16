package com.volume2.ch1.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.logging.Level;

import com.java.core.log.JavaCoreLogger;

/**
 * 字符集测试. 测试中文字符，支持中文字符的编码才能在encode和decode之后获取正确的数据
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CharsetCase {

	public static void main(String[] args) {
		//工作空间默认是UTF-8编码
		String str = "亲爱的胡敏，I love you! 我想和你一起到老";

		SortedMap<String, Charset> jvmSupportCharsets = Charset.availableCharsets();
		Iterator<String> it = jvmSupportCharsets.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			Charset value = jvmSupportCharsets.get(key);
			JavaCoreLogger.log(Level.INFO, key + "=" + value);
		}

		Charset gbk = Charset.forName("GBK");
		Charset big5 = Charset.forName("Big5");
		Charset utf8 = Charset.forName("UTF-8");
		Charset iso88591 = Charset.forName("ISO-8859-1");
		Charset euckr = Charset.forName("EUC-KR");

		char[] charArray = str.toCharArray();

		ByteBuffer gbkBytes = gbk.encode(CharBuffer.wrap(charArray));
		printByte(gbkBytes.array());
		JavaCoreLogger.log(Level.INFO, gbk.decode(gbk.encode(CharBuffer.wrap(charArray))).toString());
		JavaCoreLogger.log(Level.INFO, gbk.decode(gbkBytes).toString());

		ByteBuffer big5Bytes = big5.encode(CharBuffer.wrap(charArray));
		printByte(big5Bytes.array());
		JavaCoreLogger.log(Level.INFO, big5.decode(big5Bytes).toString());

		ByteBuffer utf8Bytes = utf8.encode(CharBuffer.wrap(charArray));
		printByte(utf8Bytes.array());
		JavaCoreLogger.log(Level.INFO, utf8.decode(utf8Bytes).toString());

		ByteBuffer iso88591Bytes = iso88591.encode(CharBuffer.wrap(charArray));
		printByte(iso88591Bytes.array());
		JavaCoreLogger.log(Level.INFO, iso88591.decode(iso88591Bytes).toString());

		ByteBuffer euckrBytes = euckr.encode(CharBuffer.wrap(charArray));
		printByte(euckrBytes.array());
		JavaCoreLogger.log(Level.INFO, euckr.decode(euckrBytes).toString());
	}

	public static void printByte(byte[] array) {
		for (byte b : array) {
			System.out.print(b + ",");
		}
		System.out.println();
	}

}
