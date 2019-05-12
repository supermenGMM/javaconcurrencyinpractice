package com.mm.demo;

import java.math.BigInteger;

/**
 * 缓存上一个结果。也就是如果一直传入同一个参数，则不用重复计算
 */
public class CacheFactorizer {
    private BigInteger[] lastFactor;
    private int lastNumber;
    public void service(int i) {
        BigInteger[] factor = null;
        //优化前
//        synchronized (this) {
//            if (i == lastNumber) {//读取
//                factor = lastFactor;
//            } else {
//                factor = factor(i);
//                //写入
//                lastFactor = factor.clone();
//                lastNumber = i;
//            }
//        }
//        优化后:方案1 .好处是factor(i)方法执行不用加锁
//读取
        synchronized (this) {
            if (i == lastNumber) {
                factor = lastFactor;
            }
        }
        //读写加锁分开。这样可以
        if (factor == null) {
            factor = factor(i);
            synchronized (this) {
                //写入
                lastFactor = factor.clone();
                lastNumber = i;
            }
        }
        doSomething(factor);
    }

    private void doSomething(BigInteger[] factor) {
        System.out.println("dosomething");
    }

    private BigInteger[] factor(int i) {
        return new BigInteger[]{new BigInteger("" + i),
                new BigInteger("" + (i + 1)),
                new BigInteger("" + (i + 2))};
    }


}
