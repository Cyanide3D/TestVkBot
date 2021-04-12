package com.cyanide3d.lib.mylittleorm.proxy;

import java.lang.reflect.Method;

public interface MethodParser {

    Object parse(Method method, Object[] args, Class<?> clazz);

}
