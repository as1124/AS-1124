package com.volume1.ch14.threads.object;

import com.volume1.ch14.threads.object.Bathroom.BathroomUser;

/**
 * 上厕所的人
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class Bathroom4Wash extends BathroomUser {

	public Bathroom4Wash(Bathroom bathroom, String userName) {
		super(bathroom);
		setUserName(userName);
	}

	@Override
	public void useBathroom() {
		synchronized (getBathroom()) {
			System.out.println(getUserName() + "获取浴室锁对象");
			while (!getBathroom().isCanUse()) {
				// 一直等待
				try {
					System.out.println(getUserName() + "等待...");
					getBathroom().wait();
				} catch (InterruptedException e) {
					// nothing
				}
			}
			System.out.println(getUserName() + "进入浴室放松....完毕");
		}
	}

}
