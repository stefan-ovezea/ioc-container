package org.test.annotations;

import org.test.base.BaseResolver;
import org.test.di.Context;
import org.test.exceptions.ComponentInitializationException;

public class InjectableResolver implements BaseResolver {

    private Class<Injectable> annotatedClass = Injectable.class;

    public void initClass(Class<?> clazz) {
        if (clazz.isAnnotationPresent(annotatedClass)) {
            try {
                Context.setInjectable(clazz.getName(), clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new ComponentInitializationException("Cannot initialize component. Make sure it is annotated with @Injectable");
        }
    }

    public Class<Injectable> getAnnotatedClass() {
        return annotatedClass;
    }
}
