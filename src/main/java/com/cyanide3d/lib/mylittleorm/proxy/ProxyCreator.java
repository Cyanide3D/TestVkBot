package com.cyanide3d.lib.mylittleorm.proxy;

import com.cyanide3d.lib.mylittleorm.repo.Repository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;

public class ProxyCreator {

    private final Set<Class<? extends Repository>> subTypesOf;
    private final MethodParser methodParser = new NewMethodParserImpl();

    public ProxyCreator() {
        Reflections reflections = new Reflections("com");
        subTypesOf = reflections.getSubTypesOf(Repository.class);
    }

    public <T> T createProxy() {
        Class<? extends Repository> repo = subTypesOf.iterator().next();
        return (T) Proxy.newProxyInstance(
                repo.getClassLoader(),
                List.of(repo).toArray(Class[]::new),
                (proxy, method, args) -> methodParser.parse(method, args, getEntityClass(repo))
        );
    }

    @SneakyThrows
    private Class<?> getEntityClass(Class<? extends Repository> repo) {
        String temp = StringUtils.substringAfter(repo.getGenericInterfaces()[0].getTypeName(), ", ");
        return Class.forName(temp.substring(0, temp.length() - 1));
    }
}
