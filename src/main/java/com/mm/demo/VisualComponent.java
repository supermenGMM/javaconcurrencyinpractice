package com.mm.demo;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 独立的状态变量
 *
 * 各个状态之间不存在耦合关系，
 */
public class VisualComponent {
    private final List keyListeners = new CopyOnWriteArrayList();
    private final List mouseListeners = new CopyOnWriteArrayList();

    public void addKeyListener(Object object) {
        keyListeners.add(object);
    }
    public void removeKeyListener(Object object) {
        keyListeners.remove(object);
    }
    public void addMouseListener(Object object) {
        keyListeners.add(object);
    }
    public void removeMouseListener(Object object) {
        keyListeners.remove(object);
    }
}
