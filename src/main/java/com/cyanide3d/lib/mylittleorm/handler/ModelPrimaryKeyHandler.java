package com.cyanide3d.lib.mylittleorm.handler;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class ModelPrimaryKeyHandler {


    public String getPrimaryKey(Object object){
        String primaryKey = "id";
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Primary.class)){
                primaryKey = field.getName();
            }
        }
        return primaryKey;
    }

    @SneakyThrows
    public int getValueOfPrimaryKey(Object object) {
        Field fieldId = object.getClass().getDeclaredField(getPrimaryKey(object));
        fieldId.setAccessible(true);
        return (int) fieldId.get(object);
    }
}
