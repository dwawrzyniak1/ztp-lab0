package app.view;

import app.model.Service;
import app.model.entities.Course;
import app.model.entities.Student;

import java.util.List;

public class CoursesView implements View {

    private Service facade = new Service();

    @Override
    public String render(String shortname) {
        if ("".equals(shortname)) {
            return allCoursesInfo();
        }

        Course course = facade.getCourseByShortname(shortname).orElse(null);

        if (course == null) {
            return "";
        }

        return formattedCourseInfo(course) + "Zapisani studenci: \n" + getEnrolledStudents(course);
    }


    private String allCoursesInfo() {
        List<Course> courses = facade.getAllCourses();
        StringBuilder builder = new StringBuilder(formattedCourseInfoHeader());
        for (Course course : courses) {
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

    private String getEnrolledStudents(Course course) {
        if (course.getStudents() == null) {
            return "";
        }

        StringBuilder builder = new StringBuilder(formattedStudentInformationHeader());
        for (Student student : course.getStudents()) {
            builder.append(formattedStudentInformation(student));
        }

        return builder.toString();
    }

    private String formattedStudentInformationHeader() {
        return String.format("%10s%15s%20s%20s%n", "nr indeksu", "imie", "nazwisko", "data urodzin");
    }

    private String formattedStudentInformation(Student student) {
        return String.format(
                "%10d%15s%20s%20s%n", student.getId(), student.getFirstname(), student.getLastname(), student.getDateOfBirth().toString()
        );
    }
}
