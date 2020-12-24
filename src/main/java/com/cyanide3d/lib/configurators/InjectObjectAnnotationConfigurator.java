package com.cyanide3d.lib.configurators;

import com.cyanide3d.lib.ApplicationContext;
import com.cyanide3d.lib.annotations.InjectObject;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectObjectAnnotationConfigurator implements Configurator {
    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext applicationContext) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                InjectObject annotation = field.getAnnotation(InjectObject.class);
                field.setAccessible(true);
                if (annotation.value().equals("")) {
                    field.set(obj, applicationContext.getObject(field.getType()));
                } else {
                    field.set(obj, applicationContext.getBeans().get(annotation.value()));
                }
            }
        }
    }
}
