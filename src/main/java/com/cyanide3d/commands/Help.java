package com.cyanide3d.commands;

import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;

public class Help extends Command {

    public Help() {
        this.name = "help";
    }

    @Override
    @SneakyThrows
    public void execute(Message message, ResendManager manager) {
        manager.sendGroupMessage("Bot have'nt commands, just have fan :)", message);
    }
}