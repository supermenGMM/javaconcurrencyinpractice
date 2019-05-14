package com.mm.demo.chapter5;

import java.util.concurrent.CountDownLatch;

/**
 * 10个线程通过闭锁控制同时启动。再通过闭锁控制，所有子线程执行完，再继续执行子线程。
 * 闭锁测试。
 * 延迟线程的进度直到其达到终止状态
 * 闭锁可以是一扇门，如果这扇门是关闭的，没有任何线程可以通过，
 * 如果这扇门打开，任何线程都可以通过，且这扇门永远处于打开状态
 */
public class TestHarnessTwo {
    private static CountDownLatch startLatch =
            new CountDownLatch(10);//启动门
    private static CountDownLatch endLatch =
            new CountDownLatch(10);//结束门
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    try {
                        startLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("开始 锁已打开"+Thread.currentThread().getName());
                    System.out.println("执行" + Thread.currentThread().getName());
                    endLatch.countDown();

                }
            }.start();
            System.out.println("开始 递减");
            startLatch.countDown();//主线程通过这个控制子线程的启动
        }
        System.out.println("开始执行");

        endLatch.await();//主线程通过这个来等待子线程完成
        System.out.println("结束锁已打开"+endLatch.getCount());
    }

}
