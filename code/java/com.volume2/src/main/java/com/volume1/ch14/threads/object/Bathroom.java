package com.volume1.ch14.threads.object;

/**
 * 代表资源, 浴室
 * 
 * @author as-1124(mailto:as1124huang@mail.com)
 */
class Bathroom {
	/**
	 * 是否可用
	 */
	private volatile boolean canUse = false;

	/**
	 * 锁描述对象.
	 */
	private Object lock = new Object();

	public boolean isCanUse() {
		return canUse;
	}

	public void setCanUse(boolean canUse) {
		this.canUse = canUse;
	}

	public Object getLock() {
		return lock;
	}

	/**
	 * 浴室使用对象标志接口
	 *
	 */
	public static abstract class BathroomUser {

		private Bathroom myRoom;

		private String userName;

		public BathroomUser(Bathroom bathroom) {
			myRoom = bathroom;
		}

		public abstract void useBathroom();

		public Bathroom getBathroom() {
			return this.myRoom;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}
}