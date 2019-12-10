package org.test;

import org.test.di.Context;
import org.test.di.DependencyInjectionFactory;
import org.test.tests.TestBean;

public class Main {

    public static void main(String[] args) throws IllegalAccessException {
        DependencyInjectionFactory.init();
        ((TestBean)Context.getBean("org.test.tests.TestBean")).writeMessage();
    }
}
