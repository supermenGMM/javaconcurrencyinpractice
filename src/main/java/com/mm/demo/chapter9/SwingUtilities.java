package com.mm.demo.chapter9;

import java.util.concurrent.*;

public class SwingUtilities{
    private static final ExecutorService executor =
            Executors.newSingleThreadExecutor(new SwingThreadFactory());
    private static volatile Thread swingThread;

    public static class SwingThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            swingThread = new Thread(r);
            return swingThread;
        }
    }

    public static boolean isEventDispatchThread() {
        return Thread.currentThread() == swingThread;
    }

    public static void invokeLater(Runnable runnable) {
        executor.execute(runnable);
    }

    public static Object invoketAndWait(Runnable task) {
        Future<?> submit = executor.submit(task);
        try {
            return submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
