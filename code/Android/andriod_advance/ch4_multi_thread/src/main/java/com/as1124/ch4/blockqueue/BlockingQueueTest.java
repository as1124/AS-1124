package com.as1124.ch4.blockqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 相比于非阻塞队列, 阻塞队列实现生产、消费者模式更加简单、简介
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BlockingQueueTest {

    private volatile boolean canStop = false;

    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    public static void main(String[] args) {
        BlockingQueueTest test = new BlockingQueueTest();
        Producer producer = test.new Producer();
        Consumer consumer = test.new Consumer();

        producer.start();
        consumer.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // not care
        } finally {
            test.canStop = true;
        }
    }

    private class Consumer extends Thread {

        @Override
        public void run() {
            while (!canStop) {
                try {
                    // take 是阻塞操作
                    Integer element = queue.take();
                    System.out.println("消费元素 == " + element);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class Producer extends Thread {

        @Override
        public void run() {
            int element = 1;
            while (!canStop) {
                try {
                    queue.put(element++);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
