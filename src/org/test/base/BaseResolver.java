package org.test.base;

import java.lang.annotation.Annotation;

public interface BaseResolver {
    void initClass(Class<?> clazz);
    Class<? extends Annotation> getAnnotatedClass();
}
