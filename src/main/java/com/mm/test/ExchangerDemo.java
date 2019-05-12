package com.mm.test;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

/**
 * exchange 用于两个进程之间交换数据。
 * 可以交换多次。
 * 如果A方exchange。但是B方没有exchange。那个A将永远等待，知道B exchange
 * 多个线程之间会随机消费。消费不到的线程等待
 */
public class ExchangerDemo {
    class Product extends Thread {
        private Exchanger exchanger;
        private CountDownLatch countDownLatch;


        public Product(Exchanger exchanger) {
            this.exchanger = exchanger;
        }

        public Product(Exchanger exchanger, CountDownLatch countDownLatch) {
            this.exchanger = exchanger;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try {
                System.out.println("当前线程:"+Thread.currentThread()+"--小明");
                Object data = exchanger.exchange(new String("小明"));
                System.out.println(Thread.currentThread().getName()+":"+data);

                List<String> list = (List<String>) exchanger.exchange(new String("校长"));
                System.out.println("第二次交换"+Thread.currentThread().getName()+list);

                System.out.println(Thread.currentThread().getName()+"执行结束");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();

        }
    }

    class Customer extends Thread {
        private Exchanger exchanger;
        private CountDownLatch countDownLatch;


        public Customer(Exchanger exchanger) {
            this.exchanger = exchanger;
        }

        public Customer(Exchanger exchanger, CountDownLatch countDownLatch) {
            this.exchanger = exchanger;
            this.countDownLatch = countDownLatch;
        }

        public void run() {
            try {
                System.out.println("当前线程:"+Thread.currentThread()+"消费者");
                List<String> list = new ArrayList<String>();
                list.add("呵呵");
                list.add("喜喜");
                Object data = exchanger.exchange(list);
                System.out.println(Thread.currentThread().getName()+":"+data);

//                System.out.println("第二次交换"+Thread.currentThread().getName()+":"+exchanger.exchange(list));

                System.out.println(Thread.currentThread().getName()+"执行结束");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();

        }
    }

    @Test
    public void test() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Exchanger exchanger = new Exchanger();
        Product product = new Product(exchanger,countDownLatch);
        product.start();
        Customer customer = new Customer(exchanger, countDownLatch);
        customer.start();

        countDownLatch.await();

    }
}
