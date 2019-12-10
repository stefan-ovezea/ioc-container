package org.test.annotations;

import org.test.di.Context;
import org.test.exceptions.ComponentInitializationException;

public class InjectableResolver {
    public static void initClass(Class clazz) {
        if (clazz.isAnnotationPresent(Injectable.class)) {
            try {
                Context.setInjectable(clazz.getName(), clazz.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            throw new ComponentInitializationException("Cannot initialize component. Make sure it is annotated with @Component");
        }
    }
}
