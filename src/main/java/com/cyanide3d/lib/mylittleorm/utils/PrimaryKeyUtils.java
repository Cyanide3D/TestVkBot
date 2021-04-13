package com.cyanide3d.lib.mylittleorm.utils;

import com.cyanide3d.lib.mylittleorm.annotations.Primary;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class PrimaryKeyUtils {


    private PrimaryKeyUtils() {
    }

    public static String getPrimaryKey(Class<?> clazz){
        String primaryKey = "id";
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Primary.class)){
                primaryKey = field.getName();
            }
        }
        return primaryKey;
    }

    public static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Primary.class);
    }

    @SneakyThrows
    public static int getValueOfPrimaryKey(Object object) {
        try {
            Field fieldId = object.getClass().getDeclaredField(getPrimaryKey(object.getClass()));
            fieldId.setAccessible(true);
            return (int) fieldId.get(object);
        } catch (Exception e) {
            return 0;
        }
    }
}
