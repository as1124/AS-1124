package com.as1124.ch4.locker;

/**
 * Volatile关键字保护实例域的并发操作.<br/>
 * <ul>
 * <li>volatile只保证可见性</li>
 * <li>volatile不保证原子性</li>
 * <li>volatile禁止指令重排</li>
 * <li>volatile不能保证并发</li>
 * <li>所以volatile常用于状态标志（可见性）</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class VolatileTest {

    // 初始边界为 [0,5]
    private volatile int lowSide = 0;
    private volatile int upSide = 5;

    public int getLowSide() {
        return lowSide;
    }

    public int getUpSide() {
        return upSide;
    }

    public void setLowSide(int value) {
        if (value > this.upSide) {
            throw new IllegalArgumentException("下限不能高于上线");
        }
        this.lowSide = value;
    }

    public void setUpSide(int value) {
        if (value < this.lowSide) {
            throw new IllegalArgumentException("上限不能低于下限");
        }
        this.upSide = value;
    }

    public static void main(String[] args) {
        final VolatileTest test = new VolatileTest();

        Thread thread1 = new Thread(() -> test.setLowSide(4));
        Thread thread2 = new Thread(() -> test.setUpSide(3));

        thread1.start();
        thread2.start();

        System.out.println(test.getLowSide() + "," + test.getUpSide());


        // 因为没有同步保证并发, 所以存在两个线程同时修改边界值导致最终结果出现 [4, 3] 的可能性
    }

}
