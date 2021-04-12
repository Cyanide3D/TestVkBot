package com.cyanide3d.models;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;

public class TestModel {
    @Primary
    Integer id = 3;
    String name = "q";
    String lastname = "ewq";

    @Override
    public String toString() {
        return "TestModel{" +
                "name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
