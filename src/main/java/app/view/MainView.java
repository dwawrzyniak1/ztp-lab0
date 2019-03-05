package app.view;

import app.controller.MainController;
import app.model.entities.Course;
import app.model.entities.Student;

import java.util.List;
import java.util.Scanner;

public class MainView implements View {

    private static final String formatting = "%-45s%-70s%-70s%n";
    public static final String INSTRUCTION = String.format(formatting, "FUNKCJA", "KOMENDA", "PRZYKLAD") +
            String.format(formatting, "Wyświetlanie danych wszystkich studentów", "<student>", "student") +
            String.format(formatting, "Wyświetlanie danych studenta", "<student {id studenta}>", "student 1") +
            String.format(formatting, "Wyświetlanie danych wszystkich kursów", "<kurs>", "<kurs>") +
            String.format(formatting, "Wyświetlanie danych kursu", "<kurs {skrocona nazwa}>", "kurs PP");
    private static final String studentFormatting = "%10s%15s%20s%20s%n";
    private static final String courseFormatting = "%-40s%-20s%-20s%n";


    private MainController controller = new MainController();
    private Scanner scanner = new Scanner(System.in);
    private AdminView adminView = new AdminView();

    public void readInput() {
        String input = scanner.nextLine();
        interprateInstruction(input);
    }

    private void interprateInstruction(String input) {
        String[] commands = input.split(commandSeparator);

        try {
            switch (commands[0]) {
                case "student":
                    showAllOrSpecificStudentBasedOnCommand(commands[1]);
                    break;
                case "kurs":
                    showAllOrSpecificCourseBasedOnCommand(commands[1]);
                    break;
                case "pomoc":
                    System.out.println(INSTRUCTION);
                    break;
                case "home":
                    adminView.readInput();
                    return;
                case "quit":
                    return;
                default:
                    System.out.println("Nieznane polecenie - " + commands[0]);
            }
        } catch (Exception e) {
            System.out.println("Operacja nie powiodła się. Sprawdź czy wykonałeś polecenie zgodne z poniższą instrukcją: \n");
            System.out.println(INSTRUCTION);
        }

        readInput();
    }

    private void showAllOrSpecificCourseBasedOnCommand(String command) {
        String courseHeader = String.format("%-40s%-20s%-20s%n", "Pełna nazwa", "Krótka nazwa", "Kod grupy");
        if("".equals(command)){
            List<Course> courses = controller.getAllCourses();
            System.out.println(courseHeader);
            System.out.println(formattedCoursesInfo(courses));
        }else{
            Course course = controller.getCourseByShortname(command);
            if(course == null){
                System.out.println("Nie ma kursu o podanej krótkiej nazwie: " + command);
            }
            System.out.println(courseHeader);
            System.out.println(formattedCourseInfo(course));
        }
    }

    private void showAllOrSpecificStudentBasedOnCommand(String command) {
        String studentHeader = String.format(studentFormatting, "nr indeksu", "imie", "nazwisko", "data urodzin");
        if ("".equals(command)) {
            List<Student> students = controller.getAllStudents();
            System.out.println(studentHeader);
            System.out.println(formattedStudentsInfo(students));
        }else{
            Student student = controller.getStudentById(command);
            if(student == null){
                System.out.println("Nie ma studenta z podanym id: " + command);
                return;
            }
            System.out.println(studentHeader);
            System.out.println(formattedStudentInfo(student));
        }
    }

    private String formattedStudentInfo(Student student) {
        StringBuilder builder = new StringBuilder(student.toString(formatting));
        builder.append("Zapisany na kursy: \n");
        for(Course course : student.getJoinedCourses()){
            builder.append(course.toString(courseFormatting));
        }
        return builder.toString();
    }

    private String formattedStudentsInfo(List<Student> students) {
        StringBuilder builder = new StringBuilder();
        for (Student student : students) {
            builder.append(student.toString(studentFormatting));
        }
        return builder.toString();
    }

    private String formattedCourseInfo(Course course) {
        StringBuilder builder = new StringBuilder(course.toString(courseFormatting));
        System.out.println("Zapisani studenci: \n");
        for(Student student : course.getStudents()){
            builder.append(student.toString(studentFormatting));
        }
        return builder.toString();
    }

    private String formattedCoursesInfo(List<Course> courses) {
        StringBuilder builder = new StringBuilder();
        for (Course course : courses) {
            builder.append(course.toString(courseFormatting));
        }
        return builder.toString();
    }
}
