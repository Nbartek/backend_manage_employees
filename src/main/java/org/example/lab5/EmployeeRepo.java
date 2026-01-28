package org.example.lab5;

import from_lab_2.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepo extends JpaRepository<Employee,Long>{

    List<Employee> findBySurname(String surname);
}
