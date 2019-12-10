package org.test.di;

import org.test.annotations.Bean;
import org.test.annotations.Dependency;
import org.test.annotations.Injectable;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DependencyInjectionFactory {

    public static final String ROOT_PACKAGE = "org.test.tests";

    public static void init() throws IllegalAccessException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration resources = classLoader.getResources(".");
            File root = new File(((URL) resources.nextElement()).getFile());
            setInjectablesOnContext(root, Injectable.class);
            setBeansOnContext(root, Bean.class);
            for (Object bean : Context.getAllBeans()) {
                for (Field field : bean.getClass().getDeclaredFields()) {
                    if (field.isAnnotationPresent(Dependency.class)) {
                        field.set(bean, Context.getInjectable(field.getType().getName()));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void setInjectablesOnContext(File root, Class annotation) throws ClassNotFoundException {
        for (Class clazz : findClassesByAnnotation(root, annotation)) {
            try {
                Context.setInjectable(clazz.getName(), clazz.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    private static void setBeansOnContext(File root, Class annotation) throws ClassNotFoundException {
        for (Class clazz : findClassesByAnnotation(root, annotation)) {
            try {
                Context.setBean(clazz.getName(), clazz.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Make it work for subfolders
    private static List<Class> findClassesByAnnotation(File directory, Class annotation) throws ClassNotFoundException {
        List classes = new ArrayList();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClassesByAnnotation(file, annotation));
            } else if (file.getPath().contains(ROOT_PACKAGE.replace(".", "/"))
                    && file.getName().endsWith(".class")) {
                Class clazz = Class.forName(ROOT_PACKAGE + "." + file.getName().substring(0, file.getName().length() - 6));
                if (clazz.isAnnotationPresent(annotation)) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }
}
