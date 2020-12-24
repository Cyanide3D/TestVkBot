package com.cyanide3d.lib;

import com.cyanide3d.lib.annotations.InitMethod;
import com.cyanide3d.lib.configurators.Configurator;
import lombok.SneakyThrows;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ObjectFactory {
    Set<Configurator> configurators;
    Set<Class<?>> beanConfigs;
    ApplicationContext context;

    @SneakyThrows
    public ObjectFactory(ApplicationContext context) {
        this.configurators = context.getConfig().getConfigurators();
        this.beanConfigs = context.getConfig().getBeanConfigs();
        this.context = context;
        fillBeans();
    }




    @SneakyThrows
    public <T> T getObject(Class<?> clazz) {
        T implObject = (T) clazz.getDeclaredConstructor().newInstance();
        configurators.forEach(conf -> {
            conf.configure(implObject, context);
        });
        invokeInitMethod(clazz, implObject);
        return implObject;
    }





    public void fillBeans(){
        for (Class<?> beanConfig : beanConfigs) {
            getObject(beanConfig);
        }
    }
    private <T> void invokeInitMethod(Class<?> clazz, T implObject) throws IllegalAccessException, InvocationTargetException {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(InitMethod.class)) {
                method.invoke(implObject);
            }
        }
    }
}
