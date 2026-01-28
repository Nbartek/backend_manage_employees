package from_lab_2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "grade")
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @OneToOne(mappedBy = "grade")
    @JsonIgnoreProperties("grade")
    ClassEmployee group;
    @Column(name = "date_of_grade")
    LocalDate grade_date;

    public Rate() {}

    public enum Grade {
        Niedop,
        Dop,
        Dost,
        Dobr,
        B_Dobr,
        Cel
    }
    @Enumerated(EnumType.STRING)
    @Column(name = "grade")
    Grade grade;
    @Column(name = "comment",nullable = true)
    String comment;


//    public Rate(
//             Grade grade,
//             String comment,
//             ClassEmployee group,
//             LocalDate grade_date) {
//        this.grade = grade;
//        this.comment = comment;
//        this.group = group;
//        this.grade_date = grade_date;
//    }
//    Rate(Grade grade){
//        this.grade = grade;
//
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ClassEmployee getGroup() {
        return group;
    }

    public void setGroup(ClassEmployee group) {
        this.group = group;
    }
    public LocalDate getGrade_date() {
        return grade_date;
    }
    public void setGrade_date(LocalDate grade_date) {
        this.grade_date = grade_date;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Grade getGrade() {
        return grade;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
