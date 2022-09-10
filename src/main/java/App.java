import database.Database;
import entities.Flight;
import menu_items.*;

import java.io.File;
import java.util.List;

public class App implements Runnable {
    @Override
    public void run() {
        //main Menu
        Database database = new Database();
        database.updateIdCounters();
        if (database.getFcFile().isEmpty()) {
            database.getFcFile().setAllFlights(Flight.getFlights(20));
        }
        else {
            database.getFcFile().updateAllFlights();
        }
        database.updateFcMemory();

        File mainMenuTextFile = new File("src/main/java/menus_text_files/menu.txt");
        List<MenuItem> mainMenuItems = List.of(new Login(1),
                new Register(2),
                new DisplayAllFlights(3),
                new DisplayFlight(4),
                new FindFlight(5),
                new Exit(6));
        Menu mainMenu = new Menu(database, mainMenuTextFile, mainMenuItems);
        mainMenu.run();
    }
}
