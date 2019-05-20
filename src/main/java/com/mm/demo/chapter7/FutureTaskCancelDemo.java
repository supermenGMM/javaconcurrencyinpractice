package com.mm.demo.chapter7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskCancelDemo {
    private static final FutureTask<Integer> futureTask = new FutureTask<Integer>(new Task());
    public static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("执行。。。。是否取消"+futureTask.isCancelled());
            Thread.sleep(5000);
            if (futureTask.isCancelled()) {
                return 0;
            }else {
                return 1;
            }
        }
    }
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        futureTask.run();
        boolean cancel = futureTask.cancel(true);
        Thread.sleep(5000);
        System.out.println("是否取消："+cancel);

        System.out.println(futureTask.get());

    }
}
