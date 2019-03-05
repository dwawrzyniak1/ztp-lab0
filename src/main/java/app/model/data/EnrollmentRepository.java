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

    public EnrollmentRepository() {
        super(filename);
        mapper = new EnrollmentMapper();
    }

    @Override
    public void add(Enrollment entity) {
        try {
            Files.write(resource, mapper.unmap(entity).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            handleError(e);
        }
    }

    public boolean isPresent(Enrollment entity){
        Optional<Enrollment> fromFile = Optional.empty();
        try (Stream<String> stream = Files.lines(resource)) {

            fromFile = stream
                    .map(record -> mapper.map(record))
                    .filter(enrollment -> enrollment.equals(entity))
                    .findFirst();

        } catch (IOException e) {
            handleError(e);
        }
        return fromFile.isPresent();
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
            handleError(e);
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
            handleError(e);
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
            handleError(e);
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
            handleError(e);
        }
    }
}
