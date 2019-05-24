package com.mm.demo.chapter9;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class GUIExcetors extends AbstractExecutorService {
    private GUIExcetors() {
    }

    private static GUIExcetors instance = new GUIExcetors();

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        if (SwingUtilities.isEventDispatchThread()) {//是事件线程
            command.run();
        } else {
            SwingUtilities.invokeLater(command);
        }
    }

    public static GUIExcetors instance() {
        return instance;
    }
}
