package app.controller;

import app.view.CoursesView;
import app.view.MenuView;
import app.view.StudentView;
import app.view.View;

import java.util.HashMap;

public class MainController implements Controller {

    private static final String formatting = "%-45s%-70s%-70s%n";
    public static final String INSTRUCTION = String.format(formatting, "FUNKCJA", "KOMENDA", "PRZYKLAD") +
            String.format(formatting, "Wyświetlanie danych wszystkich studentów", "<student>", "student") +
            String.format(formatting, "Wyświetlanie danych studenta", "<student {id studenta}>", "student 1") +
            String.format(formatting, "Wyświetlanie danych wszystkich kursów", "<kurs>", "<kurs>") +
            String.format(formatting, "Wyświetlanie danych kursu", "<kurs {skrocona nazwa}>", "kurs PP");


    private static HashMap<String, View> commandViewMapping = new HashMap<>();

    static{
        commandViewMapping.put("kurs", new CoursesView());
        commandViewMapping.put("student", new StudentView());
        commandViewMapping.put("menu", new MenuView());
    }

    public String interact(String input){
        String[] commands = input.split(separator);

        if(commandIsValid(commands[0])){
            return commandViewMapping.get(commands[0]).render(allOrSpecificData(commands));
        }

        return "Nieznane polecenie. Sprawdź czy wykonałeś polecenie zgodne z poniższą instrukcją: \n" + INSTRUCTION;
    }

    private String allOrSpecificData(String[] commands) {
        return commands.length > 1 ? commands[1] : "";
    }

    private boolean commandIsValid(String command) {
        return commandViewMapping.get(command) != null;
    }
}
