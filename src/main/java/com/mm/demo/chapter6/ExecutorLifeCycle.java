package com.mm.demo.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorLifeCycle {
    static class TaskRunable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("运行");
        }
    }
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.execute(new TaskRunable());
        executorService.execute(new TaskRunable());
        executorService.execute(new TaskRunable());

        executorService.shutdown();
        System.out.println(executorService.isTerminated());
        while (!executorService.isTerminated()) {
            System.out.println("继续等待");
        }
        System.out.println("执行结束");
    }
}