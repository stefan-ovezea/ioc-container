package org.test.di;

import org.test.annotations.BeanResolver;
import org.test.annotations.DependencyResolver;
import org.test.annotations.InjectableResolver;
import org.test.base.BaseResolver;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class DependencyInjectionFactory {

    public static final String ROOT_PACKAGE = "org.test.tests";

    public static void init() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> resources = classLoader.getResources(".");
            File root = new File(resources.nextElement().getFile());
            setComponentsOnContext(new InjectableResolver(), root);
            setComponentsOnContext(new BeanResolver(), root);
            setBeansDependencies();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void setBeansDependencies() {
        DependencyResolver resolver = new DependencyResolver();
        for (Object bean : Context.getAllBeans()) {
            resolver.initDependencies(bean);
        }
    }

    private static void setComponentsOnContext(BaseResolver resolver, File root) throws ClassNotFoundException {
        for (Class<?> clazz : findClassesByAnnotation(root, resolver.getAnnotatedClass())) {
            resolver.initClass(clazz);
        }
    }

    // TODO: Make it work for subfolders
    private static List<Class<?>> findClassesByAnnotation(File directory, Class<? extends Annotation> annotation) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClassesByAnnotation(file, annotation));
            } else if (isClassInRootPackage(file)) {
                Class<?> clazz = Class.forName(ROOT_PACKAGE + "." + file.getName().substring(0, file.getName().length() - 6));
                if (clazz.isAnnotationPresent(annotation)) {
                    classes.add(clazz);
                }
            }
        }
        return classes;
    }

    private static boolean isClassInRootPackage(File file) {
        return file.getPath().contains(ROOT_PACKAGE.replace(".", File.separator))
                && file.getName().endsWith(".class");
    }
}
