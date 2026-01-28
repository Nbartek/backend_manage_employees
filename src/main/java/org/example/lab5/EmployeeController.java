package org.example.lab5;


import from_lab_2.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //info że klasa obsługuje HTTP i zwraca JSON
@RequestMapping("/api/employees")
public class EmployeeController {
        private final EmployeeRepo employeeRepo;

        public EmployeeController(EmployeeRepo employeeRepo) {
            this.employeeRepo = employeeRepo;
        }

        @PostMapping                               //Re..Body żeby wymusić obiekt Java
        public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
            Employee sEmployee = employeeRepo.save(employee);
            return ResponseEntity.ok(sEmployee);
        }

        @PutMapping("/{id}/upd")
        public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
            if(employeeRepo.existsById(id)) {
                employeeDetails.setId(id);
                return ResponseEntity.ok(employeeRepo.save(employeeDetails));
            }else{
                return ResponseEntity.notFound().build();
            }
        }

        @DeleteMapping("/{id}")
       public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
            if(employeeRepo.existsById(id)) {
                employeeRepo.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.notFound().build();
            }
        }

        @GetMapping("/csv")
        public ResponseEntity<String> getEmployeesCSV(){
            List<Employee> employees = employeeRepo.findAll();

            StringBuilder csv = new StringBuilder();
            csv.append("ID,Name;Surname;Age;Salary;Condition\n");
            for (Employee employee : employees) {
                csv.append(employee.toString()).append("\n");
            }
            return ResponseEntity.ok().header("Content-Type", "text/csv").body(csv.toString());
        }
}
