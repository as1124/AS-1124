package com.as1124.ch4.sync;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Volatile关键字保护实例域的并发操作
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class TestVolatileField {

    // 受同步保护
    public volatile int aFiled = 0;

    private volatile int loveGirl = 1;

    // Java concurrent package下自动同步的原子操作
    public AtomicInteger atomicField = new AtomicInteger(51);

    // 不受同步保护
    public String string = "";

    public int getLoveGirl() {
        return loveGirl;
    }

    public void setLoveGirl(int loveGirl) {
        this.loveGirl = loveGirl;
    }

}
