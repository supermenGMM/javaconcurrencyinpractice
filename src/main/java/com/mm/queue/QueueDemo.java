package com.mm.queue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列使用
 */
public class QueueDemo {
    /**
     * 公平锁能保证，先put的肯定会先放到队列。
     * 非公平锁不能保证。
     */
    ArrayBlockingQueue queue = new ArrayBlockingQueue(2, false);//默认使用非公平锁。不能先进先出
    CyclicBarrier cyclicBarrier = new CyclicBarrier(10);

    public class Productor extends Thread {
        private Integer val;

        public Productor(int val) {
            this.val = val;
        }

        public void run() {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            try {
                System.out.println("开始-" + val);
                queue.put(val);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        List<Productor> list = new ArrayList<Productor>();
        for (int i = 0; i < 10; i++) {
            list.add(new Productor(i));
        }
        for (int i = 0; i < 10; i++) {
            list.get(i).start();
        }
        for (int i = 0; i < 10; i++) {
            System.out.println("输出的顺序"+queue.take());
        }

        Thread.sleep(4000);

    }

    final ReentrantLock reentrantLock = new ReentrantLock(true);
    public class LockTest extends Thread {
        private Integer val;

        public LockTest(int val) {
            this.val = val;
        }

        public void run() {
            System.out.println("线程启动"+Thread.currentThread().getName());
            reentrantLock.lock();
            try {
                System.out.println("执行线程"+val+",线程名="+Thread.currentThread().getName());
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    @Test
    public void testFairLock() throws InterruptedException {
        List<LockTest> list = new ArrayList<LockTest>();
        for (int i = 0; i < 10; i++) {
            list.add(new LockTest(i));
        }
        for (int i = 0; i < 10; i++) {
            list.get(i).start();
        }
        Thread.sleep(3000);
    }

}
