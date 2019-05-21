package com.mm.demo.chapter8;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * 线程死锁的例子
 */
public class ThreadDeadLock {
    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    class LoadFileTask implements Callable<String> {

        private String desc;

        public LoadFileTask(String desc) {
            this.desc = desc;
        }

        @Override
        public String call() throws Exception {
            System.out.println(desc);
            return desc;
        }
    }

    class LoadPageTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("开始渲染页面");
            Future<String> foot = executorService.submit(new LoadFileTask("foot"));
            Future<String> header = executorService.submit(new LoadFileTask("header"));
            System.out.println("开始组合页面");
            //这里死锁，任务将等待任务的执行
            return foot.get() + "页面" + header.get();
        }
    }

    @Test
    public void test() throws ExecutionException, InterruptedException {
        Future<String> submit = executorService.submit(new LoadPageTask());
        submit.get();
    }
}
