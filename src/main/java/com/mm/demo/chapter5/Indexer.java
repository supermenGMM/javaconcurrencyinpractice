package com.mm.demo.chapter5;

import java.io.File;
import java.util.concurrent.BlockingQueue;

/**
 * 给文件建立索引
 */
public class Indexer implements Runnable{
    private final BlockingQueue queue;

    public Indexer(BlockingQueue queue) {
        this.queue = queue;
    }

    public void run() {
        while (true) {
            try {
                indexFile(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    //建立索引
    void indexFile(Object object) {

        File file = (File) object;
        System.out.println("拿到消费:"+file.getName());
        System.out.println("建立索引");
    }
}
