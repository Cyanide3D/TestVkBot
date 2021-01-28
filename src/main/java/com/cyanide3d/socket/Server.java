package com.cyanide3d.socket;

import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.managers.ResendManager;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{
    @InjectObject
    ResendManager resendManager;
    int port;

    @Override
    @SneakyThrows
    public void run() {
        ServerSocket serverSocket = new ServerSocket(port);
        ExecutorService executor = Executors.newCachedThreadPool();
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = buffer.readLine();
                if (message != null){
                    executor.execute(new RequestMessageAnalyzer(message, resendManager));
                }
                buffer.close();
            } catch (Exception e) {
                System.out.println("Thread interrupt, try again...");
            }
        }
    }

    public Server setPort(String port) {
        this.port = Integer.parseInt(port);
        return this;
    }
}
