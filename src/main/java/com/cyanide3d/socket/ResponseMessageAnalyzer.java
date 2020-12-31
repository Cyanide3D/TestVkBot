package com.cyanide3d.socket;

import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.MessageAttachment;
import lombok.SneakyThrows;

public class ResponseMessageAnalyzer {
    Message message;

    public ResponseMessageAnalyzer(Message message) {
        this.message = message;
    }

    @SneakyThrows
    public String analyze() {
        StringBuilder result = new StringBuilder();
        PreparationResponseMessageHandler preparationResponseMessageHandler = new PreparationResponseMessageHandler(message);
        for (MessageAttachment attachment : message.getAttachments()) {
            String type = attachment.getType().getValue();
            if (type.equals("photo")) {
                result.append(preparationResponseMessageHandler.photo(attachment));
            } else if (type.equals("wall")) {
                result.append(preparationResponseMessageHandler.wall());
            } else if (type.equals("video")) {
                result.append(preparationResponseMessageHandler.video(attachment));
            } else if (type.equals("doc")) {
                result.append(preparationResponseMessageHandler.doc(attachment));
            } else if (type.equals("link")) {
                result.append(preparationResponseMessageHandler.link(attachment));
            } else if (type.equals("audio_message")) {
                result.append(preparationResponseMessageHandler.audioMessage(attachment));
            } else if (type.equals("audio")) {
                result.append(preparationResponseMessageHandler.audio(attachment));
            } else {
                result.append("'Вложение'");
            }
            result.append("\n");
        }
        return result.toString();
    }
}