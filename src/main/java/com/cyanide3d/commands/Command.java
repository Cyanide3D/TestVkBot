package com.cyanide3d.commands;

import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;
import lombok.Setter;

public abstract class Command {

    public String name;
    @Setter
    public String args = "";

    public abstract void execute(Message message, ResendManager manager);


    @Override
    public String toString() {
        return String.format("name: %s",this.name);
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Command){
            if (name.equals(((Command) obj).name)){
                return true;
            }
        }
        return false;
    }

}