package com.springtask.config;

import com.springtask.service.Foo;
import com.springtask.service.FooImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AppConfig {

    /*
     * Bean with implementation return type
     */
    @Bean
    public FooImpl fooImplBean() {

        return new FooImpl();
    }

    /*
     * Bean with interface return type
     */
    @Bean
    @Primary
    public Foo fooInterfaceBean() {

        return new FooImpl();
    }
}
