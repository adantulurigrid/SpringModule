package com.springtask.runner;

import com.springtask.config.TimeConfigProperties;
import com.springtask.service.EmployeeService;
import com.springtask.util.SkillsHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ConfigurableEnvironment environment;
    private final ApplicationContext applicationContext;
    private final SkillsHolder skillsHolder;
    private final TimeConfigProperties timeConfigProperties;
    private final EmployeeService employeeService;

    public StartupRunner(ConfigurableEnvironment environment,
                         ApplicationContext applicationContext,
                         SkillsHolder skillsHolder,
                         TimeConfigProperties timeConfigProperties,
                         EmployeeService employeeService) {

        this.environment = environment;
        this.applicationContext = applicationContext;
        this.skillsHolder = skillsHolder;
        this.timeConfigProperties = timeConfigProperties;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) {

        System.out.println("================ ENVIRONMENT PROPERTIES ================");

        Arrays.stream(environment.getActiveProfiles())
                .forEach(profile -> System.out.println("Active Profile: " + profile));

        System.out.println("Application Name: " + environment.getProperty("spring.application.name"));
        System.out.println("Datasource URL: " + environment.getProperty("spring.datasource.url"));

        System.out.println("\n================ ALL BEANS ================");

        String[] beanNames = applicationContext.getBeanDefinitionNames();

        Arrays.sort(beanNames);

        for (String bean : beanNames) {
            System.out.println(bean);
        }

        System.out.println("\n================ SPEL ARRAY ================");

        skillsHolder.printSkills();

        System.out.println("\n================ CONFIG PROPERTIES ================");

        System.out.println(timeConfigProperties);

        System.out.println("\n================ DATABASE INSERT ================");

        employeeService.addSampleEmployees();
    }
}