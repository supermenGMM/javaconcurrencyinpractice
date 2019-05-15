package com.mm.demo.chapter6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池生命周期
 * 运行、关闭、已终止
         * shutdown 关闭。正在执行的线程继续执行，没有执行的不再执行
         * shutdownnow 如果有线程正在执行，不会等待执行结束，会直接打断。
 *      关闭后提交任务，将会跑出异常RejectedExecutionHandler
 */
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
//        executorService.shutdownNow();

        System.out.println("是否终止"+executorService.isTerminated());
        System.out.println("是否shutdown:" + executorService.isShutdown());
        executorService.execute(new TaskRunable());

        while (!executorService.isTerminated()) {
            System.out.println("继续等待");
        }
        System.out.println("执行结束"+executorService.isTerminated());
    }
}