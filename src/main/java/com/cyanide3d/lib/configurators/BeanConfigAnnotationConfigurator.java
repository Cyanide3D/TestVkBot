package com.cyanide3d.lib.configurators;

import com.cyanide3d.lib.ApplicationContext;
import com.cyanide3d.lib.annotations.Bean;
import com.cyanide3d.lib.annotations.Config;
import lombok.SneakyThrows;

import java.lang.reflect.Method;

public class BeanConfigAnnotationConfigurator implements Configurator {
    @Override
    @SneakyThrows
    public void configure(Object obj, ApplicationContext applicationContext) {
        Class<?> clazz = obj.getClass();
        if (clazz.isAnnotationPresent(Config.class)) {
            String beanName;
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Bean.class)) {
                    Bean annotation = method.getAnnotation(Bean.class);
                    Object bean = method.invoke(obj);
                    beanName = bean.getClass().getName();
                    if (!annotation.value().equals("")) {
                        beanName = annotation.value();
                    }
                    applicationContext.setBeans(beanName, bean);
                }
            }
        }
    }
}
