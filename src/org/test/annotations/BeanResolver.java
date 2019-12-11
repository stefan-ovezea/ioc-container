package org.test.annotations;

import org.test.base.BaseResolver;
import org.test.di.Context;
import org.test.exceptions.ComponentInitializationException;

public class BeanResolver implements BaseResolver {

    private Class<Bean> annotatedClass = Bean.class;

    public void initClass(Class<?> clazz) {
        if (clazz.isAnnotationPresent(annotatedClass)) {
            try {
                Context.setBean(clazz.getName(), clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new ComponentInitializationException("Cannot initialize component. Make sure it is annotated with @Bean");
        }
    }

    public Class<Bean> getAnnotatedClass() {
        return annotatedClass;
    }
}
