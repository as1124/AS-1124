package com.volume1.ch14.threads.object;

import com.volume1.ch14.threads.object.Bathroom.BathroomUser;

/**
 * 维修厕所的人
 * 
 * @author as-1124(mailto:as1124huang@gmail.com)
 *
 */
public class Bathroom4Repair extends BathroomUser {

	public Bathroom4Repair(Bathroom bathroom, String userName) {
		super(bathroom);
		setUserName(userName);

	}

	@Override
	public void useBathroom() {
		synchronized (getBathroom()) {
			System.out.println(getUserName() + "获取浴室锁对象");
			getBathroom().setCanUse(false);

			System.out.println("水管工给正在维修中...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// nothing
			}
			System.out.println("浴室维修完成...请享用");

			getBathroom().setCanUse(true);

			getBathroom().notifyAll();
		}
	}

}
