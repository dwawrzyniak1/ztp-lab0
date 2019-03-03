import app.controller.Controller;
import app.controller.MainController;
import app.controller.AdminController;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    private static HashMap<String, Controller> commandControllerMapping = new HashMap<>();

    static {
        commandControllerMapping.put("admin", new AdminController());
        commandControllerMapping.put("home", new MainController());
    }

    public static void main(String[] args) {
        Controller controller = new MainController();
        Scanner scanner = new Scanner(System.in);
        String input;

        while(!(input = scanner.nextLine()).equals("quit")){
            if(commandControllerMapping.get(firstCommand(input)) != null){
                controller = commandControllerMapping.get(firstCommand(input));

            }else{
                System.out.println(controller.interact(input));
            }
        }
    }

    private static String firstCommand(String input) {
        return input.split(" ")[0];
    }
}
