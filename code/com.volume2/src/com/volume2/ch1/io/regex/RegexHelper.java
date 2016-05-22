package com.volume2.ch1.io.regex;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 正则表达式使用<br>
 * 
 * @author huangjw(mailto:as1124huang@gmail.com)
 *
 */
public class RegexHelper {

	public static void main(String[] args) {
		//testUse();
		
		testHtmlTag();
	}
	
	/*
	 * This program tests regular expression matching.
	 * Enter a pattern and strings to match, or hit Cancel to exit.
	 * If the pattern contains groups, the group boundaries are displayed 
	 * in the match.
	 */
	public static void testUse(){
		Scanner in = new Scanner(System.in);
		System.out.println("Enter pattern: ");
		String patternString  = in.nextLine();
		Pattern pattern = null;
		pattern = Pattern.compile(patternString);
		
		while(true){
			System.out.println("Enter string to match: ");
			String input = in.nextLine();
			if(input == null || input.equals("")){
				return;
			}
			Matcher matcher = pattern.matcher(input);
			
			// matchers()是全部匹配，即独占模式. 还是用matcher.find()进行逐序列匹配
			if(matcher.matches()){
				System.out.println(matcher.groupCount());
				int count = matcher.groupCount();
				if(count>0){
					for(int i=0; i<input.length(); i++){
						for(int j=1; j<=count; j++)
							if(i == matcher.start(j))
								System.out.print("(");
						System.out.print(input.charAt(i));
						for(int j=0; j<=count; j++)
							if(i+1 == matcher.end(j))
								System.out.print(")");
					}
					System.out.println();
				}
			} else {
				System.out.println("No match");
				break;
			}
			
		}
		
		if(in != null){
			in.close();
		}
	}
	
	/**
	 * This program displays all URLs in a web page by matching a regular expression that
	 * describes the <a href=..> HTML tag. Start the program as <br> java HrefMatcher URL.
	 * 
	 */
	public static void testHtmlTag(){
		//get URL string from command line or use default
		String urlString = "http://www.oracle.com/technetwork/java/index.html";
		
		//read contents into string builder
		StringBuilder input = new StringBuilder();
		int ch = 0;
		try {
			//open reader for URL
			InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());
			
			while((ch = in.read()) != -1){
				input.append((char)ch);
			}
			
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//search for all occurrences of pattern
		String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
		Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input);
		
		while(matcher.find()){
			int start = matcher.start();
			int end = matcher.end();
			String match = input.substring(start, end);
			System.out.println(match);
		}
		
	}
	
}
