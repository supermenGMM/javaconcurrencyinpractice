package com.mm.demo.chapter6.parallel;

import org.junit.Test;
import sun.misc.Cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class CompeleteServiceDemo {
    public  final static BlockingQueue blockingQueue = new LinkedBlockingDeque();
    public static void main(String[] args){
    }

    /**
     * 循环获取线程的结果
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletionService completionService = new ExecutorCompletionService(executorService);
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    Thread.sleep(50000);
                    int i1 = new Random().nextInt(10);
                    System.out.println(i1+"-----");
                    return i1;
                }
            });
        }

        //循环take获取线程的结果
        for (int i = 0; i < 10; i++) {
//            Object o = completionService.take().get();
            Object o = null;
            try {
                o = completionService.take().get(2,TimeUnit.SECONDS);//这个超时没效果，不知道为什么
            } catch (TimeoutException e) {
                e.printStackTrace();
                //取消
                //给默认的资源
                o=0;
            }
            System.out.println((Integer)o);
        }

    }

    /**
     * invokeAll所有线程
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test2()  {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable> futures = new ArrayList<Callable>();
        futures.add(new TaskQueue(2));
        futures.add(new TaskQueue(3));
        futures.add(new TaskQueue(1));
        CopyOnWriteArrayList futuresCopyOnWrite = new CopyOnWriteArrayList(futures);
        List<Future<Integer>> list = null;
        try {
            list = executorService.
                    invokeAll(futuresCopyOnWrite);
//                    invokeAll(futuresCopyOnWrite, 2, TimeUnit.SECONDS);//这个超时。如果有一个超时，后面的任务就不能再执行了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Future<Integer> future : list) {
            Integer integer = null;
            try {
                integer = future.get(1,TimeUnit.SECONDS);//这里的超时也不好用？不知道为什么
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
                System.out.println("超时");
            }
            System.out.println(integer);

        }


    }
    class TaskQueue implements Callable<Integer>{
        private int i;

        public TaskQueue(int i) {
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            Thread.sleep(1000*i);

            return 1;
        }
    }
}
