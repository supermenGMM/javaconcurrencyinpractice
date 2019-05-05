package com.mm.demo;

/**
 * 可见性
 * 当读和写在两个线程，当一个线程对一个变量进行了写入操作，对另一个线程是不可见的
 * 测试结果：并未测到线程读不到另一个线程写入变量的问题。是java优化？
 */
public class Thread3_1 {
    static volatile boolean ready = false;
    static volatile int number;

    static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!ready) {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                Thread.yield();
            }
            System.out.println(Thread.currentThread().getName()+":"+number);
            number = 22;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        new ReadThread().start();
        Thread.sleep(2000);
        ready = true;
        number = 10;
    }
}
