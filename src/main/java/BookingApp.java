import database.Database;
import entities.Flight;
import exceptions.MenuException;
import io.Console;
import io.RealConsole;
import menu_and_menu_items.*;

import java.io.File;
import java.util.List;

public class BookingApp implements Runnable {

    BookingMenu mainMenu;

    BookingApp() {
        Database database = new Database();
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
        Console console = new RealConsole();

        this.mainMenu = new BookingMenu(database, console, mainMenuItems, mainMenuTextFile);
    }
    @Override
    public void run() {
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
