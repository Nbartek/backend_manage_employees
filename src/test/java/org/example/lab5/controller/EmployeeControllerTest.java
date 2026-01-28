package org.example.lab5.controller;

import from_lab_2.Employee;
import from_lab_2.EmployeeCondition;
import org.example.lab5.EmployeeController;
import org.example.lab5.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    public EmployeeRepo employeeRepo;

    @Test
    void shouldCreateEmployee() throws Exception {
     Employee employee2 = new Employee("Jan","Kowalski", EmployeeCondition.DELEGATION, LocalDate.now(),200.2);

        when(employeeRepo.save(any(Employee.class))).thenReturn(employee2);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.name").value("Jan"));
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees/csv"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/csv"));
    }
    @Test
    void shouldDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/0"))
                .andExpect(status().isNotFound());
    }

}