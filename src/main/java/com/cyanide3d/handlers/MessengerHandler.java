package com.cyanide3d.handlers;

import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.managers.CommandManager;
import com.vk.api.sdk.objects.messages.Message;
import lombok.Setter;

public class MessengerHandler implements Runnable{

    @InjectObject
    CommandManager commandManager;
    @Setter
    private Message message;

    @Override
    public void run() {
        commandManager.execute(message);
    }
}