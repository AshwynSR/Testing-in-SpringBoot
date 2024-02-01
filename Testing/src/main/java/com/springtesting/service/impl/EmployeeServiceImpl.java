package com.springtesting.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springtesting.entities.Employee;
import com.springtesting.exception.ResourceNotFoundException;
import com.springtesting.repository.EmployeeRepo;
import com.springtesting.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

   // @Autowired - Not required as using constructor injection
   private EmployeeRepo employeeRepo;

   public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
      this.employeeRepo = employeeRepo;
   }

   @Override
   public Employee savedEmployee(Employee employee) {

      Optional<Employee> receivedEmployee = employeeRepo.findByEmail(employee.getEmail());
      if (receivedEmployee.isPresent()) {
         throw new ResourceNotFoundException("Employee already exists!!");
      }

      return employeeRepo.save(employee);
   }

   @Override
   public List<Employee> getAllEmployees() {
      return employeeRepo.findAll();
   }

   @Override
   public Optional<Employee> getEmployeeById(long id) {
      return employeeRepo.findById(id);
   }

   @Override
   public Employee updateEmployee(Employee employee) {
      return employeeRepo.save(employee);
   }

   @Override
   public void deleteEmployee(long id) {
      employeeRepo.deleteById(id);
   }

}
