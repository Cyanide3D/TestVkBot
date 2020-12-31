package com.cyanide3d.commands;

import com.cyanide3d.socket.ResponseMessageAnalyzer;
import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import lombok.SneakyThrows;

public class Unknown extends Command {

    public Unknown() {
        this.name = "unknown";
    }

    @Override
    @SneakyThrows
    public void execute(Message message, ResendManager manager) {
        GetResponse execute = manager.getVkCore().getVk().users().get(manager.getVkCore().getActor())
                .userIds(String.valueOf(message.getFromId())).execute().get(0);
        if (message.getAttachments() != null) {
            args = args + "\n" + new ResponseMessageAnalyzer(message).analyze();
        }
        String body = new StringBuilder()
                .append(execute.getFirstName())
                .append("-----")
                .append(execute.getLastName())
                .append("-----")
                .append(args)
                .toString().replace("\n", "-----");
        manager.sendDiscordMessage(body);
    }
}