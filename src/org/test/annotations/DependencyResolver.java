package org.test.annotations;

import org.test.di.Context;

import java.lang.reflect.Field;

public class DependencyResolver {

    public void initDependencies(Object bean) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Dependency.class)) {
                try {
                    field.set(bean, Context.getInjectable(field.getType().getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
