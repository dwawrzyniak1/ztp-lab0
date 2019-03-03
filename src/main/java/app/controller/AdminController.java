package app.controller;

import app.model.CRUDFacade;
import app.model.CourseFacade;
import app.model.Service;
import app.model.StudentFacade;

import java.util.Arrays;

public class AdminController implements Controller {

    private static int commandEndIndex = 2;

    private StudentFacade studentFacade = new StudentFacade();
    private CourseFacade courseFacade = new CourseFacade();

    private CRUDFacade facade;

    private Service service = new Service();

    public String interact(String input){
        String[] commands = input.split(separator);

        setCrudFacade(commands[1]);

        switch (commands[0]){
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
                service.enrollStudent(String.join(" ", Arrays.copyOfRange(commands, 1, commands.length)));
                return "Zapisano studenta na kurs";
            default:
                return "Nieznane polecenie - " + commands[0];
        }
    }

    private void setCrudFacade(String command) {
        switch (command){
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
