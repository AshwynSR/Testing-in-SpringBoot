package com.springtesting.controller;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springtesting.entities.Employee;
import com.springtesting.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private EmployeeService employeeService;

   @Autowired
   private ObjectMapper objectMapper;  // To convert Object to JSON

   //JUnit test for Create Employee REST API
   @Test
   public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

      //given - precondition or setup
      Employee employee = Employee.builder()
            .firstName("Ashwin")
            .lastName("Singh")
            .email("ashwin@example.com")
            .build();
      given(employeeService.savedEmployee(ArgumentMatchers.any(Employee.class)))
            .willAnswer((invocation) -> invocation.getArgument(0));
      // given(employeeService.savedEmployee(ArgumentMatchers.any(Employee.class))).willReturn(employee);

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(employee)));

      //then - verify the result or output using assert statements
      response.andDo(MockMvcResultHandlers.print()) //To check response in console
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
   }

   // JUnit test for Get All employees REST API
   @Test
   public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception {

      //given
      List<Employee> employeeList = new ArrayList<>();
      employeeList.add(Employee.builder().firstName("Ashwin").lastName("Singh")
            .email("ashwin@example.com").build());
      employeeList.add(Employee.builder().firstName("Ashwyn").lastName("Rathore")
            .email("ashwin@gmail.com").build());
      given(employeeService.getAllEmployees()).willReturn(employeeList);

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));

   }

   // JUnit test for Get Employee by ID REST API (positive scenario, employee exists)
   @Test
   public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

      //given
      Long employeeId = 1L;
      Employee employee = Employee.builder().firstName("Ashwin").lastName("Singh")
            .email("ashwin@example.com").build();
      given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{employeeId}", employeeId));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

   }

   // JUnit test for Get Employee by ID REST API (negetive scenario, employee does not exists)
   @Test
   public void givenEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

      //given
      Long employeeId = 1L;
      given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{employeeId}", employeeId));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());
   }

   // JUnit test for Update Employee by ID REST API (positive scenario, employee does not exists)
   @Test
   public void givenEmployeeId_whenUpdateEmployeeById_thenReturnUpdateEmployeeObject() throws Exception {

      //given
      Long employeeId = 1L;
      Employee savedEmployee = Employee.builder().firstName("Ashwin").lastName("Singh")
            .email("ashwin@example.com").build();
      Employee updatedEmployee = Employee.builder().firstName("Ashwin").lastName("Rathore")
            .email("ashwin@gmail.com").build();
      given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
      given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
            // .willReturn((updatedEmployee));
            .willAnswer((invocation) -> invocation.getArgument(0));

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{employeeId}", employeeId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedEmployee)));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
            .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));

   }

   // JUnit test for Update Employee by ID REST API (negatuve scenario, employee does not exists)
   @Test
   public void givenEmployeeId_whenUpdateEmployeeById_thenReturnEmpty() throws Exception {

      //given
      Long employeeId = 1L;
      Employee updatedEmployee = Employee.builder().firstName("Ashwin").lastName("Rathore")
            .email("ashwin@gmail.com").build();
      given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{employeeId}", employeeId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedEmployee)));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound());

   }

   // JUnit test for Delete Employee by ID REST API
   @Test
   public void givenEmployeeId_whenDeleteEmployee_thenReturnSuccessString() throws Exception {

      //given
      Long employeeId = 1L;
      willDoNothing().given(employeeService).deleteEmployee(employeeId);

      //when - action or behavior that we are going to test
      ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{employeeId}", employeeId));

      //then - verify the result or output using assert statement
      response.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk());
   }
}
