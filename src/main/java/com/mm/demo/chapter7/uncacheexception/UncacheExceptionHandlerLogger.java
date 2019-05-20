package com.mm.demo.chapter7.uncacheexception;

public class UncacheExceptionHandlerLogger implements Thread.UncaughtExceptionHandler{

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("线程为:"+t.getName()+"异常为:"+e);
    }
}
