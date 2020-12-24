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
        manager.getVkCore().getVk().messages()
                .send(manager.getVkCore().getActor()).message("asdad").peerId(2000000002)
                .randomId(new Random().nextInt(10000)).execute();
    }
}
