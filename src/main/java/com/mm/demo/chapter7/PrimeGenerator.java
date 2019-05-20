package com.mm.demo.chapter7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/*
 * 素数生成器，使用list作为存储
 * 练习：线程的取消
 * 通过一个volite字段设置开关
 */
public class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes = new ArrayList<BigInteger>();
    private volatile boolean cancel = false;
    @Override
    public void run() {
        BigInteger bigInteger = BigInteger.ONE;
        while (!cancel){
            bigInteger = bigInteger.nextProbablePrime();
            synchronized (primes) {
                System.out.println("添加到数组"+bigInteger);
                primes.add(bigInteger);
            }
        }
    }

    public void cancel() {
        System.out.println("取消");
        cancel = true;
    }

    public synchronized List<BigInteger> get() {
        return primes;
    }

    /**
     * 生成素数一秒
     * @return
     */
    public static List<BigInteger> aSecondOfPrimes() {
        PrimeGenerator primeGenerator = new PrimeGenerator();
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

    public static void main(String[] args){
        List<BigInteger> bigIntegers = aSecondOfPrimes();
        System.out.println(bigIntegers.size()+"长度");
        for (BigInteger bigInteger :
                bigIntegers) {
            System.out.println(bigInteger);
        }
    }
}
