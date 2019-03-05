package app.model.data;

import app.model.data.mappers.StudentMapper;
import app.model.entities.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;

public class StudentRepository extends Repository<Student> {

    private static String filename = "StudentRepository";

    public StudentRepository() {
        super(filename);
        mapper = new StudentMapper();
    }

    public Student getStudentById(Long studentId) {
        try (Stream<String> stream = Files.lines(resource)) {

            return stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record))
                    .filter(student -> studentId.equals(student.getId()))
                    .findFirst();

        } catch (IOException e) {
            handleError(e);
        }
        return Optional.empty();
    }
}
