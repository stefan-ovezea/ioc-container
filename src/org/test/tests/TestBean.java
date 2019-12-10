package org.test.tests;

import org.test.annotations.Bean;
import org.test.annotations.Dependency;

@Bean
public class TestBean {

    @Dependency
    public MessageService messageService;

    public void writeMessage() {
        System.out.println(messageService.getMessage());
    }

}
