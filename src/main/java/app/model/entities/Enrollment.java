package app.model.entities;

import lombok.Data;

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
}
