package com.springtask;

import com.springtask.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DirtyContextTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void test1() {

        employeeService.deleteAllEmployees();

        employeeService.addSampleEmployees();

        assertEquals(2, employeeService.countEmployees());

        System.out.println("Test1 Employee Count: "
                + employeeService.countEmployees());
    }

    @Test
    void test2() {

        /*
         * Cleanup to ensure isolation
         */

        employeeService.deleteAllEmployees();

        assertEquals(0, employeeService.countEmployees());

        System.out.println("Test2 Employee Count: "
                + employeeService.countEmployees());
    }
}