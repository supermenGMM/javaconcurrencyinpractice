package com.mm.demo.chapter6;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutionWebServer {
    private static final int NTHREADS = 10;
    private static final ExecutorService EXECUTORS =
            Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        final ServerSocket serverSocket = new ServerSocket(801);
        int i = 0;
        while (true) {
            System.out.println("循环"+i);
            final Socket socket = serverSocket.accept();
            System.out.println("开始"+i);
            EXECUTORS.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        handleRequest(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            System.out.println("结束"+i++);
        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        inputStream.read(bytes);
        System.out.println(new String(bytes,"utf-8")+"====");


        System.out.println("处理请求..."+Thread.currentThread().getName());
    }

}
