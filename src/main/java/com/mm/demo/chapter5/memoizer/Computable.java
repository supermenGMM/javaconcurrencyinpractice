package com.mm.demo.chapter5.memoizer;

/**
 * 构建高效且可伸缩的结果缓存
 * @param <A>
 * @param <V>
 */
public interface Computable<A,V> {
    V comput(A arg);
}
