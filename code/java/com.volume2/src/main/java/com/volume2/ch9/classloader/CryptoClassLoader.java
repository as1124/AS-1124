package com.volume2.ch9.classloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class loader loads encrypted class files.
 * class 文件加载有防篡改能力，直接替换package名会出现加载异常
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class CryptoClassLoader extends ClassLoader {

	private String classRoot;

	public CryptoClassLoader(String pathRoot) {
		// 设置parent为null是为了避免加载双亲的classpath上的类
		super(null);
		classRoot = pathRoot;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classBytes = null;
		try {
			classBytes = loadClassBytes(name);
			Class<?> cl = defineClass(name, classBytes, 0, classBytes.length);
			if (cl != null) {
				return cl;
			} else {
				throw new ClassNotFoundException(name);
			}
		} catch (IOException e) {
			throw new ClassNotFoundException(name);
		}

	}

	private byte[] loadClassBytes(String name) throws IOException {
		String cname = classRoot + name.replace('.', '/') + ".myclass";
		return Files.readAllBytes(Paths.get(cname));
	}
}
