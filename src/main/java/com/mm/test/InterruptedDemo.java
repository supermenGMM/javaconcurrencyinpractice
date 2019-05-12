package com.mm.test;

import java.util.concurrent.Callable;

public class InterruptedDemo {
    public synchronized void aaa() {
        int i = 1;
        while (i < 100) {
            System.out.println(Thread.interrupted());
            if (!Thread.interrupted()) {
                System.out.println(i);
            }
            if (i == 50) {
                Thread.currentThread().interrupt();
                System.out.println(Thread.interrupted());
            }

            i++;
        }


    }
    public static void main(String[] args) throws InterruptedException {
        InterruptedDemo interruptedDemo = new InterruptedDemo();

        interruptedDemo.aaa();
        interruptedDemo.bbb();


    }

    public synchronized void bbb() {
        System.out.println("bbb");
    }
}
