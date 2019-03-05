package app.view;

import app.controller.AdminController;
import app.model.data.OperationNotPermitedException;

import java.util.Arrays;
import java.util.Scanner;

public class AdminView implements View{

    private static final String formatting = "%-45s%-70s%-70s%n";

    private static final String INSTRUCTION = String.format(formatting, "FUNKCJA", "KOMENDA", "PRZYKLAD") +
            String.format(formatting, "Dodawanie danych studenta", "<dodaj studenta {id},{imie},{nazwisko},{data urodzin}>", "dodaj studenta 1,Damian,Wawrzyniak,1997-10-10") +
            String.format(formatting, "Usuwanie danych studenta", "<usun studenta {id}>", "usun studenta 1") +
            String.format(formatting, "Aktualizowanie danych studenta", "<aktualizuj studenta {id},{imie},{nazwisko},{data urodzin}>", "aktualizuj studenta 1,Adam,Wawrzyniak,1997-10-12") +
            String.format(formatting, "Dodawanie danych kursu", "<dodaj kurs {pelna nazwa},{skrocona nazwa},{kod grupy}>", "dodaj kurs Zaawansowane Techniki Programowania,ZTP,ZTP-1") +
            String.format(formatting, "Usuwanie danych kursu", "<usun kurs {kod grupy}>", "usun kurs ZTP-1") +
            String.format(formatting, "Aktualizowanie danych kursu", "<aktualizuj kurs {pelna nazwa},{skrocona nazwa},{kod grupy}>", "aktualizuj kurs Zaawansowane Techniki Programowania Obiektowego,ZTP,ZTP-1") +
            String.format(formatting, "Zapisywanie studenta na kurs", "<zapisz {id studenta},{kod grupy}>", "zapisz 1,ZTP-1") +
            String.format(formatting, "Wypisywanie studenta z kursu", "<wypisz {id studenta},{kod grupy}>", "wypisz 1,ZTP-1");

    private static int studentOrCourseDataStartIndex = 2;
    private static int enrollmentDataStartIndex = 1;
    
    private AdminController controller = new AdminController();
    private Scanner scanner = new Scanner(System.in);
    private MainView mainView = new MainView();
    
    public void readInput(){
        String input = scanner.nextLine();
        interprateInstruction(input);
    }

    private void interprateInstruction(String input) {
        String[] commands = input.split(commandSeparator);
        
        try {
            switch (commands[0]) {
                case "dodaj":
                    addStudentOrCourseBasedOnCommand(commands[1], dataCuttedFromCommandLine(commands, studentOrCourseDataStartIndex));
                    System.out.println("Dodano");
                    break;
                case "aktualizuj":
                    updateStudentOrCourseBasedOnCommand(commands[1], dataCuttedFromCommandLine(commands, studentOrCourseDataStartIndex));
                    System.out.println("Zaktualizowano dane");
                    break;
                case "usun":
                    deleteStudentOrCourseBasedOnCommand(commands[1], dataCuttedFromCommandLine(commands, studentOrCourseDataStartIndex));
                    System.out.println("Usunieto dane");
                    break;
                case "zapisz":
                    controller.enrollStudentToCourse(dataCuttedFromCommandLine(commands, enrollmentDataStartIndex));
                    System.out.println("Zapisano studenta na kurs");
                    break;
                case "wypisz":
                    controller.discardStudentFromCourse(dataCuttedFromCommandLine(commands, enrollmentDataStartIndex));
                    System.out.println("Wypisano studenta z kursu");
                    break;
                case "pomoc":
                    showInstruction();
                    break;
                case "home":
                    mainView.readInput();
                    return;
                case "quit":
                    return;
                default:
                    System.out.println("Nieznane polecenie - "  + commands[0]);
            }
        } catch (OperationNotPermitedException e) {
            System.out.println("Niedozwolona operacja: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Operacja nie powiodła się. Sprawdź czy wykonałeś polecenie zgodne z poniższą instrukcją: \n");
            showInstruction();
        }

        readInput();
    }

    private void deleteStudentOrCourseBasedOnCommand(String command, String data) {
        if("studenta".equals(command)){
            controller.deleteStudent(data);
        }else if("kurs".equals(command)){
            controller.deleteCourse(data);
        }
    }

    private void updateStudentOrCourseBasedOnCommand(String command, String data) {
        if("studenta".equals(command)){
            controller.updateStudent(data);
        }else if("kurs".equals(command)){
            controller.updateCourse(data);
        }
    }

    private void addStudentOrCourseBasedOnCommand(String command, String data) {
        if("studenta".equals(command)){
            controller.createStudent(data);
        }else if("kurs".equals(command)){
            controller.createCourse(data);
        }
    }

    private String dataCuttedFromCommandLine(String[] commands, int dataStartIndex) {
        return String.join(" ", Arrays.copyOfRange(commands, dataStartIndex, commands.length));
    }

    public void showInstruction(){
        System.out.println(INSTRUCTION);
    }
    
    
}
