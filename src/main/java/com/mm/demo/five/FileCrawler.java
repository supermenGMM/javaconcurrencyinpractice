package com.mm.demo.five;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;

/**
 * 桌面搜索中的生产者消费者任务
 */
public class FileCrawler implements Runnable {

    private final BlockingQueue<File> fileQueue;
    private final FileFilter fileFilter;
    private final File file;

    public FileCrawler(BlockingQueue<File> fileQueue, FileFilter fileFilter, File file) {
        this.fileQueue = fileQueue;
        this.fileFilter = fileFilter;
        this.file = file;
    }


    public void run() {
        try {
            crawl(file);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void crawl(File root) throws InterruptedException {
        File[] files = root.listFiles(fileFilter);
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                crawl(file);
            }else {
                fileQueue.put(file);
            }
        }
    }
}
