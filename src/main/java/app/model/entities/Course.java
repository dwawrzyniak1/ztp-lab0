package app.model.entities;

import lombok.Data;

import java.util.List;

@Data
public class Course {

    private String fullname;

    private String shortname;

    private String code;

    private List<Student> students;

    public Course(){}

    public Course(String fullname, String shortname, String code) {
        this.fullname = fullname;
        this.shortname = shortname;
        this.code = code;
    }

    public String record(String separator){
        return fullname +
                separator +
                shortname +
                separator +
                code + "\n";
    }

    public String toString(String courseFormatting) {
        return String.format(
                courseFormatting, getFullname(), getShortname(), getCode()
        );
    }
}
