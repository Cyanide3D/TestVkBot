package com.cyanide3d.lib;

import lombok.SneakyThrows;

public class Application {
    @SneakyThrows
    public static ApplicationContext run(String packageToScan, String propertyPath) {
        ApplicationContext applicationContext = new ApplicationContext(packageToScan, propertyPath);
        ObjectFactory objectFactory = new ObjectFactory(applicationContext);
        applicationContext.setObjectFactory(objectFactory);
        return applicationContext;
    }
}
