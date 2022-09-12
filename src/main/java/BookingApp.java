import database.Database;
import entities.Flight;
import exceptions.MenuException;
import menu_and_menu_items.*;

import java.io.File;
import java.util.List;

public class BookingApp implements Runnable {
    @Override
    public void run() {
        //main Menu
        Database database = new Database();
        database.updateIdCounters();
        if (database.getFcFile().isEmpty()) {
            database.getFcFile().setAllFlights(Flight.getRandom(10));
        }
        else {
            database.getFcFile().updateAllFlights();
        }
        database.updateFcInMemory();
        File mainMenuTextFile = new File("src/main/java/menus_text_files/menu.txt");
        List<MenuItem> mainMenuItems = List.of(new Login(1),
                new Register(2),
                new DisplayAllFlights(3),
                new DisplayFlight(4),
                new FindFlight(5),
                new Exit(6));
        Menu mainMenu = new Menu(database, mainMenuTextFile, mainMenuItems);
        while (true) {
            try {
                mainMenu.run();
                break;
            }
            catch (MenuException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
