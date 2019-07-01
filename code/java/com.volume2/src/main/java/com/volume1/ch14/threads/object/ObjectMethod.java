package com.volume1.ch14.threads.object;

import com.volume1.ch14.threads.object.Bathroom.BathroomUser;

/**
 * 学习{@link Object#wait()} 和 {@link Object#notify()}方法的含义和使用场景.
 * <p>
 * 通过{@link Bathroom#getLock()}作为锁进行同步操作, wait()/notify()；
 * 和通过直接对{@link Bathroom}实例进行锁可以达到同样的效果
 * </p>
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class ObjectMethod {

	public static void main(String[] args) {
		Bathroom bath = new Bathroom();

		BathroomUser one = new Bathroom4Wash(bath, "张三");
		new Thread(() -> {
			one.useBathroom();
		}).start();

		BathroomUser two = new Bathroom4Wash(bath, "李四");
		new Thread(() -> {
			two.useBathroom();
		}).start();

		BathroomUser three = new Bathroom4Wash(bath, "王五");
		new Thread(() -> {
			three.useBathroom();
		}).start();

		BathroomUser worker = new Bathroom4Repair(bath, "维修工");
		new Thread(() -> {
			worker.useBathroom();
		}).start();
	}
}
