package app.view;

import app.model.Service;
import app.model.entities.Course;
import app.model.entities.Student;

import java.util.List;


public class StudentView implements View {

    private Service facade = new Service();

    @Override
    public String render(String studentId) {
        if ("".equals(studentId)) {
            return allStudentsInfo();
        }

        Student student = facade.getStudentById(Long.valueOf(studentId)).orElse(null);

        if (student == null) {
            return "";
        }

        return formattedStudentInformationHeader() +
                formattedStudentInformation(student) +
                "Zapisane kursy:\n" +
                getJoinedCourses(student);
    }

    private String allStudentsInfo() {
        List<Student> students = facade.getAllStudents();
        StringBuilder info = new StringBuilder(formattedStudentInformationHeader());
        for (Student student : students) {
            info.append(formattedStudentInformation(student));
        }
        return info.toString();
    }
    private String formattedStudentInformationHeader() {
        return String.format("%10s%15s%20s%20s%n", "nr indeksu", "imie", "nazwisko", "data urodzin");
    }

    private String formattedStudentInformation(Student student) {
        return String.format(
                "%10d%15s%20s%20s%n", student.getId(), student.getFirstname(), student.getLastname(), student.getDateOfBirth().toString()
        );
    }

    private String getJoinedCourses(Student student) {
        if (student.getJoinedCourses() == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(formattedCourseInfoHeader());
        for (Course course : student.getJoinedCourses()) {
            builder.append(formattedCourseInfo(course));
        }
        return builder.toString();
    }

    private String formattedCourseInfoHeader() {
        return String.format("%-40s%-20s%-20s%n", "Pełna nazwa", "Krótka nazwa", "Kod grupy");
    }

    private String formattedCourseInfo(Course course) {
        return String.format(
                "%-40s%-20s%-20s%n", course.getFullname(), course.getShortname(), course.getCode()
        );
    }
}
