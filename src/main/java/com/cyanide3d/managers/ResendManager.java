package com.cyanide3d.managers;

import com.cyanide3d.core.VKCore;
import com.cyanide3d.handlers.SendDiscordMessageHandler;
import com.cyanide3d.lib.annotations.InjectObject;
import com.vk.api.sdk.objects.messages.Message;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Random;

public class ResendManager {
    @InjectObject
    @Getter
    private VKCore vkCore;
    @InjectObject
    KeyboardManager keyboardManager;
    @InjectObject
    SendDiscordMessageHandler sendDiscordMessageHandler;

    @SneakyThrows
    public void sendGroupMessage(String msg, Message message) {
        vkCore.getVk().messages().send(vkCore.getActor()).message(msg).peerId(message.getPeerId()).randomId(new Random().nextInt(10000)).execute();
    }
    @SneakyThrows
    public void sendPrivateMessage(int userId, String msg){
        vkCore.getVk().messages().send(vkCore.getActor()).message(msg).userId(userId).randomId(new Random().nextInt(10000)).execute();
    }
    @SneakyThrows
    public void keyboard(String msg, Message message) {
        vkCore.getVk().messages().send(vkCore.getActor()).message(msg).peerId(message.getPeerId()).randomId(new Random().nextInt(10000)).keyboard(keyboardManager.getKeyboard()).execute();
    }
    public void sendDiscordMessage(String msg){
        sendDiscordMessageHandler.send(msg);
    }
    @SneakyThrows
    public void sendMessageFromDiscord(String msg){
        vkCore.getVk().messages().send(vkCore.getActor()).message(msg).peerId(2000000001).randomId(new Random().nextInt(10000)).execute();
    }
}