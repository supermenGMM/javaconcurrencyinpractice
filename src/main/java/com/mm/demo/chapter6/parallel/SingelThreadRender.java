package com.mm.demo.chapter6.parallel;


import com.sun.scenario.effect.ImageData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.mm.demo.chapter6.parallel.Layout.scanFroImageInfo;

/**
 * 找出可利用的并行性
 * 页面渲染：串行
 */
public class SingelThreadRender {
    public void renderPage(Layout layout){
        List<String> texts = new ArrayList<String>();
        layout.renderingText(texts);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<ImageData> images = new ArrayList<ImageData>();
        List<Layout.ImageInfo> imageInfos = scanFroImageInfo();
        for (Layout.ImageInfo imageInfo : imageInfos) {
            images.add(imageInfo.download());
        }
        layout.renderingImage(images);
    }

    @Test
    public void test() {
        long start = System.currentTimeMillis();
        renderPage(new Layout());
        System.out.println("执行时间:"+(System.currentTimeMillis()-start));
    }

}
