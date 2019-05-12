package com.mm.demo;

import java.security.PublicKey;

/**
 * 发布publishing和逸出escape
 * 测试逸出
 * 测试结果：正常。并没用报错
 */
public class Thread3_2 {
    interface EscapeInterface{
        void test();
    }

    public void aaa() {
        System.out.println("aaa");
    }
//不安全的。不用这样做
    public Thread3_2() {
        new EscapeInterface(){
            public void test() {
                Thread3_2.this.aaa();//构造函数中，内部类中使用了外部类的方法。
            }
        }.test();
    }

    public static void main(String[] args){
        Thread3_2 t = new Thread3_2();

    }
}
