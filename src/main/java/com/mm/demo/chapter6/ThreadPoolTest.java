package com.mm.demo.chapter6;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {
    //测试，出现一个

    /**
     * 固定长度的线程.当达到最大数据，线程池规则不再变化。除非有未预期的Exception而结束，线程池会再补充一个新线程
     */
    @Test
    public void fixedThreadPool() {
        Executors.newFixedThreadPool(1);//创建固定长度的线程

    }

    /**
     * 可伸缩的线程池 .如果线程池的规模超过 现有需求，回收空闲线程。需求增加 ，添加新线程。
     */
    @Test
    public void cacheThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();//可缓存的线程池。
    }

    /**
     * 单线程的线程池，创建单一的工作者线程执行任务，如果因为异常结束则创建另外一个来替代。
     * 确保任务在队列中串行执行，如FIFO，LIFO,优先级
     */
    @Test
    public void singleThreadpool() {
        Executors.newSingleThreadExecutor();
    }

    /**
     * 创建 一个固定长度的线程池，且以延迟或定时的方式来执行，类似timer
     */
    public void scheduelPool() {
        Executors.newScheduledThreadPool(10);
    }

    class DirectExecutor implements java.util.concurrent.Executor{

        @Override
        public void execute(Runnable command) {
            command.run();
        }
    }
}
