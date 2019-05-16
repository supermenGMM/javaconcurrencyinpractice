package com.mm.demo.chapter7;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeRun {

    public static void timeRun
            (final Runnable r,
             final long timeout, TimeUnit timeUnit){
        class RethrowableTask implements Runnable {
            private Throwable throwable;
            @Override
            public void run() {
                try {
                    r.run();
                } catch (Throwable throwable) {
                    this.throwable = throwable;
                }
            }

            void reThrow() throws Exception {
                if (throwable != null) {
                    throw launderThrowable(throwable);
                }
            }

            Exception launderThrowable(Throwable throwable) {
                return new Exception(throwable);
            }
        }

        RethrowableTask rethrowableTask = new RethrowableTask();
        final Thread thread = new Thread(rethrowableTask);
        thread.start();

        ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(10);
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                thread.interrupt();
            }
        }, timeout, timeUnit);

    }

    public static void main(String[] args){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("----");
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timeRun(r, 1, TimeUnit.SECONDS);
    }

}
