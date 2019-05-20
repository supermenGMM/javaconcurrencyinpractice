package com.mm.souretest;

import org.junit.Test;

import java.util.concurrent.*;

public class ThreadTest {
    public static void main(String[] args) throws Exception {

//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("----");
//
//            }
//        });
//        System.out.println(thread.getState());
//        thread.start();
//        System.out.println(thread.getState());
////        thread.start();
//        System.out.println("结束");
//        thread.join();
//        System.out.println(thread.getState());

        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("---------");
                return null;
            }
        };
        Object call = callable.call();
    }

    /**
     * futuretask 的特性。
     * 直接用futuretask.run方法，其实就是串行的方法。要启动线程。只有通过Thread
     * @throws InterruptedException
     * @throws ExecutionException
     */
    @Test
    public void test() throws InterruptedException, ExecutionException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(4000);
                System.out.println(Thread.currentThread().getName()+"线程名");
                return 1;
            }
        });
        System.out.println(futureTask.isDone()+"isDone");
//        Thread thread = new Thread(futureTask);
//        thread.start();

        futureTask.run();

        System.out.println("执行完代码run");
        System.out.println(futureTask.get());

        Thread.sleep(4000);


//        future.cancel()
    }

    /**
     * callable和futureTask、Thread一起用
     * runable和Thread一起用
     */
    @Test
    public void callable() {
        Callable<Integer> callable = new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                System.out.println("线程：" + Thread.currentThread().getName());
                return 1;
            }

        };

    }
}
