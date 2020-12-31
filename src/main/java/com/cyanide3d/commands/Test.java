package com.cyanide3d.commands;

import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;

import java.util.Random;

public class Test extends Command{

    public Test() {
        this.name = "test";
    }

    @Override
    @SneakyThrows
    public void execute(Message message, ResendManager manager) {
        System.out.println(message.getPeerId());
    }
}
