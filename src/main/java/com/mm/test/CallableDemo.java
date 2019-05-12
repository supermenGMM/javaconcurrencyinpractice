package com.mm.test;

import java.util.concurrent.*;

public class CallableDemo {
    static class MyCall implements Callable{

        public Object call() throws Exception {
            for (int i = 0; i < 100000; i++) {
                System.out.println("1");
            }
            return 1;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future submit = executorService.submit(new MyCall());
        boolean done = submit.isDone();
        System.out.println("done=" + done);
        Object o = submit.get();
        System.out.println(o);

    }
}
