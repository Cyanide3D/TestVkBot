package com.cyanide3d.lib.mylittleorm.proxy.configurators;

import java.lang.reflect.Method;

public interface MethodConfigurer {
    Object configure(Method method, Object[] args, Class<?> clazz);
}
