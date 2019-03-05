package app.controller;

import app.model.data.CourseRepository;
import app.model.data.EnrollmentRepository;
import app.model.data.OperationNotPermitedException;
import app.model.data.StudentRepository;
import app.model.data.mappers.CourseMapper;
import app.model.data.mappers.EnrollmentMapper;
import app.model.data.mappers.StudentMapper;
import app.model.entities.Course;
import app.model.entities.Enrollment;
import app.model.entities.Student;

public class AdminController {

    private CourseRepository courseRepository = new CourseRepository();
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();
    private StudentRepository studentRepository = new StudentRepository();

    private StudentMapper studentMapper = new StudentMapper();
    private CourseMapper courseMapper = new CourseMapper();
    private EnrollmentMapper enrollmentMapper = new EnrollmentMapper();

    public void createStudent(String stringRepresentation){
        Student student = studentMapper.map(stringRepresentation);
        studentRepository.add(student);
    }

    public void deleteStudent(String stringRepresentation){
        Student student = studentMapper.map(stringRepresentation);
        studentRepository.delete(student);
    }

    public void updateStudent(String stringRepresentation){
        Student student = studentMapper.map(stringRepresentation);
        studentRepository.update(student);
    }

    public void createCourse(String stringRepresentation){
        Course course = courseMapper.map(stringRepresentation);
        courseRepository.add(course);
    }

    public void deleteCourse(String stringRepresentation){
        Course course = courseMapper.map(stringRepresentation);
        courseRepository.delete(course);
    }

    public void updateCourse(String stringRepresentation){
        Course course = courseMapper.map(stringRepresentation);
        courseRepository.update(course);
    }

    public void enrollStudentToCourse(String parameters) throws OperationNotPermitedException {
        Enrollment enrollment = enrollmentMapper.map(parameters);
        validateEnrollment(enrollment);
        enrollmentRepository.add(enrollment);
    }

    private void validateEnrollment(Enrollment enrollment) throws OperationNotPermitedException {
        Course course = courseRepository.getCourseByCode(enrollment.getCourseCode());
        Student student = studentRepository.getStudentById(enrollment.getStudentId());
        if(course == null || student == null){
            throw new OperationNotPermitedException("Student lub kurs nie istnieje");
        }
        if(enrollmentRepository.isPresent(enrollment)){
            throw new OperationNotPermitedException("Student jest już zapisany na ten kurs");
        }
    }

    public void discardStudentFromCourse(String parameters) {
        Enrollment enrollment = enrollmentMapper.map(parameters);
        enrollmentRepository.delete(enrollment);
    }
}
