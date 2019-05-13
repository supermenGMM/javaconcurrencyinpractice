package com.mm.demo.five.memoizer;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger> {

    @Override
    public BigInteger comput(String arg) {
        System.out.println("计算结果开始");
        return caculate(arg);
    }

    private BigInteger caculate(String arg) {
        System.out.println("复杂耗时的计算");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new BigInteger(arg);
    }
}
