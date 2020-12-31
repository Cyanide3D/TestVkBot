package com.cyanide3d.core;

import com.cyanide3d.dao.ObjectDao;
import com.cyanide3d.handlers.MessengerHandler;
import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.lib.annotations.Property;
import com.cyanide3d.socket.Server;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VKServer {
    @InjectObject
    private VKCore vkCore;
    @InjectObject
    private MessengerHandler messengerHandler;
    @Property("discord_socket_port")
    private String port;
    @InjectObject
    private ObjectDao dao;
    @InjectObject
    private Server server;

    @SneakyThrows
    public void start() {
        System.out.println("Running discord socket server...");
        server.setPort(port).start();
        System.out.println("Discord socket server launched on port " + port);
        System.out.println("Running VK bot...");
        while (true) {
            try {
                Thread.sleep(400);
                Message message = vkCore.getMessage();
                if (message != null) {
                    ExecutorService executor = Executors.newCachedThreadPool();
                    messengerHandler.setMessage(message);
                    executor.execute(messengerHandler);
                }
            } catch (Exception e) {
                System.err.println("Some error with connection!\nTry connection in 5 second...");
                Thread.sleep(4600);
            }
        }
    }
}