package com.mm.test;

import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 栅栏
 *
 */
public class CyclicbarrierDemo {
    //测试。创建四个线程，当所以线程都执行完写操作，才进行下面的操作
    class Writer extends Thread {
        private CyclicBarrier cyclicBarrier ;
        private int num;
        public Writer(CyclicBarrier cyclicBarrier,int num) {
            this.cyclicBarrier = cyclicBarrier;
            this.num = num;
        }

        public void run() {
            System.out.println("线程" + Thread.currentThread().getName() + "正在写。。。");
            try {

                Thread.sleep(5000*num);
                System.out.println("线程"+Thread.currentThread()+"执行完毕，等待其他线程执行。");
                cyclicBarrier.await();//等待其他线程运行到这里
//                cyclicBarrier.await(10, TimeUnit.MILLISECONDS);//会抛出TimeoutException、BrokenBarrierException异常后继续执行

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } /*catch (TimeoutException e) {
                e.printStackTrace();
            }*/
            System.out.println("线程"+Thread.currentThread()+"执行完毕");
        }
    }

    @Test
    public void testCyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
            @Override
            public void run() {
                System.out.println("等待成功。执行栅栏方法"+Thread.currentThread().getName());
            }
        });//等待线程达到栅栏，会有一个线程执行Runable
        for (int i = 0; i < 5; i++) {
            Writer writer = new Writer(cyclicBarrier,i);
            writer.start();
        }


//栅栏是可重用的。但是countdownlatch不可以
//        for (int i = 0; i < 5; i++) {
//            Writer writer = new Writer(cyclicBarrier,i);
//            writer.start();
//        }
        Thread.sleep(60000);



    }

}
