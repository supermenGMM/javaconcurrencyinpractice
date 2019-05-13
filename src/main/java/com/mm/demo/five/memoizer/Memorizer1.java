package com.mm.demo.five.memoizer;

import java.math.BigInteger;
import java.util.HashMap;

/**
 * 使用hashMap和同步机制来初始化缓存，
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
