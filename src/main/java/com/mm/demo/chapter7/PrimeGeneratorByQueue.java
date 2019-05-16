package com.mm.demo.chapter7;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 通过队列实现终端，通过队列做存储。
 * 练习：线程的取消
 * 通过一个volite字段设置开关
 * 该场景有bug ：如果队列在生产者put值时，无法中断线程，
 * 因为取消执行后，可能不再消费，而任然在生产，生产时如果阻塞，
 * 是执行不到判断取消条件的代码的/
 *
 */
public class PrimeGeneratorByQueue implements Runnable {
    private final BlockingQueue<BigInteger> queue = new LinkedBlockingDeque<BigInteger>(10);//指定生产者容量
    private volatile boolean cancel = false;
    @Override
    public void run() {
        BigInteger bigInteger = BigInteger.ONE;
        while (!cancel){
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
        System.out.println("取消");
        cancel = true;
    }

    public BlockingQueue<BigInteger> get() {
        return queue;
    }

    /**
     * 生成素数一秒
     * @return
     */
    public static BlockingQueue<BigInteger> aSecondOfPrimes() {
        PrimeGeneratorByQueue primeGenerator = new PrimeGeneratorByQueue();
        Thread thread = new Thread(primeGenerator);
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("中断");
        } finally {
            primeGenerator.cancel();
        }
        return  primeGenerator.get();

    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<BigInteger> queue = aSecondOfPrimes();
        System.out.println(queue.size()+"长度");
        //如果不执行下面的取出，线程就无法结束.执行了，上面的put就不再阻塞了。可以触发取消的判断
        System.out.println("取出:"+queue.take());
    }
}
