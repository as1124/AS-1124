package com.as1124.ch4.locker;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class PayManagement {

    private double[] accounts;

    private Lock payLock;

    private Condition condition;

    public PayManagement(int n, double money) {
        accounts = new double[n];
        payLock = new ReentrantLock();

        // 得到条件对象
        condition = payLock.newCondition();
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = money;
        }
    }

    public void transfer(int from, int to, int amount) throws InterruptedException {
        payLock.lock();
        Log.i("并发", "当前线程名称===" + Thread.currentThread().getName());
        try {
            while (accounts[from] < amount) {
                Log.i("并发", from + "【转账资金不足】");
                // 阻塞当前线程, 并放弃锁; 之所以用while进行循环是因为 signalAll() 之后不能确定当前账户一定有充足金额进行转账
                condition.await();
            }

            accounts[from] = accounts[from] - amount;
            accounts[to] = accounts[to] + amount;
            condition.signalAll();

            Log.i("并发", "转账成功");
        } finally {
            payLock.unlock();
        }
    }

}
