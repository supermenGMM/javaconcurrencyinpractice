package com.mm.demo.chapter5;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 提前加载稍后需要的数据
 * FutureTask也可以做闭锁。
 */
public class PreLoader {
    private final FutureTask futureTask = new FutureTask(
            new Callable<ProductInfo>() {
                @Override
                public ProductInfo call() throws Exception {
                    return caculateProduct();//这里抛出的异常将会在get时抛出
                }

                private ProductInfo caculateProduct() throws DataLoadException {
                    ProductInfo productInfo = new ProductInfo();
                    productInfo = null;
                    productInfo.name = "香蕉";
                    productInfo.num = 2;
                    if (productInfo == null) {
                        throw new DataLoadException();
                    }
                    return productInfo;
                }
            });

    private class ProductInfo {
        private int num;
        private String name;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "ProductInfo{" +
                    "num=" + num +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    private final Thread thread = new Thread(futureTask);
    public void start() {
        thread.start();
    }
    public ProductInfo get() throws ExecutionException, InterruptedException {
        try {
            ProductInfo productInfo = (ProductInfo) futureTask.get();
            System.out.println(productInfo);
            return productInfo;
        } catch (ExecutionException e) {
            System.out.println(e);
            Throwable cause = e.getCause();
            if (cause instanceof DataLoadException) {
                throw (DataLoadException)cause;
            }else{
                System.out.println("未知异常");
                throw launderThrowable(cause);
            }
        }
    }

    private RuntimeException launderThrowable(Throwable cause) {
        System.out.println("未知异常处理/");
        return (RuntimeException) cause;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //先启动线程。
        PreLoader preLoader = new PreLoader();
        preLoader.start();

        ProductInfo productInfo = preLoader.get();

    }
}
