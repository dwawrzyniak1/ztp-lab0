package app.model.entities;

import lombok.Data;

import java.util.Objects;

@Data
public class Enrollment {

    private Long studentId;

    private String courseCode;

    public Enrollment(Long studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
    }

    public String record(String separator) {
        return studentId +
                separator +
                courseCode + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return studentId.equals(that.studentId) &&
                courseCode.equals(that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }
}
