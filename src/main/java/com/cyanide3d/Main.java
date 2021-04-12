package com.cyanide3d;


import com.cyanide3d.core.VKServer;
import com.cyanide3d.lib.Application;
import com.cyanide3d.lib.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("com", "settings.properties");
        VKServer vkServer = context.getObject(VKServer.class);
        vkServer.start();
    }
}