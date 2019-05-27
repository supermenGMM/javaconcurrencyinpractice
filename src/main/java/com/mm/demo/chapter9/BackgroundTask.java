package com.mm.demo.chapter9;

import java.util.concurrent.*;

public abstract class BackgroundTask<V> implements Runnable{
    protected  final FutureTask computation = new Computation();
    public BackgroundTask() {
    }

    private class Computation extends FutureTask<V>{

        public Computation() {
            super(new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return compute();
                }
            });
        }

        @Override
        protected void done() {
            super.done();
            System.out.println("任务已完成");
            //任务完成回调用这个方法.完成后需要将数据更新到试图
            GUIExcetors.instance().execute(new Runnable() {
                @Override
                public void run() {

                    boolean isCancel = false;
                    Throwable throwable =null;
                    V v = null;
                    try {
                        v = get();
                        System.out.println("任务结果:" + v);
                    } catch (InterruptedException e) {
                        BackgroundTask.this.cancel();
                        isCancel = true;
                    } catch (CancellationException e) {
                        isCancel = true;
                    } catch (ExecutionException e) {
                        throwable = e.getCause();
                    }
                    System.out.println("是否取消isCancelled()："+isCancelled());
                    onComplete(v,throwable,isCancel);//由客户端实现
                }
            });

        }
    }

    protected abstract void onComplete(V v,Throwable throwable,boolean isCancel);

    protected abstract void cancel();

    protected abstract V compute();//执行任务

    protected void setProcess(final int current, final int max) {
        GUIExcetors.instance().execute(new Runnable() {
            @Override
            public void run() {
                onProcess(current,max);
            }
        });
    }

    protected abstract void onProcess(int current, int max);
}
