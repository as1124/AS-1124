package com.as1124.background_tasks.threadpool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义线程工厂；可以设置线程的名称等
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;

    public MyThreadFactory() {
        SecurityManager sm = System.getSecurityManager();
        this.group = sm != null ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = "[ ThreadPool ]-" + poolNumber.getAndIncrement() + "-thread-";
    }

    public Thread newThread(Runnable task) {
        Thread t = new Thread(this.group, task, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
