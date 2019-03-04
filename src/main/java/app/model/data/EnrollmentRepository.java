package app.model.data;

import app.model.data.mappers.EnrollmentMapper;
import app.model.entities.Course;
import app.model.entities.Enrollment;
import app.model.entities.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnrollmentRepository extends Repository<Enrollment> {

    private static String filename = "EnrollmentRepository";

    private StudentRepository studentRepository = new StudentRepository();
    private CourseRepository courseRepository = new CourseRepository();

    public EnrollmentRepository() {
        super(filename);
        mapper = new EnrollmentMapper();
    }

    public void add(Enrollment entity) throws OperationNotPermitedException {
        try {
            validateEnrollment(entity);
            Files.write(resource, mapper.unmap(entity).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateEnrollment(Enrollment entity) throws OperationNotPermitedException {
        Course course = courseRepository.getCourseByCode(entity.getCourseCode()).orElse(null);
        Student student = studentRepository.getStudentById(entity.getStudentId()).orElse(null);
        if(course == null || student == null){
            throw new OperationNotPermitedException("Student lub kurs nie istnieje");
        }
    }

    public List<Enrollment> getByStudentId(Long studentId) {
        List<Enrollment> result = new ArrayList<>();
        try (Stream<String> stream = Files.lines(resource)) {

            result = stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record))
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
                    .map(record -> mapper.map(record))
                    .filter(enrollment -> code.equals(enrollment.getCourseCode()))
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteByStudentId(Long id) {
        try (Stream<String> stream = Files.lines(resource)) {

            List<String> deleted = stream
                    .filter(record -> !"".equals(record.trim()))
                    .filter(record -> !mapper.map(record).getStudentId().equals(id))
                    .collect(Collectors.toList());

            Files.write(resource, deleted);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteByCourseCode(String courseCode) {
        try (Stream<String> stream = Files.lines(resource)) {

            List<String> deleted = stream
                    .filter(record -> !"".equals(record.trim()))
                    .filter(record -> !mapper.map(record).getCourseCode().equals(courseCode))
                    .collect(Collectors.toList());

            Files.write(resource, deleted);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
