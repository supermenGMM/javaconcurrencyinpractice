package com.mm.queue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 队列使用
 * 公平和非公平锁的使用
 */
public class QueueDemo {
    /**
     * 公平锁能保证，先put的肯定会先放到队列。
     * 非公平锁不能保证。
     * 队列使用公平锁，那么队列是FIFO的
     */

    /**
     * 测试1：使用 了公平锁或非公平锁的队列ArrayLinkQueue
     * ArrayLinkQueue：达到容量，就等待
     */
    ArrayBlockingQueue arrayQueue = new ArrayBlockingQueue(2, false);//默认使用非公平锁。不能先进先出
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
                arrayQueue.put(val);
                System.out.println("结束 -"+val);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ArrayLinkQueue使用公平和非公平锁的区别
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        List<Productor> list = new ArrayList<Productor>();
        for (int i = 0; i < 10; i++) {
            list.add(new Productor(i));
        }
        for (int i = 0; i < 10; i++) {
            list.get(i).start();
        }

        //输出
        Thread.sleep(4000);
        for (int i = 0; i < 10; i++) {
            System.out.println("输出的顺序"+ arrayQueue.take());
        }

        Thread.sleep(4000);

    }

    /**
     * 测试2：公平锁或非公平锁的演示
     */
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

    /**
     * 测试3：队列 LinkedBlockingQueue
     */
    LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue(2);
    public class Productor2 extends Thread {
        private Integer val;

        public Productor2(int val) {
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
                linkedBlockingQueue.put(val);
                System.out.println("结束 -"+val);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * LinkedBlockingQueue使用公平和非公平锁的区别
     * @throws InterruptedException
     */
    @Test
    public void testLinkBlockQueu() throws InterruptedException {
        List<Productor2> list = new ArrayList<Productor2>();
        for (int i = 0; i < 10; i++) {
            list.add(new Productor2(i));
        }
        for (int i = 0; i < 10; i++) {
            list.get(i).start();
        }
        //输出
        Thread.sleep(3000);
        for (int i = 0; i < 10; i++) {
            System.out.println("输出的顺序"+ linkedBlockingQueue.take());
        }

        Thread.sleep(4000);

    }

}
