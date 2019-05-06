package com.mm.demo;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 基于委托模式的机动车追踪器实现
 */
public class DelegatingVehicleTracker {
    /**
     * 不可变
     */
    private static class Point {
        private final int x;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private final int y;

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    private final ConcurrentHashMap<String, Point> tracker ;
    private final Map<String ,Point> unmodifiableMap ;

    public DelegatingVehicleTracker(Map<String ,Point> map) {
        this.tracker = new ConcurrentHashMap<String, Point>(map);
        unmodifiableMap = Collections.unmodifiableMap(tracker);
    }

    public  synchronized Map<String,Point> getLocations() {
        return unmodifiableMap;
    }

    public synchronized  Point getLocation(String key) {
        return tracker.get(key);
    }

    public  synchronized void setTracker(String key, int x, int y) throws IllegalAccessException {
        Point point = tracker.get(key);
//        if (point == null) {
//            throw new IllegalArgumentException("没有可用的Key");
//        }
        Point ponit = new Point(x,y);
        tracker.put(key, ponit);
    }



    public static void main(String[] args) throws IllegalAccessException {
        Map<String, Point> map = new HashMap<String, Point>();
        map.put("1", new Point(11,22));
        DelegatingVehicleTracker tracker = new DelegatingVehicleTracker(map);
        Map<String, Point> copyLocations = tracker.getLocations();
        System.out.println(copyLocations.get("1"));
//看一下是拷贝的是否随着原map改变而改变
        tracker.setTracker("1", 33, 44);

        System.out.println(copyLocations.get("1"));


        tracker.setTracker("2", 33, 44);

        System.out.println(copyLocations.get("2"));

    }

}

