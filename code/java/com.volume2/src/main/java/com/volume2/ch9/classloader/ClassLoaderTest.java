package com.volume2.ch9.classloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * {@link ClassLoader} 相关知识
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ClassLoaderTest {

	public static void main(String[] args) {
		String newClassName = "com.volume2.ch9.classloader.ClassLoaderDemo";
		URL loaderRoot = ClassLoaderDemo.class.getResource("/");
		String projectRoot = loaderRoot.getFile().replace("/target/classes", "");
		File myClassFile = new File(projectRoot, newClassName.replace('.', '/') + ".myclass");
		myClassFile.getParentFile().mkdirs();
		try (InputStream classIn = ClassLoaderDemo.class.getResourceAsStream("ClassLoaderDemo.class");
				FileOutputStream classOut = new FileOutputStream(myClassFile)) {
			byte[] buff = new byte[2048];
			int length = 0;
			while ((length = classIn.read(buff)) != -1) {
				classOut.write(buff, 0, length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		ClassLoader loader = new CryptoClassLoader(projectRoot);
		try {
			Class<?> c = loader.loadClass(newClassName);
			Method m = c.getDeclaredMethod("main", String[].class);
			Object argus = new String[] { "ClassLoader--参数：huang" };
			m.invoke(null, argus);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
