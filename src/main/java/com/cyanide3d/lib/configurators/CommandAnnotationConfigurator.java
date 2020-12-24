package com.cyanide3d.lib.configurators;

import com.cyanide3d.commands.Command;
import com.cyanide3d.lib.ApplicationContext;
import com.cyanide3d.lib.annotations.Commands;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class CommandAnnotationConfigurator implements Configurator {
    @SneakyThrows
    @Override
    public void configure(Object obj, ApplicationContext applicationContext) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Commands.class)) {
                field.setAccessible(true);
                if (field.get(obj) == null) {
                    Set<Class<? extends Command>> subTypesOf = applicationContext.getConfig().getReflections().getSubTypesOf(Command.class);
                    HashSet<Command> commands = new HashSet<>();
                    for (Class<? extends Command> aClass : subTypesOf) {
                        commands.add(aClass.getDeclaredConstructor().newInstance());
                    }
                    field.set(obj, commands);
                }
            }
        }
    }
}
