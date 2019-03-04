package app.model;

import app.model.data.CourseRepository;
import app.model.data.EnrollmentRepository;
import app.model.data.mappers.CourseMapper;
import app.model.entities.Course;

public class CourseFacade implements CRUDFacade{

    CourseRepository repository = new CourseRepository();
    EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    CourseMapper mapper = new CourseMapper();

    public void create(String parameters) {
        Course course = mapper.map(parameters);
        repository.add(course);
    }

    public void update(String parameters) {
        Course course = mapper.map(parameters);
        repository.update(course);
    }

    public void delete(String parameters) {
        Course course = new Course();
        String courseCode = parameters.split(",")[0];
        course.setCode(courseCode);
        repository.delete(course);
        enrollmentRepository.deleteByCourseCode(courseCode);
    }

}
