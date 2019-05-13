package com.mm.demo.chapter5;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 计数信号量：控制同时访问某个特定资源的操作数量，或同时执行某个指定操作的数量
 * 还可以用来实现某种资源池、或对容器施加边界。
 */
public class CountingSemaphore {
    Set<String> set;
    Semaphore semaphore;

    public CountingSemaphore(int bound) {
        set = new HashSet();
        semaphore = new Semaphore(bound);
    }

    //添加 数据。申请资源。
    public boolean add(String ele) throws InterruptedException {
        boolean add = set.add(ele);
        if(add){
            semaphore.acquire(1);
        }
        System.out.println("添加资源"+ele+","+add);
        return add;
    }

    //添加 数据。申请资源。
    public boolean remove(String ele) throws InterruptedException {

        boolean remove = set.remove(ele);
        if (remove) {
            semaphore.release(1);
        }
        System.out.println("释放资源"+ele+","+remove);
        return remove;
    }

    public static void main(String[] args){
        //测试。获取第11个就开始等待。然后获取释放，则获取到
        final CountingSemaphore countingSemaphore = new CountingSemaphore(10);

        new Thread() {
            public void run() {
                try {
                    for (int i = 0; i < 10; i++) {
                        countingSemaphore.add(""+i);
                    }

                    while (countingSemaphore.add("11")) {
                        System.out.println("获取到资源");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        System.out.println("获取结束");

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(5000);
                    for (int i = 0; i < 10; i++) {
                        if (i == 5) {
                            Thread.sleep(5000);
                        }
                        countingSemaphore.remove(""+i);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
