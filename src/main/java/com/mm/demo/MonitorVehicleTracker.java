package com.mm.demo;

import java.util.*;

/**
 * 基于监视器模式的机动车追踪器实现
 */
public class MonitorVehicleTracker {
    private static class Point {
        private int x;
        private int y;
    }
    private final Map<String, Point> tracker ;

    public MonitorVehicleTracker(Map tracker) {
        this.tracker = deepCopy(tracker);
    }

    public  synchronized Map<String,Point> getLocations() {
        return deepCopy(this.tracker);
    }

    public synchronized  Point getLocation(String key) {
        return tracker.get(key);
    }

    public  synchronized void setTracker(String key, int x, int y) throws IllegalAccessException {
        Point point = tracker.get(key);
        if (point == null) {
            throw new IllegalArgumentException("没有可用的Key");
        }
        Point ponit = new Point();
        ponit.x = x;
        ponit.y = y;
        tracker.put(key, ponit);
    }

    private  Map<String,Point> deepCopy(Map<String,Point> tracker) {
        Map<String,Point> copy = new HashMap();
        Set<String> keySet = tracker.keySet();
        for (String key : keySet) {
            copy.put(key, tracker.get(key));
        }
        return Collections.unmodifiableMap(copy);
    }

}

