package com.as1124.javacore.reference;

/**
 * Java中GC以及对象引用知识
 *
 * @author As-1124 (mailto:as1124huang@gmail.com)
 */
public class ReferenceObjectTest {

	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		// GC调用
		System.out.println("[ReferenceObject] GC 执行完成");
	}
}
