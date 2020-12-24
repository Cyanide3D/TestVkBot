package com.cyanide3d.managers;

import com.cyanide3d.commands.Command;
import com.cyanide3d.handlers.CommandDeterminantHandler;
import com.cyanide3d.lib.annotations.Commands;
import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.lib.annotations.Singleton;
import com.vk.api.sdk.objects.messages.Message;

import java.util.HashSet;

@Singleton
public class CommandManager {

    @InjectObject
    ResendManager resendManager;
    @InjectObject
    CommandDeterminantHandler commandDeterminantHandler;
    @Commands
    private HashSet<Command> commandList;

    public void execute(Message message){
        commandDeterminantHandler.getCommand(commandList, message).execute(message, resendManager);
    }
}