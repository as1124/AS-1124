package com.volume2.ch1.io;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

/**
 * 字符集测试. 测试中文字符，支持中文字符的编码才能在encode和decode之后获取正确的数据
 * 
 * @author as1124huang@gmail.com
 *
 */
public class CharsetTest {

	public static void main(String[] args) {
		//工作空间默认是UTF-8编码
		String str = "亲爱的胡敏，I love you! 我想和你一起到老";
		
		SortedMap<String, Charset> jvmSupportCharsets = Charset.availableCharsets();
		Iterator<String> it = jvmSupportCharsets.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			Charset value = jvmSupportCharsets.get(key);
			System.out.println(key+"="+value);
		}
		
		Charset gbk = Charset.forName("GBK");
		Charset big5 = Charset.forName("Big5");
		Charset utf8 = Charset.forName("UTF-8");
		Charset iso88591 = Charset.forName("ISO-8859-1");
		Charset euckr = Charset.forName("EUC-KR");
		
		char[] charArray = str.toCharArray();
		
		ByteBuffer gbkBytes = gbk.encode(CharBuffer.wrap(charArray));
		printByte(gbkBytes.array());
		System.out.println(gbk.decode(gbk.encode(CharBuffer.wrap(charArray))).array());
		System.out.println(gbk.decode(gbkBytes));
		
		ByteBuffer big5Bytes = big5.encode(CharBuffer.wrap(charArray));
		printByte(big5Bytes.array());
		System.out.println(big5.decode(big5Bytes));
		
		ByteBuffer utf8Bytes = utf8.encode(CharBuffer.wrap(charArray));
		printByte(utf8Bytes.array());
		System.out.println(utf8.decode(utf8Bytes));
		
		ByteBuffer iso88591Bytes = iso88591.encode(CharBuffer.wrap(charArray));
		printByte(iso88591Bytes.array());
		System.out.println(iso88591.decode(iso88591Bytes));
		
		ByteBuffer euckrBytes = euckr.encode(CharBuffer.wrap(charArray));
		printByte(euckrBytes.array());
		System.out.println(euckr.decode(euckrBytes));
	}

	public static void printByte(byte[] array){
		for(byte b : array){
			System.out.print(b+",");
		}
		System.out.println();
	}
	
}
