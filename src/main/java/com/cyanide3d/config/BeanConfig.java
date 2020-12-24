package com.cyanide3d.config;

import com.cyanide3d.lib.annotations.Bean;
import com.cyanide3d.lib.annotations.Config;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;

@Config
public class BeanConfig {

    @Bean
    public VkApiClient vk() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        return new VkApiClient(transportClient);
    }
}