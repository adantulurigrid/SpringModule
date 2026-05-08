package com.springtask;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(properties = {
        "company.name=OpenAI",
        "company.location=India"
})
public class PropertyInjectionTest {

    @Value("${company.name}")
    private String companyName;

    @Value("${company.location}")
    private String companyLocation;

    @Test
    void testProperties() {

        System.out.println(companyName);
        System.out.println(companyLocation);

        assertEquals("OpenAI", companyName);
        assertEquals("India", companyLocation);
    }
}