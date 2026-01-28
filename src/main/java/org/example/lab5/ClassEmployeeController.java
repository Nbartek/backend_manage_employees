package org.example.lab5;

import from_lab_2.ClassEmployee;
import from_lab_2.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class ClassEmployeeController {
    private final ClassEmployeeRepo classEmployeeRepo;

    public ClassEmployeeController(ClassEmployeeRepo classEmployeeRepo) {
        this.classEmployeeRepo = classEmployeeRepo;
    }

    @PostMapping
    public ResponseEntity<ClassEmployee> addClassEmployee(@RequestBody ClassEmployee classEmployee) {
        ClassEmployee savedClassEmployee = classEmployeeRepo.save(classEmployee);
        return ResponseEntity.ok(savedClassEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassEmployee(@PathVariable Long id) {
        if(classEmployeeRepo.existsById(id)) {
            classEmployeeRepo.deleteById(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}/upd")
    public ResponseEntity<ClassEmployee> updateEmployee(@PathVariable Long id, @RequestBody ClassEmployee employeeDetails) {
        return classEmployeeRepo.findById(id).map(group -> {
            // Update fields
            group.setNameOfGroup(employeeDetails.getNameOfGroup());
            group.setMaxCapacity(employeeDetails.getMaxCapacity());
            group.setGrade(employeeDetails.getGrade()); // Updates the Grade/Rate too
            return ResponseEntity.ok(classEmployeeRepo.save(group));
        }).orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/all")
    public ResponseEntity<List<ClassEmployee>> getAllClassEmployees(){
        List<ClassEmployee> classEmployees = classEmployeeRepo.findAll();
        return ResponseEntity.ok(classEmployees);
    }
    @GetMapping("/{id}/employee")
    public ResponseEntity<List<Employee>> getClassEmployeeById(@PathVariable Long id){
        var groupOptional = classEmployeeRepo.findById(id);

        if(groupOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(groupOptional.get().getEmployees());
    }

    @GetMapping("/{id}/fill")
    public ResponseEntity<Double> getClassProcentegaFill(@PathVariable Long id){
        List<Employee> employees = classEmployeeRepo.findById(id).orElse(null).getEmployees();
        if(employees == null) return ResponseEntity.notFound().build();
        else {
            ClassEmployee classEmployee = classEmployeeRepo.findById(id).orElse(null);
            Double result =  employees.size()/(double)classEmployee.getMaxCapacity();
            return ResponseEntity.ok(result);
        }
    }
}
