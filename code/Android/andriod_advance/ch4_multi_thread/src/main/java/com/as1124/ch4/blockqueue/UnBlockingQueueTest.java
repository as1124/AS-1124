package com.as1124.ch4.blockqueue;

import java.util.PriorityQueue;

/**
 * 利用非阻塞队列和对象锁实现生产者和消费者模型
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class UnBlockingQueueTest {

    private volatile boolean canStop = false;


    // 这个是非阻塞队列, not blocking
    private PriorityQueue<Integer> queue = new PriorityQueue<>(10);

    public static void main(String[] args) {
        UnBlockingQueueTest test = new UnBlockingQueueTest();
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
                synchronized (queue) {
                    // while 循环用来保证再次唤醒时继续检查队列不为空
                    while (queue.size() == 0) {
                        System.out.println("队列空, 等待数据");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }
                    }

                    // 每次消费队首元素
                    Integer element = queue.poll();
                    System.out.println("消费元素 == " + element);
                    queue.notify();
                }
            }
        }
    }

    private class Producer extends Thread {

        @Override
        public void run() {
            int element = 1;
            while (!canStop) {
                synchronized (queue) {
                    while (queue.size() == 10) {
                        System.out.println("队列满....");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            queue.notify();
                            e.printStackTrace();
                        }
                    }

                    // 每次插入一个元素
                    queue.offer(element++);
                    queue.notify();
                }
            }
        }
    }
}
