package app.controller;

import app.model.data.CourseRepository;
import app.model.data.EnrollmentRepository;
import app.model.data.StudentRepository;
import app.model.entities.Course;
import app.model.entities.Student;

import java.util.List;

public class MainController{

    private CourseRepository courseRepository = new CourseRepository();
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private StudentRepository studentRepository = new StudentRepository();

    public List<Student> getAllStudents() {
        return studentRepository.getAll();
    }

    public Student getStudentById(String parameter) {
        return studentRepository.getStudentById(Long.valueOf(parameter));
    }

    public List<Course> getAllCourses() {
        return courseRepository.getAll();
    }

    public Course getCourseByShortname(String shortname) {
        return courseRepository.getCourseByShortName(shortname);
    }
}
