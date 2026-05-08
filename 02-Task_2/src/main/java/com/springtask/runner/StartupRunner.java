package com.springtask.runner;

import com.springtask.service.BeanInjectionDemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ConfigurableEnvironment environment;
    private final ApplicationContext applicationContext;
    private final BeanInjectionDemo beanInjectionDemo;

    public StartupRunner(ConfigurableEnvironment environment,
                         ApplicationContext applicationContext,
                         BeanInjectionDemo beanInjectionDemo) {

        this.environment = environment;
        this.applicationContext = applicationContext;
        this.beanInjectionDemo = beanInjectionDemo;
    }

    @Override
    public void run(String... args) {

        System.out.println("\n================ ACTIVE PROFILES ================\n");

        Arrays.stream(environment.getActiveProfiles())
                .forEach(System.out::println);

        System.out.println("\n================ BEAN NAMES ================\n");

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        Arrays.sort(beanNames);

        for (String bean : beanNames) {

            System.out.println(bean);
        }

        System.out.println("\n================ BEAN INJECTION TEST ================\n");

        beanInjectionDemo.testInjection();
    }
}