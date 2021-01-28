package com.cyanide3d.commands;

import com.cyanide3d.managers.ResendManager;
import com.vk.api.sdk.objects.messages.Message;
import lombok.SneakyThrows;
import org.apache.logging.log4j.core.util.JsonUtils;

public class Test extends Command {

    public Test() {
        this.name = "test";
    }


    @Override
    @SneakyThrows
    public void execute(Message message, ResendManager manager) {
//        try {
//            manager.sendPrivateMessage(5683740, "hello, если видишь это сообщение - скопируй его и отправь мне в ЛС");
//            manager.getVkCore().getVk().messages().delete(manager.getVkCore().getActor()).messageIds(message.getRandomId()).deleteForAll(true).groupId(manager.getVkCore().getActor().getGroupId()).execute();
//        } catch (Exception e){
//            manager.sendGroupMessage("Что то пошло не так", message);
//        }
        System.out.println(message.getPeerId());
    }
}
