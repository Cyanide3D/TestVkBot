package com.cyanide3d.lib;

import com.cyanide3d.lib.annotations.Singleton;
import com.cyanide3d.lib.config.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {

    @Getter
    Config config;
    @Getter
    @Setter
    ObjectFactory objectFactory;
    Map<Class<?>, Object> singletons;
    Map<String, Object> beans = new HashMap<>();

    public ApplicationContext(String pcg, String propertyPath) {
        config = new Config(pcg, propertyPath);
        singletons = new HashMap<>();
    }

    public <T> T getObject(Class<?> clazz) {
        Class<?> temp = clazz;
        if (singletons.containsKey(temp)) {
            return (T) singletons.get(temp);
        }
        if (beans.containsKey(clazz.getName())) {
            return (T) beans.get(clazz.getName());
        }
        if (temp.isInterface()) {
            temp = config.getImplClass(clazz);
        }
        T implObject = objectFactory.getObject(temp);
        if (temp.isAnnotationPresent(Singleton.class)) {
            singletons.put(temp, implObject);
        }
        return implObject;
    }

    public void setBeans(String beanName, Object bean) {
        beans.put(beanName, bean);
    }

    public Map<String, Object> getBeans() {
        return beans;
    }
}
