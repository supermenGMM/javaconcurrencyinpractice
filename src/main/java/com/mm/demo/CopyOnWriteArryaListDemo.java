package com.mm.demo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class CopyOnWriteArryaListDemo {
    public static void main(String[] args){
        List list
                = new ArrayList();
        list.add("1");

        Collection collection = Collections.synchronizedCollection(list);
        CopyOnWriteArrayList copy = new CopyOnWriteArrayList();
        ReentrantLock lock = new ReentrantLock();

        lock.tryLock();
        lock.unlock();

    }
}
