package com.cyanide3d.commands;

import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;

public class Buttons extends Command{

    public Buttons() {
        this.name = "Кнопки";
    }

    @Override
    public void execute(Message message, ResendManager manager) {
        manager.keyboard("Лови ;)", message);
    }
}