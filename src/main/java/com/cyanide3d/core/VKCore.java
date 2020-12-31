package com.cyanide3d.core;

import com.cyanide3d.lib.annotations.InitMethod;
import com.cyanide3d.lib.annotations.InjectObject;
import com.cyanide3d.lib.annotations.Property;
import com.cyanide3d.lib.annotations.Singleton;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

@Singleton
public class VKCore {

    @Getter
    @InjectObject
    private VkApiClient vk;
    private int ts;
    @Getter
    private GroupActor actor;
    private int maxMsgId = -1;
    @Property("group_id")
    String groupId;
    @Property
    String access_token;

    @SneakyThrows
    @InitMethod
    public void init() {
        actor = new GroupActor(Integer.parseInt(groupId), access_token);
        ts = vk.messages().getLongPollServer(actor).execute().getTs();

    }

    @SneakyThrows
    public Message getMessage() {
        try {
            MessagesGetLongPollHistoryQuery eventsQuery = vk.messages()
                    .getLongPollHistory(actor)
                    .ts(ts);
            if (maxMsgId > 0) {
                eventsQuery.maxMsgId(maxMsgId);
            }
            List<Message> messages = eventsQuery
                    .execute()
                    .getMessages().getItems();
            if (!messages.isEmpty()) {
                ts = vk.messages()
                        .getLongPollServer(actor)
                        .execute()
                        .getTs();
            }
            if (!messages.isEmpty() && !messages.get(0).isOut()) {
                int messageId = messages.get(0).getId();
                if (messageId > maxMsgId) {
                    maxMsgId = messageId;
                }
                return messages.get(0);
            }
        } catch (Exception e){
            System.out.println("Problem with get message");
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        }
        return null;
    }
}