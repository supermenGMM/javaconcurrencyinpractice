package com.mm.demo.chapter12;

import org.junit.Test;

public class TestBlock {
    /**
     * 阻塞测试
     */
    @Test
    public void testBlock() throws InterruptedException {

        class NewTask extends Thread {
            final BufferQueue<Integer> bufferQueue = new BufferQueue(10);
            public void run() {

                try {
                    bufferQueue.take();
                } catch (InterruptedException e) {

                    System.out.println("打断！！");
                }
            }
        }
        NewTask newTask = new NewTask();

        newTask.start();

        newTask.interrupt();
        newTask.join();

    }

}
