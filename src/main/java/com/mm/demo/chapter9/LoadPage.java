package com.mm.demo.chapter9;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.*;

public class LoadPage {
    ExecutorService executor = Executors.newFixedThreadPool(10);

    void onCreate() {
        Button button = new Button();
        final BackgroundTask<Integer> backgroundTask = new BackgroundTask<Integer>() {
            @Override
            protected void onComplete(Integer integer, Throwable throwable, boolean isCancel) {
                System.out.println("完成!处理结果");
                if (throwable != null) {
                    System.out.println("提示用户异常。需要重试");
                }
                if (isCancel) {
                    return;
                } else {
                    System.out.println("使用数据:" + integer + "渲染页面");
                }
            }

            @Override
            protected void cancel() {
                System.out.println("任务取消");
            }

            @Override
            protected Integer compute() {
                System.out.println("处理任务....步骤一");
                setProcess(1, 3);
                System.out.println("处理任务....步骤二");
                setProcess(2, 3);
                System.out.println("处理任务....步骤三");
                setProcess(3, 3);
                return 3;
            }

            @Override
            protected void onProcess(int current, int max) {
                System.out.println("给页面显示一下进度，当前进度" + current + ",总共需要" + max);
                if (current == max) {
                    System.out.println("给页面提示：任务完成！");
                }
            }

            @Override
            public void run() {
                compute();
            }
        };
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executor.submit(backgroundTask.computation);
            }
        });
    }
}
