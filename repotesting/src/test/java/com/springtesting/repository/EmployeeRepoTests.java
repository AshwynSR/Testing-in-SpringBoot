package com.springtesting.repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springtesting.entities.Employee;

@DataJpaTest
public class EmployeeRepoTests {

   @Autowired
   private EmployeeRepo employeeRepo;

   private Employee employee;

   @BeforeEach
   public void setUp() {
      employee = Employee.builder()
            .firstName("Ashwin")
            .lastName("Singh")
            .email("ashwin@example.com")
            .build();
   }

   //JUnit test for save employee operation
   @Test
   @DisplayName("JUnit test for save employee operation")
   public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

      //given - precondition or setup
      // Employee employee = Employee.builder()
      //       .firstName("Ashwin")
      //       .lastName("Singh")
      //       .email("ashwin@example.com")
      //       .build();

      //when - action or the behavior that we are going test
      Employee savedEmployee = employeeRepo.save(employee);

      //then - verify the output
      Assertions.assertThat(savedEmployee).isNotNull();
      Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
   }

   //JUnit test for get all employees operation
   @Test
   public void givenEmployeeList_whenFindAll_thenEmployeeList() {

      //given - precondition or setup
      Employee employee1 = Employee.builder()
            .firstName("Ashwin")
            .lastName("Singh")
            .email("ashwin@example.com")
            .build();
      Employee employee2 = Employee.builder()
            .firstName("Harshit")
            .lastName("Singh")
            .email("harshit@example.com")
            .build();
      Employee employee3 = Employee.builder()
            .firstName("Raju")
            .lastName("Sharma")
            .email("raju@example.com")
            .build();
      employeeRepo.save(employee1);
      employeeRepo.save(employee2);
      employeeRepo.save(employee3);

      //when - action or the behavior that we are going test
      List<Employee> employeeList = employeeRepo.findAll();

      //then - verify the output
      Assertions.assertThat(employeeList).isNotNull();
      Assertions.assertThat(employeeList.size()).isEqualTo(3);
   }

   //JUnit test for get employee by id operation
   @Test
   public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

      //given - precondition or setup
      // Employee employee = Employee.builder()
      //       .firstName("Ashwin")
      //       .lastName("Singh")
      //       .email("ashwin@example.com")
      //       .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee receivedEmployee = employeeRepo.findById(employee.getId()).get();

      //then - verify the output
      Assertions.assertThat(receivedEmployee).isNotNull();
   }

   //JUnit test for get Employee by email operation
   @Test
   public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee receivendEmployee = employeeRepo.findByEmail(employee.getEmail()).get();

      //then - verify the output
      Assertions.assertThat(receivendEmployee).isNotNull();
   }

   //JUnit test for update employee operation
   @Test
   public void givenUpdateEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee() {

      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee savedEmployee = employeeRepo.findById(employee.getId()).get();
      savedEmployee.setEmail("ash@gmail.com");
      savedEmployee.setLastName("Rathore");
      Employee receivedEmployee = employeeRepo.save(savedEmployee);

      //then - verify the output
      Assertions.assertThat(receivedEmployee.getEmail()).isEqualTo("ash@gmail.com");
      Assertions.assertThat(receivedEmployee.getLastName()).isEqualTo("Rathore");
   }

   //JUnit test for delete employee operation
   @Test
   public void givenEmployeeObject_whenDelete_thenDeleteEmployee() {

      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      employeeRepo.deleteById(employee.getId());
      //employeeRepe.delete(employee);
      Optional<Employee> employeeOptional = employeeRepo.findById(employee.getId());

      //then - verify the output
      Assertions.assertThat(employeeOptional).isEmpty();
   }

   //JUnit test for custom query using JPQL with index parameters
   @Test
   public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {
      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee receivedEmployee = employeeRepo.findByJPQL("Ashwin", "Singh");

      //then - verify the output
      Assertions.assertThat(receivedEmployee).isNotNull();
   }

   //JUnit test for custom query using JPQL with named parameters
   @Test
   public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {
      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee receivedEmployee = employeeRepo.findByJPQLNamedParams("Ashwin", "Singh");

      //then - verify the output
      Assertions.assertThat(receivedEmployee).isNotNull();
   }

   //JUnit test for custom query using native SQL with index
   @Test
   public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {
         //given - precondition or setup
      //    Employee employee = Employee.builder()
      //                .firstName("Ashwin")
      //                .lastName("Singh")
      //                .email("ashwin@example.com")
      //                .build();
         employeeRepo.save(employee);

         //when - action or the behavior that we are going test
         Employee receivedEmployee = employeeRepo.findByNativeSQL("Ashwin", "Singh");

         //then - verify the output
         Assertions.assertThat(receivedEmployee).isNotNull();
   }

   //JUnit test for custom query using native SQL with named parameters
   @Test
   public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {
      //given - precondition or setup
      // Employee employee = Employee.builder()
      //             .firstName("Ashwin")
      //             .lastName("Singh")
      //             .email("ashwin@example.com")
      //             .build();
      employeeRepo.save(employee);

      //when - action or the behavior that we are going test
      Employee receivedEmployee = employeeRepo.findByNativeSQLNamedParams("Ashwin", "Singh");

      //then - verify the output
      Assertions.assertThat(receivedEmployee).isNotNull();
   }
}
