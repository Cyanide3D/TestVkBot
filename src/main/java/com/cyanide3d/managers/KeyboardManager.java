package com.cyanide3d.managers;

import com.cyanide3d.lib.annotations.InitMethod;
import com.cyanide3d.lib.annotations.Property;
import com.cyanide3d.lib.annotations.Singleton;
import com.vk.api.sdk.objects.messages.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class KeyboardManager {
    @Getter
    Keyboard keyboard = new Keyboard();
    List<List<KeyboardButton>> allButtons = new ArrayList<>();
    List<KeyboardButton> line1 = new ArrayList<>();
    @Property
    String discord_link;
    @Property
    String site_link;

    @InitMethod
    private void init(){
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Дискорд").setType(TemplateActionTypeNames.OPEN_LINK).setLink(discord_link)));
        line1.add(new KeyboardButton().setAction(new KeyboardButtonAction().setLabel("Сайт").setType(TemplateActionTypeNames.OPEN_LINK).setLink(site_link)));
        allButtons.add(line1);
        keyboard.setButtons(allButtons);
    }
}