package com.mm.demo;

import java.util.*;

/**
 * 基于监视器模式的机动车追踪器实现
 */
public class MonitorVehicleTracker {
    private static class MutablePoint {
        private int x;
        private int y;

        public MutablePoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public MutablePoint() {
        }

        @Override
        public String toString() {
            return "MutablePoint{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    private final Map<String, MutablePoint> tracker ;

    public MonitorVehicleTracker(Map<String,MutablePoint> tracker) {
        this.tracker = deepCopy(tracker);
    }

    public  synchronized Map<String,MutablePoint> getLocations() {
        return deepCopy(this.tracker);
    }

    public synchronized MutablePoint getLocation(String key) {
        return tracker.get(key);
    }

    public  synchronized void setTracker(String key, int x, int y) throws IllegalAccessException {
        MutablePoint mutablePoint = tracker.get(key);
        if (mutablePoint == null) {
            throw new IllegalArgumentException("没有可用的Key");
        }
        MutablePoint ponit = new MutablePoint();
        ponit.x = x;
        ponit.y = y;
        tracker.put(key, ponit);
    }

    private  Map<String,MutablePoint> deepCopy(Map<String,MutablePoint> tracker) {
        Map<String,MutablePoint> copy = new HashMap();
        Set<String> keySet = tracker.keySet();
        for (String key : keySet) {
            copy.put(key, tracker.get(key));
        }
        return Collections.unmodifiableMap(copy);//既然这里进行了unmodifiableMap拷贝，上面为什么还会有重建个集合？没必要啊。
    }

    public static void main(String[] args){
        Map<String, MutablePoint> locations = new HashMap<String, MutablePoint>();
        locations.put("1",new MutablePoint(1,2));
        //看一下deepCopy的value是拿的引用还是复制的实体。（答案：引用）
        MonitorVehicleTracker monitorVehicleTracker = new MonitorVehicleTracker(locations);
        Map<String, MutablePoint> deepCopyLocations = monitorVehicleTracker.getLocations();
        System.out.println(deepCopyLocations.get("1"));

        MutablePoint point = monitorVehicleTracker.getLocation("1");
        point.x = 4;
        System.out.println(deepCopyLocations.get("1"));

        deepCopyLocations.put("1", new MutablePoint(4, 5));//这里会报错。因为deepcopy的不允许修改。

    }

}

