package com.mm.demo.chapter8;

import java.util.concurrent.*;

/**
 * 线程池的创建和销毁，已经各个配置的修改
 * 测试结果：
 *1. 有界线程池，有界队列，当线程满后不会再创建新的线程。如果队列满了，则任务被抛弃。（默认饱和策略为absortPolicy）
 *2.无界线程池，有界队列，则可无界的创建新的线程。？那队列还有什么用？
 *
 */
public class ThreadCreateAndDestory {

    public static void main(String[] args) {
        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2,
                Integer.MAX_VALUE,5, TimeUnit.SECONDS,
               new ArrayBlockingQueue<Runnable>(2));

        //设置饱和策略
//        executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        //测试线程池中 的线程数等于线程池的基本大小时，仅当工作队列满的时候才会创建线程
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务1线程"+Thread.currentThread().getName());
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("任务2线程"+Thread.currentThread().getName());
            }
        });
        /*
        两个之后线程不会再创建。只有两个线程执行任务。
        测试结果：执行第三个任务的线程是第一个任务的线程。
         */
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务3线程" + Thread.currentThread().getName());
            }
        });
/*
        两个之后线程不会再创建。只有两个线程执行任务。
        测试结果：执行第三个任务的线程是第一个任务的线程。
         */
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("任务4线程" + Thread.currentThread().getName());
            }
        });
        /*
        两个之后线程不会再创建。只有两个线程执行任务。
        测试结果：执行第三个任务的线程是第一个任务的线程。
         */
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("任务5线程" + Thread.currentThread().getName());
                }
            });
        } catch (RejectedExecutionException e) {
            System.out.println("任务5被抛弃");
        }
        /*
        两个之后线程不会再创建。只有两个线程执行任务。
        测试结果：执行第三个任务的线程是第一个任务的线程。
         */
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("任务6线程" + Thread.currentThread().getName());
                }
            });
        } catch (RejectedExecutionException e) {
            System.out.println("任务6被抛弃");
        }
        executorService.shutdown();
    }
}
