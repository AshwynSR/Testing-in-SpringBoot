package com.springtesting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springtesting.entities.Employee;
import com.springtesting.service.EmployeeService;



@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

   private EmployeeService employeeService;

   public EmployeeController(EmployeeService employeeService) {
      this.employeeService = employeeService;
   }

   @PostMapping
   @ResponseStatus(HttpStatus.CREATED)
   public Employee createEmployee(@RequestBody Employee employee) {
      return employeeService.savedEmployee(employee);
   }

   @GetMapping
   public List<Employee> getAllEmployees() {
      return employeeService.getAllEmployees();
   }

   @GetMapping("/{id}")
   public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
      return employeeService.getEmployeeById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
   }

   @PutMapping("/{id}")
   public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
      return employeeService.getEmployeeById(id)
            .map(savedEmployee -> {
               savedEmployee.setFirstName(employee.getFirstName());
               savedEmployee.setLastName(employee.getLastName());
               savedEmployee.setEmail(employee.getEmail());
               Employee updatedEmployee = employeeService.updateEmployee(savedEmployee);
               return ResponseEntity.ok(updatedEmployee);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());

   }

   @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
      employeeService.deleteEmployee(id);
      return new ResponseEntity<String>("Deleted the employee successfully!!", HttpStatus.OK);
   }
}
