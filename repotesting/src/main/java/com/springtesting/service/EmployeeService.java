package com.springtesting.service;

import java.util.List;
import java.util.Optional;

import com.springtesting.entities.Employee;

public interface EmployeeService {

   Employee savedEmployee(Employee employee);

   List<Employee> getAllEmployees();

   Optional<Employee> getEmployeeById(long id);

   Employee updateEmployee(Employee employee);

   void deleteEmployee(long id);
}
