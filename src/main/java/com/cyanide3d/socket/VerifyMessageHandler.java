package com.cyanide3d.socket;

import com.cyanide3d.managers.ResendManager;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.Charset;

public class VerifyMessageHandler implements Runnable{

    String message;
    ResendManager resendManager;

    public VerifyMessageHandler(String message, ResendManager resendManager) {
        this.message = message;
        this.resendManager = resendManager;
    }

    @Override
    public void run() {
        String author = StringUtils.substringBefore(message, ":");
        String msg = StringUtils.substringAfter(message, ":");
        String result = "[From DISCORD] " + author + ": " + msg;
        resendManager.sendMessageFromDiscord(new String(result.getBytes(), Charset.forName("windows-1251")));
    }
}
