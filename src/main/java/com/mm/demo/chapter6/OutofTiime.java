package com.mm.demo.chapter6;

import org.junit.Test;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 错误的timer
 * 不建议使用Timer。
 * Timer的问题：
 * 1。在执行所有定时任务时只会创建一个线程。如果一个时间过长，将破坏其他task的精确性
 * 2.如果timerTask抛出未检查异常，将终止定时线程。Timer线程不会捕获异常，而错误的认为整个Timer被取消
 */
public class OutofTiime {

    /*
    测试Timer的问题1
    两个任务的线程名是一样的
    如果任务1 执行时间小于2秒。那么任务2比任务一执行的时间晚2秒
            可任务1执行超过2秒。例如5秒好，经测试，那么任务2 将比任务一执行晚5秒
         */
    @Test
    public void testLongTask() throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new LongTask(), 2);
        Thread.sleep(2000);
        timer.schedule(new LongTask(), 2);



        Thread.sleep(10000);

    }

    /**
     * 测试Timer 的问题2
     *
     * 任务1 抛出异常。任务2直接不执行了
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new ThrowTask(), 1);
        Thread.sleep(1);
        timer.schedule(new ThrowTask(), 2);

        Thread.sleep(5000);
    }

    static class ThrowTask extends TimerTask {
        @Override
        public void run() {
            throw new RuntimeException();
        }
    }

    static class LongTask extends TimerTask {
        @Override
        public void run() {
            try {
                System.out.println(new Date().toString()+":"+Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
