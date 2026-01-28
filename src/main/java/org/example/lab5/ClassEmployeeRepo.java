package org.example.lab5;

import from_lab_2.ClassEmployee;
import from_lab_2.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassEmployeeRepo extends JpaRepository<ClassEmployee,Long> {

}
