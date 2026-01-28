package org.example.lab5.controller;

import from_lab_2.ClassEmployee;
import from_lab_2.Employee;
import from_lab_2.EmployeeCondition;
import org.example.lab5.ClassEmployeeController;
import org.example.lab5.ClassEmployeeRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClassEmployeeController.class)
class ClassEmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    public ClassEmployeeRepo classEmployeeRepo;

    @Test
    void shouldCreateClassEmployee() throws Exception {
        ClassEmployee group = new ClassEmployee();
        group.setMaxCapacity(10);
        group.setNameOfGroup("A");

        when(classEmployeeRepo.save(any(ClassEmployee.class))).thenReturn(group);

        mockMvc.perform(post("/api/groups")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(0))
                .andExpect(jsonPath("$.nameOfGroup").value("A"));
    }

    @Test
    void shouldGetAllEmployees() throws Exception {
        ClassEmployee group = new ClassEmployee();
        group.setMaxCapacity(10);
        group.setNameOfGroup("A");
        Employee employee1 = new Employee("Jan","Kowalski", EmployeeCondition.DELEGATION, LocalDate.now(),200.2);
        Employee employee2 = new Employee("Grzes","Kowalski", EmployeeCondition.DELEGATION, LocalDate.now(),200.2);
        Employee employee3 = new Employee("Frann","Goldsmith", EmployeeCondition.DELEGATION, LocalDate.now(),200.2);
        group.addEmployee(employee1);
        group.addEmployee(employee2);
        group.addEmployee(employee3);
        group.setId(1L);

        when(classEmployeeRepo.findById(1L)).thenReturn(Optional.of(group));

        mockMvc.perform(get("/api/groups/1/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$.[0].name").value("Jan"));
    }

    @Test
    public void getAllClassEmployees_ShouldReturnList() throws Exception {
        // Arrange (Prepare Data)
        ClassEmployee group1 = new ClassEmployee();
        group1.setId(1L);
        group1.setNameOfGroup("Java Group");
        group1.setMaxCapacity(10);
        ClassEmployee group2 = new ClassEmployee();
        group2.setNameOfGroup("Python Group");
        group2.setMaxCapacity(15);
        group2.setId(2L);
        List<ClassEmployee> groups = Arrays.asList(group1, group2);

        when(classEmployeeRepo.findAll()).thenReturn(groups);

        mockMvc.perform(get("/api/groups/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nameOfGroup").value("Java Group"))
                .andExpect(jsonPath("$[1].nameOfGroup").value("Python Group"));
    }



    @Test
    public void getClassProcentegaFill_ShouldReturnDouble() throws Exception {
        int capacity = 10;
        int currentEmployees = 5;

        ClassEmployee group = new ClassEmployee();
        group.setMaxCapacity(capacity);
        group.setNameOfGroup( "Test Group");
        group.setId(1L);

        List<Employee> mockList = new ArrayList<>();
        for(int i=0; i<currentEmployees; i++) {
            mockList.add(new Employee());
        }
        group.setEmployees(mockList);


        when(classEmployeeRepo.findById(1L)).thenReturn(Optional.of(group));

        mockMvc.perform(get("/api/groups/1/fill")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("0.5"));
    }

    @Test
    public void getClassEmployeeById_WhenNotFound_ShouldReturn404() throws Exception {
        when(classEmployeeRepo.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/groups/99/employee"))
                .andExpect(status().isNotFound());
    }
}

