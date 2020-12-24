package com.cyanide3d.handlers;

import com.cyanide3d.commands.Command;
import com.cyanide3d.commands.Unknown;
import com.vk.api.sdk.objects.messages.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;


public class CommandDeterminantHandler {


    public Command getCommand(Collection<Command> commands, Message message) {
        String body = message.getText();
        for (Command command : commands) {
            String[] words = body.split(" ");
            if (command.name.equals(words[0])) {
                if (words.length > 1){
                    command.setArgs(StringUtils.substringAfter(body, words[0] + " "));
                }
                return command;
            }
        }
        Unknown unknown = new Unknown();
        unknown.setArgs(body);
        return unknown;
    }
}