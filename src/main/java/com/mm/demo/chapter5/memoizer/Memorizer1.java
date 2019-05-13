package com.mm.demo.chapter5.memoizer;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * 使用hashMap和同步机制来初始化缓存
 *
 * 加锁导致并发时，如果首次同时多个线程获取同一个key的value,
 * 第一个线程获取锁之后，其他线程要等待执行结束才可以继续。
 *
 * 这个版本是书中的改进版，书中直接在方法上加锁，那会造成更多的性能问题。
 */
public class Memorizer1<A,V> implements Computable<A,V>{
    private final Computable<A,V> computable;
    private final HashMap<A,V> map = new HashMap();

    public Memorizer1(Computable computable) {
        this.computable = computable;
    }

    @Override
    public V comput(A arg) {
        V v = map.get(arg);
        if (v == null) {
            synchronized (this) {
                v = map.get(arg);//如果这里不获取，并发时同一个arg，可能会执行两次下面的计算
                if (v == null) {
                    System.out.println("第一次获取");
                    v = computable.comput(arg);
                    map.put(arg, v);
                }else {
                    System.out.println("同步方法从缓存中获取");
                }
            }
        }else {
            System.out.println("不同步从缓存中获取");
        }

        return v;
    }

    public static void main(String[] args){
        ExpensiveFunction expensiveFunction = new ExpensiveFunction();
        final Memorizer1<String, BigInteger> memorizer1 = new Memorizer1(expensiveFunction);
        new Thread() {

            @Override
            public void run() {
                System.out.println(memorizer1.comput("1"));
            }
        }.start();

        new Thread() {

            @Override
            public void run() {
                System.out.println(memorizer1.comput("1"));
            }
        }.start();
        new Thread() {

            @Override
            public void run() {
                System.out.println(memorizer1.comput("2"));

            }
        }.start();




    }
}
