package com.mm.test;

/**
 * wait notify notifyall
 */
public class WaitTest {

    Integer num = 0;
    int coupSize=100;//容量

        public synchronized   void product() {
                if (num >= coupSize) {
                    try {
                        System.out.println("等待消费");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                num++;
                System.out.println("生产一个产品，目前数量为" + num);
                //通知消费者可以买了
                notify();

        }

        public synchronized void customer() {
            if (num <=0) {
                try {
                    System.out.println("等待生产");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            num--;
            System.out.println("消费一个产品，目前数量为" + num);
            //通知消费者可以买了
            notify();
        }

    public static void main(String[] args) throws InterruptedException {
        final WaitTest waitTest = new WaitTest();
//
//        new Thread() {
//            public void run() {
//                for (int i=0;i<1000;i++) {
//                    waitTest.product();
//
//                }
//            }
//        }.start();
//
//        new Thread() {
//            public void run() {
//                for (int i=0;i<1000;i++) {
//                    waitTest.customer();
//
//                }
//            }
//        }.start();

//        new Thread(){
//            public void run() {
//                waitTest.product();
//
//            }
//        }.start();
        synchronized (waitTest) {
                waitTest.wait(1000);
                System.out.println("等待完毕");
        }



    }


}
