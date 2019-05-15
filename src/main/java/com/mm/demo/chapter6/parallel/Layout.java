package com.mm.demo.chapter6.parallel;

import com.sun.scenario.effect.ImageData;

import java.util.ArrayList;
import java.util.List;

public class Layout {
    public void renderingText(List<String> textList) {
        System.out.println("渲染文本");
    }
    public void renderingImage(List<ImageData> imageDatas) {
        System.out.println("渲染图片");
    }

    static List<ImageInfo> scanFroImageInfo(){
        System.out.println("分析页面加载图片");
        ArrayList<ImageInfo> imageInfos = new ArrayList<ImageInfo>();
        imageInfos.add(new ImageInfo());
        imageInfos.add(new ImageInfo());
        imageInfos.add(new ImageInfo());
        return imageInfos;
    }


    static class ImageInfo{
        public ImageData download() {
            System.out.println("下载图片");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
