package com.volume2.ch9.classloader;

public class ClassLoaderDemo {

	public static void main(String[] args) {
		System.out.println("测试自定义ClassLoader加载class");
		System.out.println("Successed!!!");

		for (String str : args) {
			System.out.println("接收参数 == " + str);
		}
	}

}