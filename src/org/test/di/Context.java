package org.test.di;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Injectables injectables = new Injectables();
    private static Beans beans = new Beans();

    public static void setInjectable(String key, Object injectable) {
        injectables.setInjectable(key, injectable);
    }

    public static void setBean(String key, Object bean) {
        beans.setBean(key, bean);
    }

    public static Collection<Object> getAllInjectables() {
        return injectables.getAllInjectables();
    }

    public static Collection<Object> getAllBeans() {
        return beans.getAllBeans();
    }

    public static Object getInjectable(String key) {
        return injectables.getInjectable(key);
    }

    public static Object getBean(String key) {
        return beans.getBean(key);
    }
}

class Injectables {
    private Map<String, Object> injectables = new HashMap<>();

    public Object getInjectable(String key) {
        return injectables.get(key);
    }

    void setInjectable(String key, Object injectable) {
        injectables.putIfAbsent(key, injectable);
    }

    Collection<Object> getAllInjectables() {
        return injectables.values();
    }
}

class Beans {
    private Map<String, Object> beans = new HashMap<>();

    public Object getBean(String key) {
        return beans.get(key);
    }

    void setBean(String key, Object injectable) {
        beans.putIfAbsent(key, injectable);
    }

    Collection<Object> getAllBeans() {
        return beans.values();
    }
}
