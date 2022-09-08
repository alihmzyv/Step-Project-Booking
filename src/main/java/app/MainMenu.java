package app;


import controllers.FlightController;
import io.Console;
import io.RealConsole;

import java.io.File;

public class MainMenu implements Runnable {

    private Console console = new RealConsole();
    private UserMenu userMenu = new UserMenu();
    private Login login = new Login();
    private Register register = new Register();
    private File menu = new File("src/main/java/menus/menu");



    public void run() {

        while (true) {
            console.printFile(menu);
            int menuItemNum = console.getPositiveInt("Please select an item from the menu displayed above:");

            switch (menuItemNum) {
                case 1 -> login.run();
                case 2 -> register.run();
//                case 3 ->
            }
        }
    }
}
