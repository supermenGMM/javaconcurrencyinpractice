package com.mm.demo.five.memoizer;

import java.util.concurrent.*;

/**
 * 比较好的设计
 */
public class Memorizer3<A,V> implements Computable<A,V>{
    private final ConcurrentMap<A, Future<V>> concurrentMap = new ConcurrentHashMap();
    private final Computable<A, V> computable;

    public Memorizer3(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V comput(final A arg) {
        Future<V> future = concurrentMap.get(arg);
        try {
            if (future == null) {//因为竞态条件，并发下会发生计算两年次，但是几率很小，
                // 因为future创建过程比执行完计算过程的时间段很多。 可以接受。比加同步锁性能要好
                FutureTask<V> futureTask = new FutureTask<V>(new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.comput(arg);
                    }
                });
                future = concurrentMap.putIfAbsent(arg, future);//这里防止了被多次赋值
                if (future == null) {//这里为空。就是第一次赋值，第一次要执行run。
                    future = futureTask;
                    futureTask.run();
                }
            }
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            future.cancel(true);
            concurrentMap.remove(arg);//异常处理。需要把计算不出结果的future移除
        }
        return null;
    }
}
