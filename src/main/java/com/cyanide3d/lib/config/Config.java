package com.cyanide3d.lib.config;

import com.cyanide3d.lib.configurators.Configurator;
import lombok.Getter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Config {
    @Getter
    Reflections reflections;
    Properties properties;

    public Config(String pcg, String propertyPath) {
        reflections = new Reflections(pcg);
        properties = new Properties();
        loadProperty(propertyPath);
    }
    @SneakyThrows
    private void loadProperty(String propertyPath){
        try {
            properties.load(new FileReader(propertyPath));
        } catch (IOException e) {
            properties.store(new FileWriter(propertyPath), "Automatically generated property...");
        }
    }

    public Class<?> getImplClass(Class<?> etc) {
        Set<Class<?>> subTypesOf = reflections.getSubTypesOf((Class<Object>) etc);
        if (subTypesOf.size() > 1) {
            throw new RuntimeException("Больше одной реализации интерфейса");
        }
        return subTypesOf.iterator().next();
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

    public Set<Configurator> getConfigurators() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Set<Configurator> cfg = new HashSet<>();
        Set<Class<? extends Configurator>> subTypesOf = reflections.getSubTypesOf(Configurator.class);
        for (Class<? extends Configurator> aClass : subTypesOf) {
            cfg.add(aClass.getDeclaredConstructor().newInstance());
        }
        return cfg;
    }

    public Set<Class<?>> getBeanConfigs() {
        return reflections.getTypesAnnotatedWith(com.cyanide3d.lib.annotations.Config.class);
    }
}