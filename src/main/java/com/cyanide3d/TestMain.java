package com.cyanide3d;


import com.cyanide3d.lib.mylittleorm.proxy.ProxyCreator;
import lombok.SneakyThrows;

public class TestMain {
    @SneakyThrows
    public static void main(String[] args) {
        TestRepo proxy = new ProxyCreator().createProxy();
        System.out.println(proxy.findByLastname("321"));
    }
}
