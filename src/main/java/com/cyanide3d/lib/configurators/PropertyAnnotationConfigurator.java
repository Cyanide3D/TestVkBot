package com.cyanide3d.lib.configurators;

import com.cyanide3d.lib.ApplicationContext;
import com.cyanide3d.lib.annotations.Property;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class PropertyAnnotationConfigurator implements Configurator{
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext applicationContext) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Property.class)){
                Property annotation = field.getAnnotation(Property.class);
                String propertyName;
                if (annotation.value().isEmpty()){
                    propertyName = field.getName();
                } else {
                    propertyName = annotation.value();
                }
                field.setAccessible(true);
                field.set(obj, applicationContext.getConfig().getProperty(propertyName));
            }
        }
    }
}
