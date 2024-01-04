package com.springtesting.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.springtesting.entities.Employee;

@DataJpaTest
public class EmployeeRepoTests {

   @Autowired
   private EmployeeRepo employeeRepo;

   //JUnit test for save employee operation
   @Test
   @DisplayName("JUnit test for save employee operation")
   public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

      //given
      Employee employee = Employee.builder()
            .firstName("Ashwin")
            .lastName("Singh")
            .email("ashwin@example.com")
            .build();

      //when
      Employee savedEmployee = employeeRepo.save(employee);

      //then
      Assertions.assertThat(savedEmployee).isNotNull();
      Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
   }

   //JUnit test for get all employees operation
   @Test
   public void givenEmployeeList_whenFindAll_thenEmployeeList() {

      //given
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

      //when
      List<Employee> employeeList = employeeRepo.findAll();

      //then
      Assertions.assertThat(employeeList).isNotNull();
      Assertions.assertThat(employeeList.size()).isEqualTo(3);
   }

   //JUnit test for get employee by id operation
   @Test
   public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

      //given
      Employee employee = Employee.builder()
            .firstName("Ashwin")
            .lastName("Singh")
            .email("ashwin@example.com")
            .build();
      employeeRepo.save(employee);

      //when
      Employee receivedEmployee = employeeRepo.findById(employee.getId()).get();

      //then
      Assertions.assertThat(receivedEmployee).isNotNull();

   }
}
