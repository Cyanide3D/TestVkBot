package com.cyanide3d.models;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;

public class TestModel {
    @Primary
    Integer id=5;
    String name = "asdasdasdasdasdasdasd";
    String lastname = "asasasasasasasasasasas";

    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
