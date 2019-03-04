package app.model;

import app.model.data.EnrollmentRepository;
import app.model.data.StudentRepository;
import app.model.data.mappers.StudentMapper;
import app.model.entities.Student;

public class StudentFacade implements CRUDFacade {

    private StudentRepository repository = new StudentRepository();
    private EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

    private StudentMapper mapper = new StudentMapper();

    public void create(String parameters) {
        Student student = mapper.map(parameters);
        repository.add(student);
    }

    public void update(String parameters) {
        Student student = mapper.map(parameters);
        repository.update(student);
    }

    public void delete(String parameters) {
        Long id = Long.valueOf(parameters);
        Student student = new Student();
        student.setId(id);
        repository.delete(student);
        enrollmentRepository.deleteByStudentId(id);
    }

}
