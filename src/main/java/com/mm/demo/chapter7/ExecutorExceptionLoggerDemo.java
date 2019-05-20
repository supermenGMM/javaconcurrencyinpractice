package com.mm.demo.chapter7;

import com.mm.demo.chapter7.uncacheexception.UncacheExceptionHandlerLogger;

import java.util.concurrent.*;

/**
 * 线程工厂监听线程异常
 */
public class ExecutorExceptionLoggerDemo {

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(10, new
                ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread thread = new Thread(r);
                        thread.setUncaughtExceptionHandler(new UncacheExceptionHandlerLogger());
                        return thread;
                    }
                });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("runable,start");
                throw new RuntimeException("运行时异常");
            }
        });

        Future<Integer> future2 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("callable,start");
                int a = 1 / 0;
                System.out.println("callable,end");
                return 1;
            }
        });

        System.out.println("运行两个线程");
        try {
            future2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
