package com.mm.demo.chapter6;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final ExecutorService EXECUTORS =
            Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            final Socket socket = serverSocket.accept();
            EXECUTORS.execute(new Runnable() {
                @Override
                public void run() {
                    handleRequest(socket);
                }
            });
        }
    }

    private static void handleRequest(Socket socket) {
        System.out.println("处理请求...");
    }

}
