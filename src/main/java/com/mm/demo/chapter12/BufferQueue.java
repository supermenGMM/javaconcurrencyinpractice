package com.mm.demo.chapter12;

import java.util.concurrent.Semaphore;

/**
 * 基于信号量的有界缓存
 * @param <E>
 */
public class BufferQueue<E> {
    private final Semaphore availableItems = new Semaphore(0); ;
    private final Semaphore availableSpaces ;
    private final E[] items ;
    private int putPosition = 0, takePosition = 0,capacity;

    public BufferQueue(int capacity) {
        this.capacity = capacity;
        items = (E[]) new Object[capacity];
        availableSpaces = new Semaphore(capacity);
    }

    public void put(E e) throws InterruptedException {
        availableSpaces.acquire();
        insert(e);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E e = delete();
        availableSpaces.release();
        return e;
    }

    private E delete() {
        E e = items[takePosition];
        items[takePosition] = null;
        if(--takePosition<0){
            takePosition = items.length - 1;
        }
        return e;
    }

    private void insert(E e) {
        items[putPosition] = e;
        if (++putPosition >= items.length) {
            putPosition = 0;
        }
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }
    //基本单元测试
    public static void main(String[] args) throws InterruptedException {
        BufferQueue<Integer> bufferQueue = new BufferQueue<Integer>(10);
        System.out.println("是否空" + bufferQueue.isEmpty());
        System.out.println("是否满" + bufferQueue.isFull());
        for (int i = 0; i < 10; i++) {
            bufferQueue.put(1);
        }
        System.out.println("是否空" + bufferQueue.isEmpty());
        System.out.println("是否满" + bufferQueue.isFull());
        for (int i = 0; i < 10; i++) {
            Integer take = bufferQueue.take();
            System.out.println(take);
        }
        System.out.println("是否空" + bufferQueue.isEmpty());
        System.out.println("是否满" + bufferQueue.isFull());

    }


}
