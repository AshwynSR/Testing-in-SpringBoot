package com.springtesting.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import com.springtesting.entities.Employee;
import com.springtesting.repository.EmployeeRepo;
import com.springtesting.service.impl.EmployeeServiceImpl;

public class EmployeeServiceTestUsingMockMethod {

   private EmployeeRepo employeeRepo;
   private EmployeeService employeeService;

   @BeforeEach
   public void setup() {
      employeeRepo = Mockito.mock(EmployeeRepo.class);
      employeeService = new EmployeeServiceImpl(employeeRepo);
   }

   // JUnit test for saveEmployee method using the mock() method provided by Mockito
   @Test
   @DisplayName("JUnit test for savedEmployee method")
   public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

      //given - precondition or setup
      Employee employee = Employee.builder()
         .id(1L) // mock object requires Id
         .firstName("Ashwin")
         .lastName("Singh")
         .email("ashwin@example.com")
            .build();

      BDDMockito.given(employeeRepo.findByEmail(employee.getEmail())).willReturn(Optional.empty());
      BDDMockito.given(employeeRepo.save(employee)).willReturn(employee);
      System.out.println(employeeRepo);
      System.out.println(employeeService);

      //when - action or the behavior that we are going to test
      Employee savedEmployee = employeeService.savedEmployee(employee);
      System.out.println(savedEmployee);

      //then - verify the  output
      Assertions.assertThat(savedEmployee).isNotNull();
      Assertions.assertThat(savedEmployee.getEmail()).isEqualTo(employee.getEmail());

   }
}
