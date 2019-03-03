package app.model.entities;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
public class Student {

    private Long id;

    private String firstname;

    private String lastname;

    private LocalDate dateOfBirth;

    private List<Course> joinedCourses;

    public Student(Long id, String firstname, String lastname, LocalDate dateOfBirth) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
    }

    public Student() {
    }

    public String record(String separator){
        return id +
                separator +
                firstname +
                separator +
                lastname +
                separator +
                dateOfBirth + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
