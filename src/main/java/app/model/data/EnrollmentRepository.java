package app.model.data;

import app.model.data.mappers.EnrollmentMapper;
import app.model.entities.Enrollment;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnrollmentRepository extends Repository<Enrollment> {

    private static String filename = "EnrollmentRepository";

    public EnrollmentRepository() {
        super(filename);
        mapper = new EnrollmentMapper();
    }

    public List<Enrollment> getByStudentId(Long studentId) {
        List<Enrollment> result = new ArrayList<>();
        try (Stream<String> stream = Files.lines(resource)) {

            result = stream
                    .map(record -> (Enrollment)mapper.map(record))
                    .filter(enrollment -> studentId.equals(enrollment.getStudentId()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Enrollment> getByCourseCode(String code) {
        List<Enrollment> result = new ArrayList<>();
        try (Stream<String> stream = Files.lines(resource)) {

            result = stream
                    .map(record -> (Enrollment)mapper.map(record))
                    .filter(enrollment -> code.equals(enrollment.getCourseCode()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
