package com.mm.demo.chapter6.parallel;

import com.sun.scenario.effect.ImageData;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用future等待图像下载
 */
public class FutureRender {
    public void render(final Layout layout) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<List<ImageData>> future = executorService.submit(new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<Layout.ImageInfo> imageInfos = Layout.scanFroImageInfo();
                List<ImageData> list = new ArrayList<ImageData>();
                for (Layout.ImageInfo imageInfo :
                        imageInfos) {
                    list.add(imageInfo.download());
                }
                return list;
            }
        });
        Thread.sleep(2000);
        try {
            layout.renderingText(new ArrayList<String>());
            layout.renderingImage(future.get(2,TimeUnit.SECONDS));

        } catch (InterruptedException e) {
            e.printStackTrace();
            //被中断就取消
            Thread.currentThread().interrupt();//被中断就取消
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.out.println("遇到未知异常处理----");
        } catch (TimeoutException e) {
            e.printStackTrace();
            System.out.println("超时");
            layout.renderingImage(null);//默认值
        }
    }

    @Test
    public void test() throws InterruptedException {
        long start = System.currentTimeMillis();
        render(new Layout());
        System.out.println("执行时间:"+(System.currentTimeMillis()-start));
    }
}
