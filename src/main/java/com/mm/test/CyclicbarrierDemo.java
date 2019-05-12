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
//                cyclicBarrier.await();//等待其他线程运行到这里
                cyclicBarrier.await(10, TimeUnit.MILLISECONDS);
                System.out.println("线程"+Thread.currentThread()+"开始下面的执行");
            } catch (InterruptedException e) {
                e.printStackTrace();

            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            Writer writer = new Writer(cyclicBarrier,i);
            writer.start();
        }
        Thread.sleep(10000);

    }

}
