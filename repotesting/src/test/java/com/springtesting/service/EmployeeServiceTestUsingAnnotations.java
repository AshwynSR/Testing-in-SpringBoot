package com.springtesting.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.springtesting.entities.Employee;
import com.springtesting.exception.ResourceNotFoundException;
import com.springtesting.repository.EmployeeRepo;
import com.springtesting.service.impl.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTestUsingAnnotations {

   @Mock
   private EmployeeRepo employeeRepo;

   @InjectMocks
   private EmployeeServiceImpl employeeService;

   private Employee employee;

   @BeforeEach
   public void setUp() {
      employee = Employee.builder()
         .id(1L)
         .firstName("Ashwin")
         .lastName("Singh")
         .email("ashwin@example.com")
         .build();
   }

   //JUnit test for saveEmployee method using annotations (positive scenario - employee not found)
   @Test
   public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

      //given - precondition or setup
      given(employeeRepo.findByEmail(employee.getEmail())).willReturn(Optional.empty());
      given(employeeRepo.save(employee)).willReturn(employee);
      System.out.println(employeeRepo);
      System.out.println(employeeService);

      //when - action or the behaviour that we are going to test
      Employee savedEmployee = employeeService.savedEmployee(employee);
      System.out.println(savedEmployee);

      //then - verify the output
      Assertions.assertThat(savedEmployee).isNotNull();
      Assertions.assertThat(savedEmployee.getEmail()).isEqualTo(employee.getEmail());

   }

   //JUnit test for saveEmployee method using annotations which throws exception(negative scenario - employee exists)
   @DisplayName("JUnit test to throw exception")
   @Test
   public void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {

      //given - precondition or setup
      given(employeeRepo.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
      //given(employeeRepo.save(employee)).willReturn(employee);   This stubbing is not required as this method is not called.
      System.out.println(employeeRepo);
      System.out.println(employeeService);

      //when - action or the behaviour that we are going to test
      org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
            () -> employeeService.savedEmployee(employee));

      //then - verify the output
      verify(employeeRepo, never()).save(any(Employee.class));

   }

   //JUnit test for getAllEmployees method
   @Test
   public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {

      //given - precondition or setup
      Employee employee1 = Employee.builder()
            .id(2L)
            .firstName("Rahul")
            .lastName("Kumar")
            .email("rahul@example.com")
            .build();
      given(employeeRepo.findAll()).willReturn(List.of(employee, employee1));

      //when - action or thes behaviour that we are going to test
      List<Employee> receivedListOfEmployees = employeeService.getAllEmployees();

      //then - verify the output
      Assertions.assertThat(receivedListOfEmployees).isNotNull();
      Assertions.assertThat(receivedListOfEmployees.size()).isEqualTo(2);
   }

   //JUnit test for getAllEmployees method that returns empty list
   @Test
   public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

      //given - precondition or setup
      given(employeeRepo.findAll()).willReturn(Collections.emptyList());

      //when - action or thes behaviour that we are going to test
      List<Employee> receivedListOfEmployees = employeeService.getAllEmployees();

      //then - verify the output
      Assertions.assertThat(receivedListOfEmployees).isEmpty();
      Assertions.assertThat(receivedListOfEmployees.size()).isEqualTo(0);
   }

   //JUnit test for getEmployeeById method
   @Test
   public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
      //given - precondition or setup
      given(employeeRepo.findById(1L)).willReturn(Optional.of(employee));

      //when - action or thes behaviour that we are going to test
      Employee receivedEmployees = employeeService.getEmployeeById(1L).get();

      //then - verify the output
      Assertions.assertThat(receivedEmployees).isNotNull();
      Assertions.assertThat(receivedEmployees.getId()).isEqualTo(1L);
   }

   //JUnit test for updateEmployee method
   @Test
   public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
      //given - precondition or setup
      given(employeeRepo.save(employee)).willReturn(employee);
      employee.setEmail("ashwin@gmail.com");
      employee.setLastName("Rathore");

      //when - action or thes behaviour that we are going to test
      Employee updatedEmployee = employeeService.updateEmployee(employee);

      //then - verify the output
      Assertions.assertThat(updatedEmployee).isNotNull();
      Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("ashwin@gmail.com");
      Assertions.assertThat(updatedEmployee.getLastName()).isEqualTo("Rathore");
   }

   //JUnit test for deleteEmployee method
   @Test
   public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing() {
      //given - precondition or setup
      willDoNothing().given(employeeRepo).deleteById(1L);   //For methods that return void

      //when - action or thes behaviour that we are going to test
      employeeService.deleteEmployee(1L);

      //then - verify the output
      verify(employeeRepo, times(1)).deleteById(1L);
   }

}
