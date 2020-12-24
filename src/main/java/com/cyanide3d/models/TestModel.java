package com.cyanide3d.models;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;
import lombok.Data;

@Data
public class TestModel {
    String name = "geYshA";
    @Primary
    int primary_id;
    String lastname = "sssssss";
}
