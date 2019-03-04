package app.model.data;

import app.model.data.mappers.CourseMapper;
import app.model.entities.Course;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Stream;

public class CourseRepository extends Repository<Course> {

    private static String filename = "CourseRepository";

    public CourseRepository() {
        super(filename);
        mapper = new CourseMapper();
    }

    public Optional<Course> getCourseByShortName(String shortname) {
        try (Stream<String> stream = Files.lines(resource)) {

            return stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record))
                    .filter(course -> shortname.equals(course.getShortname()))
                    .findFirst();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Course> getCourseByCode(String courseCode) {
        try (Stream<String> stream = Files.lines(resource)) {

            return stream
                    .filter(record -> !"".equals(record.trim()))
                    .map(record -> mapper.map(record))
                    .filter(course -> courseCode.equals(course.getCode()))
                    .findFirst();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
