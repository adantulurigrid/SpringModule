package com.springtask;

import com.springtask.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class EmployeeIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void integrationTest() {

        employeeService.deleteAllEmployees();

        employeeService.addSampleEmployees();

        long count = employeeService.countEmployees();

        System.out.println("Employee Count: " + count);

        assertEquals(2, count);
    }
}