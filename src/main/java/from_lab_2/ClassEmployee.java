package from_lab_2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Entity
@Table(name = "class_employees")
@Access(AccessType.PROPERTY) // Dostęp przez gettery
public class ClassEmployee implements Comparator<Employee> {


//    @JsonIgnore
    private final LongProperty id = new SimpleLongProperty();
//    @JsonIgnore
    private final StringProperty nameOfGroup = new SimpleStringProperty();
//    @JsonIgnore
    private final IntegerProperty maxCapacity = new SimpleIntegerProperty();

    private Rate grade;
    private List<Employee> employees = new ArrayList<>();

    public ClassEmployee() {
    }
//@JsonCreator
//    public ClassEmployee(@JsonProperty("nameOfGroup") String nameOfGroup,@JsonProperty("maxCapacity") int maxCapacity) {
//        setNameOfGroup(nameOfGroup);
//        setMaxCapacity(maxCapacity);
//    }

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id",nullable = true)
    @JsonIgnoreProperties("group")
    public Rate getGrade() {
        return grade;
    }
    public void setGrade(Rate grade) {
        this.grade = grade;

        if (grade != null) {
            grade.setGroup(this);
        }
    }
    public void deleteGrade(){this.grade.setGrade_date(null);}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    public Long getId() { return id.get(); }
    public void setId(Long id) { this.id.set(id); }
    @JsonIgnore
    public LongProperty idProperty() { return id; }

    @Column(name = "name_of_group")
    public String getNameOfGroup() { return nameOfGroup.get(); }
    public void setNameOfGroup(String name) { this.nameOfGroup.set(name); }
    @JsonIgnore
    public StringProperty nameOfGroupProperty() { return nameOfGroup; }

    @Column(name = "max_capacity")
    public int getMaxCapacity() { return maxCapacity.get(); }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity.set(maxCapacity); }
    @JsonIgnore
    public IntegerProperty maxCapacityProperty() { return maxCapacity; }


    @OneToMany(
            mappedBy = "classEmployee",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("classEmployee")
    public List<Employee> getEmployees() {
        return employees;
    }


    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    @JsonIgnore
    @Transient
    public ObservableList<Employee> getEmployeesObservable() {
        return FXCollections.observableList(employees);
    }

    @JsonIgnore
    @Transient
    public ListProperty<Employee> employeesProperty() {
        return new SimpleListProperty<>(getEmployeesObservable());
    }




    public int employeesCount() {
        return employees.size();
    }

    public void addEmployee(Employee employee) {
        if (employees.size() >= getMaxCapacity()) {
            System.out.println("Maximum capacity exceeded");
            return;
        }
        if (employees.contains(employee)) {
            System.out.println("Employee already exists");
            return;
        }

        employees.add(employee);

        employee.setClassEmployee(this);
    }

    public void removeEmployee(Employee employee) {
        if (employees.remove(employee)) {
            employee.setClassEmployee(null); // Czyszczenie relacji
        } else {
            System.out.println("Employee does not exist");
        }
    }

    public void addSalary(Employee employee, double bonus) {

        if(employees.contains(employee)) {

            employee.changeSalary(bonus);
        } else {
            System.out.println("Employee does not exist");
        }
    }

    public void changeCondition(Employee employee, EmployeeCondition condition) {
        if(employees.contains(employee)) {
            employee.changeEmployeeCondition(condition);
        } else {
            System.out.println("Employee does not exist");
        }
    }

    public Employee search(String searchString) {

        for (Employee employee : employees) {
            if (employee.getSurname().equalsIgnoreCase(searchString)) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String ocenaInfo;

        if (Hibernate.isInitialized(grade) && grade != null) {
            ocenaInfo = " Ocena: " + grade.getGrade() ;
        } else if (grade == null) {
            ocenaInfo = " Brak ocen";
        } else {
            ocenaInfo = " Oceny jeescze nie ma";
        }

        return getNameOfGroup() + ", wolne miejsce: " + (getMaxCapacity() - employeesCount()) + ocenaInfo;
    }


    public static int compareByFill(Object a, Object b) {
        ClassEmployee e1 = (ClassEmployee) a;
        ClassEmployee e2 = (ClassEmployee) b;
        double e1_perc = (double)e1.employeesCount() / e1.getMaxCapacity();
        double e2_perc = (double)e2.employeesCount() / e2.getMaxCapacity();
        return Double.compare(e1_perc, e2_perc);
    }

    @Override
    public int compare(Employee o1, Employee o2) {
        return o1.getSurname().compareTo(o2.getSurname());
    }

    public boolean findGroupForEmployee(Employee employee) {
        return employees.contains(employee);
    }
}