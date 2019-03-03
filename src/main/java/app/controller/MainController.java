package app.controller;

import app.view.CoursesView;
import app.view.MenuView;
import app.view.StudentView;
import app.view.View;

import java.util.HashMap;

public class MainController implements Controller {

    private static HashMap<String, View> commandViewMapping = new HashMap<>();

    static{
        commandViewMapping.put("kurs", new CoursesView());
        commandViewMapping.put("student", new StudentView());
        commandViewMapping.put("menu", new MenuView());
    }

    public String interact(String input){
        String[] commands = input.split(separator);

        return commandViewMapping.get(commands[0]).render(commands.length > 1 ? commands[1] : "");
    }
}
