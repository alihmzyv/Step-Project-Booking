package app;

import java.io.File;
import java.util.List;

public class App implements Runnable {
    @Override
    public void run() {
        //main Menu
        Database database = new Database();
        File userMenuTextFile = new File("src/main/java/menus/userMenu.txt");
        List<MenuItem> userMenuItems = List.of(new DisplayFlights(1),
                new DisplaySpecificFlight(2),
                new BookFlight(3),
                new CancelFlight(4),
                new MyFlights(5),
                new EndSession(6));
        MenuItem userMenu = new Menu(- 1, database, userMenuTextFile, userMenuItems);
        File mainMenuTextFile = new File("src/main/java/menus/menu.txt");
        List<MenuItem> mainMenuItems = List.of(new Login(1),
                new Register(2),
                new DisplayFlights(3),
                new DisplaySpecificFlight(4),
                new FindFlight(5),
                new Exit(6),
                userMenu);
        Menu mainMenu = new Menu(1, database, mainMenuTextFile, mainMenuItems);
    }
}
