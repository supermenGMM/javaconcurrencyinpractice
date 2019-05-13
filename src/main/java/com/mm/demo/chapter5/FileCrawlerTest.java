package com.mm.demo.chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class FileCrawlerTest {
    public static void main(String[] args){
        final File file = new File("D:/");
        FileFilter fileFilter = new FileFilter() {
            public boolean accept(File pathname) {
                return true;
            }
        };
        BlockingQueue<File> queue = new LinkedBlockingDeque<File>(10);

        FileCrawler fileCrawler = new FileCrawler(queue,fileFilter,file);
        new Thread(fileCrawler).start();

        new Thread(new Indexer(queue)).start();

    }
}
