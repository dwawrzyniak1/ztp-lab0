package app.controller;

import app.model.CRUDFacade;
import app.model.CourseFacade;
import app.model.Service;
import app.model.StudentFacade;
import app.model.data.OperationNotPermitedException;

import java.util.Arrays;

public class AdminController implements Controller {

    private static final String formatting = "%-45s%-70s%-70s%n";

    public static final String INSTRUCTION = String.format(formatting, "FUNKCJA", "KOMENDA", "PRZYKLAD") +
            String.format(formatting, "Dodawanie danych studenta", "<dodaj studenta {id},{imie},{nazwisko},{data urodzin}>", "dodaj studenta 1,Damian,Wawrzyniak,1997-10-10") +
            String.format(formatting, "Usuwanie danych studenta", "<usun studenta {id}>", "usun studenta 1") +
            String.format(formatting, "Aktualizowanie danych studenta", "<aktualizuj studenta {id},{imie},{nazwisko},{data urodzin}>", "aktualizuj studenta 1,Adam,Wawrzyniak,1997-10-12") +
            String.format(formatting, "Dodawanie danych kursu", "<dodaj kurs {pelna nazwa},{skrocona nazwa},{kod grupy}>", "dodaj kurs Zaawansowane Techniki Programowania,ZTP,ZTP-1") +
            String.format(formatting, "Usuwanie danych kursu", "<usun kurs {kod grupy}>", "usun kurs ZTP-1") +
            String.format(formatting, "Aktualizowanie danych kursu", "<aktualizuj kurs {pelna nazwa},{skrocona nazwa},{kod grupy}>", "aktualizuj kurs Zaawansowane Techniki Programowania Obiektowego,ZTP,ZTP-1") +
            String.format(formatting, "Zapisywanie studenta na kurs", "<zapisz {id studenta},{kod grupy}>", "zapisz 1,ZTP-1") +
            String.format(formatting, "Wypisywanie studenta z kursu", "<wypisz {id studenta},{kod grupy}>", "wypisz 1,ZTP-1");


    private static int commandEndIndex = 2;

    private StudentFacade studentFacade = new StudentFacade();
    private CourseFacade courseFacade = new CourseFacade();

    private CRUDFacade facade;

    private Service service = new Service();

    public String interact(String input) {
        String[] commands = input.split(separator);

        setCrudFacade(commands[1]);

        try {
            switch (commands[0]) {
                case "dodaj":
                    facade.create(String.join(" ", Arrays.copyOfRange(commands, commandEndIndex, commands.length)));
                    return "Dodano";
                case "aktualizuj":
                    facade.update(String.join(" ", Arrays.copyOfRange(commands, commandEndIndex, commands.length)));
                    return "Zaktualizowano dane";
                case "usun":
                    facade.delete(String.join(" ", Arrays.copyOfRange(commands, commandEndIndex, commands.length)));
                    return "Usunieto dane";
                case "zapisz":
                    service.enrollStudentToCourse(String.join(" ", Arrays.copyOfRange(commands, 1, commands.length)));
                    return "Zapisano studenta na kurs";
                case "wypisz":
                    service.discardStudentFromCourse(String.join(" ", Arrays.copyOfRange(commands, 1, commands.length)));
                    return "Wypisano studenta z kursu";
                default:
                    return "Nieznane polecenie - " + commands[0];
            }
        } catch (OperationNotPermitedException e) {
            return "Niedozwolona operacja: " + e.getMessage();
        } catch (Exception e) {
            return "Operacja nie powiodła się. Sprawdź czy wykonałeś polecenie zgodne z poniższą instrukcją: \n" + INSTRUCTION;
        }

    }

    private void setCrudFacade(String command) {
        switch (command) {
            case "studenta":
                facade = studentFacade;
                break;
            case "kurs":
                facade = courseFacade;
                break;
            default:
                break;
        }
    }
}
