package com.volume2.ch1.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewTest {
	
	private static byte charToByte(char c) {
		return (byte) "0123456789abcdef".indexOf(c);
	}

	public static void main(String[] args) {
		String text = "  2.隐患编号：0202010x0A;";
		Pattern pp = Pattern.compile("([0][x]){1}([a-zA-Z_0-9])*[;]{1}");
		Matcher matcher = pp.matcher(text);
		while(matcher.find()){
			String notCommand = text.substring(0, matcher.start());
			System.out.println(notCommand);
		}
	}
}
