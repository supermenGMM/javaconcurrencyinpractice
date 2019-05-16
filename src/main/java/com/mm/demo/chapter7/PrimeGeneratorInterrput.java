package com.mm.demo.chapter7;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**3
 * 通过队列实现终端，通过队列做存储。
 * 练习：线程的取消
 * 通过interrput中断线程
 */
public class PrimeGeneratorInterrput extends Thread {
    private final BlockingQueue<BigInteger> queue = new LinkedBlockingDeque<BigInteger>(10);//指定生产者容量
    private boolean cancel = false;

    @Override
    public void run() {
        BigInteger bigInteger = BigInteger.ONE;
        while (!Thread.currentThread().isInterrupted()&&!cancel) {
            bigInteger = bigInteger.nextProbablePrime();
            System.out.println("添加到数组" + bigInteger);
            try {
                queue.put(bigInteger);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;//中断
            }
        }
        System.out.println("线程结束了");
    }

    public void cancel() {
        cancel = true;
        interrupt();//如果线程阻塞，那么会抛出中断异常。但是如果没阻塞，就不能中断。所以非阻塞的中断目前还需要通过字段控制。
        System.out.println("是否中断:" + Thread.currentThread().isInterrupted());
    }

    public BlockingQueue<BigInteger> get() {
        return queue;
    }

    /**
     * 生成素数一秒
     *
     * @return
     */
    public static void main(String[] args) {
        PrimeGeneratorInterrput thread = new PrimeGeneratorInterrput();
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("中断");
        } finally {
            thread.cancel();
        }
    }


}
