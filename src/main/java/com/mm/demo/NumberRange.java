package com.mm.demo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个状态变量直接存在着不变性条件，则会委托失败
 */
public class NumberRange {
    private final AtomicInteger lower = new AtomicInteger();
    private final AtomicInteger upper = new AtomicInteger();

    public void setLower(int i) throws InterruptedException {
        //不安全的“先检查后执行”
        if (i > upper.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + "> upper");
        }
        Thread.sleep(2900);//这里睡一会。等待upper设为一个比较小的值。
        lower.set(i);
    }
    public void setUpper(int i) {
        //不安全的“先检查后执行”
        if (i < lower.get()) {
            throw new IllegalArgumentException("can't set lower to " + i + "> upper");
        }
        upper.set(i);
    }

    public boolean isInRange() {
        if (lower.get() > upper.get()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {

        //测试。多线程下。出现lower>upper的情况.
        final NumberRange numberRange = new NumberRange();
        numberRange.setUpper(10);
        numberRange.setLower(1);
        for (int i=1;i<2;i++) {
            new Thread(){
                public void run() {
                    try {
                        numberRange.setLower(9);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            new Thread(){
                public void run() {
                    numberRange.setUpper(7);
                }
            }.start();
        }
        Thread.sleep(3000);
        System.out.println(numberRange.isInRange());
    }



}
