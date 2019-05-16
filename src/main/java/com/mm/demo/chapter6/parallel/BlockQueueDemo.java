package com.mm.demo.chapter6.parallel;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockQueueDemo {
    @Test
    public void test() {
        BlockingQueue blockingQueue = new LinkedBlockingDeque();
        try {
            blockingQueue.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
