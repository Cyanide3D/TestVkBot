package com.cyanide3d;


import com.cyanide3d.lib.mylittleorm.proxy.ProxyCreator;
import com.cyanide3d.models.TestModel;
import lombok.SneakyThrows;

public class TestMain {
    @SneakyThrows
    public static void main(String[] args) {
        TestRepo proxy = new ProxyCreator().createProxy();
        proxy.findByLastname("321");
    }
}
