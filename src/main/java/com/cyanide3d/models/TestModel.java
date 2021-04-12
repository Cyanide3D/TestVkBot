package com.cyanide3d.models;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;

public class TestModel {
    @Primary
    Integer id = 1;
    String name = "123";
    String lastname = "321";

    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
