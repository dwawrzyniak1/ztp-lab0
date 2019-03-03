package app.model.data.mappers;

import app.model.entities.Student;

import java.time.LocalDate;

public class StudentMapper implements Mapper<Student> {

    private static String separator = ",";

    private static int idPosition = 0;
    private static int firstnamePosition = 1;
    private static int lastnamePosition = 2;
    private static int birthdayPosition = 3;

    @Override
    public Student map(String source) {
        String[] mapping = source.split(separator);
        return new Student(
                Long.valueOf(mapping[idPosition]),
                mapping[firstnamePosition],
                mapping[lastnamePosition],
                LocalDate.parse(mapping[birthdayPosition])
        );
    }

    @Override
    public String unmap(Student entity) {
        return entity.record(separator);
    }
}
