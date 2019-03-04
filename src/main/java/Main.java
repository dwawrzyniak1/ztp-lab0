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

        System.out.println("Przełączenie trybu na admin - wpisz <admin> | Powrót do strony głównej - wpisz <home> | Wyjście z aplikacji - wpisz <quit>");

        while(!(input = scanner.nextLine()).equals("quit")){
            if(changeModeCommandDetected(input)){
                controller = changeMode(input);
                printModeInstruction(firstCommand(input));
            }else{
                System.out.println(controller.interact(input));
            }
        }
    }

    private static boolean changeModeCommandDetected(String input) {
        return commandControllerMapping.get(firstCommand(input)) != null;
    }

    private static Controller changeMode(String input) {
        return commandControllerMapping.get(firstCommand(input));
    }

    private static void printModeInstruction(String mode) {
        switch (mode){
            case "admin":
                System.out.println("PANEL ADMINISTRATORA");
                System.out.println("Wazne! Uzywaj formatu danych zgodnego z przykladem.");
                System.out.println(AdminController.INSTRUCTION);
                break;
            case "home":
                System.out.println("STRONA GŁÓWNA");
                System.out.println(MainController.INSTRUCTION);
                break;
        }
    }

    private static String firstCommand(String input) {
        return input.split(" ")[0];
    }
}
