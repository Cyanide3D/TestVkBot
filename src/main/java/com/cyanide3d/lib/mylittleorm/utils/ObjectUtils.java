package com.cyanide3d.lib.mylittleorm.utils;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectUtils {

    @SneakyThrows
    public static List<Object> valueExtract(Object object) {
        List<Object> values = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            values.add(field.get(object));
        }

        return values;
    }

    @SneakyThrows
    //TODO
    public static List<Object> valueExtractWithoutPrimary(Object object) {
        List<Object> values = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Primary.class)) {
                field.setAccessible(true);
                values.add(field.get(object));
            }
        }

        return values;
    }

}
