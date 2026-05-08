package com.springtask.service;

import com.springtask.entity.Employee;
import com.springtask.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    @Override
    public void addSampleEmployees() {

        employeeRepository.save(new Employee("Aditya", "Backend"));
        employeeRepository.save(new Employee("Rahul", "Frontend"));

        System.out.println("Sample employees inserted");
    }

    @Override
    public long countEmployees() {

        return employeeRepository.count();
    }

    @Override
    public void deleteAllEmployees() {

        employeeRepository.deleteAll();
    }
}