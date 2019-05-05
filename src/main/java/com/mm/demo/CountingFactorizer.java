package com.mm.demo;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 统计次数
 */
public class CountingFactorizer {
    private final AtomicLong count = new AtomicLong();

    public long getCount() {
        return count.get();
    }

    public void service(int i) {
        BigInteger[] factor = factor(i);
        long l = count.incrementAndGet();
        System.out.println("请求次数为"+l);
        doSomething(factor);
        return ;
    }
    private void doSomething(BigInteger[] factor) {
        System.out.println("dosomething");
    }

    private BigInteger[] factor(int i) {
        return new BigInteger[]{new BigInteger("" + i),
                new BigInteger("" + (i + 1)),
                new BigInteger("" + (i + 2))};
    }

    public static void main(String[] args) throws Exception {
        final CountingFactorizer countingFactorizer = new CountingFactorizer();
        for (int i=0;i<100;i++) {
            new  Thread(){
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countingFactorizer.service(1);
                }
            }.start();
        }

    }


}
