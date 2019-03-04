package app.model;

import app.model.data.CourseRepository;
import app.model.data.EnrollmentRepository;
import app.model.data.OperationNotPermitedException;
import app.model.data.StudentRepository;
import app.model.entities.Course;
import app.model.entities.Enrollment;
import app.model.entities.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Service {

    private CourseRepository courseRepository = new CourseRepository();
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private StudentRepository studentRepository = new StudentRepository();

    public Optional<Course> getCourseByShortname(String shortname){
        Course course = courseRepository.getCourseByShortName(shortname).orElse(null);

        if(course == null){
            return Optional.empty();
        }

        List<Enrollment> enrollments = enrollmentRepository.getByCourseCode(course.getCode());

        List<Student> enrolledStudents = new ArrayList<>();
        for(Enrollment enr : enrollments){
            studentRepository.getStudentById(enr.getStudentId()).ifPresent(enrolledStudents::add);
        }
        course.setStudents(enrolledStudents);

        return Optional.of(course);
    }

    public Optional<Student> getStudentById(Long id){
        Student student = studentRepository.getStudentById(id).orElse(null);

        if(student == null){
            return Optional.empty();
        }

        List<Enrollment> enrollments = enrollmentRepository.getByStudentId(student.getId());

        List<Course> joinedCourses = new ArrayList<>();
        for(Enrollment enr : enrollments){
            courseRepository.getCourseByCode(enr.getCourseCode()).ifPresent(joinedCourses::add);
        }
        student.setJoinedCourses(joinedCourses);

        return Optional.of(student);
    }

    public void enrollStudentToCourse(String parameters) throws OperationNotPermitedException {
        String[] inputs = parameters.split(",");
        Enrollment enrollment = new Enrollment(Long.valueOf(inputs[0]), inputs[1]);
        enrollmentRepository.add(enrollment);
    }

    public List<Student> getAllStudents(){
        return studentRepository.getAll();
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAll();
    }

    public void discardStudentFromCourse(String parameters) {
        String[] inputs = parameters.split(",");
        Enrollment enrollment = new Enrollment(Long.valueOf(inputs[0]), inputs[1]);
        enrollmentRepository.delete(enrollment);
    }
}
