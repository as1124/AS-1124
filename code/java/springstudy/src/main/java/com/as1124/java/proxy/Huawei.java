package com.as1124.java.proxy;

import java.util.Scanner;
import java.util.Stack;

/**
 * 
 * 给定字符串，判断是否是有效字符串
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class Huawei {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			String source = scanner.next();
			if (source.trim().equals("")) {
				System.out.println(0);
				return;
			}

			char[] chars = source.toCharArray();
			int left1 = source.indexOf('(');
			int left2 = source.indexOf('{');
			int left3 = source.indexOf('[');
			// 找出左括号最小index
			int minLeft = -1;
			if (left1 < 0 && left2 < 0 && left3 < 0) {
				System.out.println(0);
				return;
			}
			if (left1 > -1) {
				minLeft = left1;
			} else if (left2 > -1) {
				minLeft = left2;
			} else if (left3 > -1) {
				minLeft = left3;
			}
			if (left1 > -1) {
				minLeft = Math.min(minLeft, left1);
			}
			if (left2 > -1) {
				minLeft = Math.min(minLeft, left2);
			}
			if (left3 > -1) {
				minLeft = Math.min(minLeft, left3);
			}

			int firtstRight1 = source.indexOf(')');
			int firtstRight2 = source.indexOf('}');
			int firtstRight3 = source.indexOf(']');
			// 找出右括号最小index
			int minRight = -1;
			if (firtstRight1 <= 0 && firtstRight2 <= 0 && firtstRight3 <= 0) {
				System.out.println(0);
				return;
			}
			if (firtstRight1 > 0) {
				minRight = firtstRight1;
			} else if (firtstRight2 > 0) {
				minRight = firtstRight2;
			} else if (firtstRight3 > 0) {
				minRight = firtstRight3;
			}
			if (firtstRight1 > 0) {
				minRight = Math.min(minRight, firtstRight1);
			}
			if (firtstRight2 > 0) {
				minRight = Math.min(minRight, firtstRight2);
			}
			if (firtstRight3 > 0) {
				minRight = Math.min(minRight, firtstRight3);
			}

			if (minLeft > minRight) {
				System.out.println(0);
				return;
			}

			int result = 0;
			// 记录下一个查找的闭口右括号
			Stack<Character> leftCharStack = new Stack<>();
			for (int i = 0; i < chars.length; i++) {
				switch (chars[i]) {
					case '(':
						leftCharStack.push(')');
						break;
					case '{':
						leftCharStack.push('}');
						break;
					case '[':
						leftCharStack.push(']');
						break;
					case ')':
					case '}':
					case ']':
						if (leftCharStack.isEmpty()) {
							// 先出现了右括号，不是有效字符串
							break;
						}
						if (leftCharStack.peek() == chars[i]) {
							// 如果正确匹配则弹出top元素，开始匹配下一个
							leftCharStack.pop();
						} else {
							// 如果不匹配则顺序错乱，不是有效字符串
							break;
						}
						break;
					default:
						break;
				}
			}
			if (leftCharStack.isEmpty()) {
				result = 1;
			}
			System.out.println(result);
		}
	}

}
