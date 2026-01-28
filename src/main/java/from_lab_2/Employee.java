package from_lab_2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.Year;

@Entity
@Table(name = "employees")
@Access(AccessType.PROPERTY)
public class Employee implements Comparable<Employee> {

    private LongProperty id = new SimpleLongProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private ObjectProperty<EmployeeCondition> employeeCondition = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dateOfBirth = new SimpleObjectProperty<>();
    private DoubleProperty salary = new SimpleDoubleProperty();

    private ObjectProperty<ClassEmployee> classEmployee = new SimpleObjectProperty<>();

    public Employee() {
        this.employeeCondition.set(EmployeeCondition.PRESENT);
        this.dateOfBirth.set(LocalDate.now());
    }

    @JsonCreator
    public Employee(@JsonProperty("name") String name,
                    @JsonProperty("surname") String surname,
                    @JsonProperty("condition") EmployeeCondition condition,
                    @JsonProperty("dateOfBirth") LocalDate dateOfBirth,
                    @JsonProperty("salary") double salary) {
        setName(name);
        setSurname(surname);
        setCondition(condition);
        setDateOfBirth(dateOfBirth);
        setSalary(salary);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() { return id.get(); }
    public void setId(Long id) { this.id.set(id); }
    @JsonIgnore
    public LongProperty idProperty() { return id; }

    @Column(name = "name")
    public String getName() { return name.get(); }
    public void setName(String n) { this.name.set(n); }
    @JsonIgnore
    public StringProperty nameProperty() { return name; }


    @Column(name = "surname")
    public String getSurname() { return surname.get(); }
    public void setSurname(String s) { this.surname.set(s); }
    @JsonIgnore
    public StringProperty surnameProperity() { return surname; }



    @Enumerated(EnumType.STRING)
    @Column(name = "employee_condition")
    public EmployeeCondition getCondition() {
        return employeeCondition.get();
    }

    public void setCondition(EmployeeCondition condition) {
        this.employeeCondition.set(condition);
    }

    @JsonIgnore
    public ObjectProperty<EmployeeCondition> conditionProperty() {
        return employeeCondition;
    }

    public void changeEmployeeCondition(EmployeeCondition condition){
        setCondition(condition);
    }

    @Column(name = "birth_date")
    public LocalDate getDateOfBirth() { return dateOfBirth.get(); }
    public void setDateOfBirth(LocalDate date) { this.dateOfBirth.set(date); }
    @JsonIgnore
    public ObjectProperty<LocalDate> birthDateProperty() { return dateOfBirth; }


    @Transient
    public int getAge() {
        if (dateOfBirth.get() == null) return 0;
        return Year.now().getValue() - dateOfBirth.get().getYear();
    }

    @Column(name = "salary")
    public double getSalary() { return salary.get(); }
    public void setSalary(double s) { this.salary.set(s); }
    @JsonIgnore
    public DoubleProperty salaryProperty() { return salary; }

    public void changeSalary(double bonus){
        setSalary(getSalary() + bonus);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id")
    @JsonIgnoreProperties("employees")
    public ClassEmployee getClassEmployee() {
        return classEmployee.get();
    }

    public void setClassEmployee(ClassEmployee classEmployee) {
        this.classEmployee.set(classEmployee);
    }

    @JsonIgnore
    public ObjectProperty<ClassEmployee> classEmployeeProperty() {
        return classEmployee;
    }

    @Override
    public int compareTo(Employee o) {
        int result = this.surname.get().compareTo(o.surname.get());
        if (result == 0) {
            return this.name.get().compareTo(o.name.get());
        }
        return result;
    }

    public void printing(){
        System.out.println("Name: " + getName());
        System.out.println("Surname: " + getSurname());
        System.out.println("Condition: " + getCondition());
        System.out.println("Salary: " + getSalary());
    }
}