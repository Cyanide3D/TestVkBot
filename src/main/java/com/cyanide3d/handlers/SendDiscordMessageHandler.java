package com.cyanide3d.handlers;

import com.cyanide3d.lib.annotations.Property;
import lombok.SneakyThrows;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class SendDiscordMessageHandler {

    @Property("discord_port")
    private String port;

    @SneakyThrows
    public void send(String message) {
        try (Socket socket = new Socket("188.134.66.216", Integer.parseInt(port))) {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Charset.forName("windows-1251")));
            bufferedWriter.write(message + "\r");
            bufferedWriter.close();
        }
    }
}
