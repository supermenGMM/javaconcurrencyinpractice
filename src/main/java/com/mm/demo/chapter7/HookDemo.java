package com.mm.demo.chapter7;

/**
 * jvm的钩子。当jvm关闭时，要先关闭钩子。所有可以在钩子中关闭资源
 */
public class HookDemo {
    static class MyTheard extends Thread{

        public void run() {
            System.out.println("执行线程");
        }

        @Override
        public synchronized void start() {
            super.start();
            //一个钩子
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run() {
                    System.out.println("关闭资源----");
                    MyTheard.this.realese();
                }
            });
        }

        public void realese() {
            System.out.println("释放资源");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyTheard myTheard = new MyTheard();
        myTheard.start();


        Thread.sleep(5000);
        System.out.println("主线程睡眠结束");
    }
}
