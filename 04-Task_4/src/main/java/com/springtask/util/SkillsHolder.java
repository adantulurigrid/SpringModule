package com.springtask.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SkillsHolder {

    @Value("#{'${employee.skills}'.split('-')}")
    private String[] skills;

    public void printSkills() {
        System.out.println("Skills from SpEL:");
        Arrays.stream(skills)
                .forEach(System.out::println);
    }
}