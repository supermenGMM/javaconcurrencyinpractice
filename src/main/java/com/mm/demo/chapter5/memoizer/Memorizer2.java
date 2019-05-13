package com.mm.demo.chapter5.memoizer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 使用ConcurrentHashMap
 * 不加锁，因为有v的判null操作，仍然会在并发的时候发生执行执行两次计算。
 *  且arg可能会被多次赋值。
 */
public class Memorizer2<A,V> implements Computable<A,V>{
    private final ConcurrentMap<A,V> concurrentMap = new ConcurrentHashMap();
    private final Computable<A, V> computable;

    public Memorizer2(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V comput(A arg) {
        V v = concurrentMap.get(arg);
        if (v == null) {
            v = computable.comput(arg);
            concurrentMap.put(arg, v);
        }
        return v;
    }
}
