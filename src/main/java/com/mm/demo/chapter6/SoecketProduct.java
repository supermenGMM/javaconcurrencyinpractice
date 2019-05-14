package com.mm.demo.chapter6;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

public class SoecketProduct {
    private static final int count = 20;
    private static CountDownLatch startLatch =
            new CountDownLatch(count);//启动门
    private static CountDownLatch endLatch =
            new CountDownLatch(count);//结束门


    public static void main(String[] args) throws InterruptedException, IOException {
        for (int i = 0; i < count; i++) {
            final int num =i;
            new Thread() {
                public void run() {
                    try {
                        startLatch.await();
                        System.out.println("开始 锁已打开"+Thread.currentThread().getName());
                        System.out.println("执行" + Thread.currentThread().getName());
                        Socket socket = new Socket("127.0.0.1", 801);
                        OutputStream outputStream = socket.getOutputStream();
                        outputStream.write(("helloworld"+num).getBytes());
                        outputStream.close();
                        socket.close();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    endLatch.countDown();

                }
            }.start();
            System.out.println("开始 递减");
            startLatch.countDown();//主线程通过这个控制子线程的启动
        }
        System.out.println("开始执行");

        endLatch.await();//主线程通过这个来等待子线程完成
        System.out.println("结束锁已打开"+endLatch.getCount());

    }

}
