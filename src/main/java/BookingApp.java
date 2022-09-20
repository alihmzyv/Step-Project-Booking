import database.Database;
import entities.BookingAppLogger;
import exceptions.BookingAppException;
import exceptions.menu_exceptions.MenuException;
import io.Console;
import io.RealConsole;
import menu_and_menu_items.*;
import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class BookingApp implements Runnable {
    BookingMenu mainMenu;
    BookingApp() {
        Database database = new Database();
        File mainMenuTextFile = new File("src/main/java/menus_text_files/menu.txt");
        List<MenuItem> mainMenuItems = List.of(new Login(1),
                new Register(2),
                new DisplaySomeFlights(3, Duration.ofHours(24)),
                new DisplaySomeFlights(4, Duration.ofDays(7)),
                new DisplayFlight(5),
                new SearchFlight(6),
                new Exit(7));
        Console console = new RealConsole();
        BookingAppLogger logger = new BookingAppLogger(LogManager.getLogger("BookingApp"));
        this.mainMenu = new BookingMenu(database, console, mainMenuItems, mainMenuTextFile, logger);
    }
    @Override
    public void run() {
        while (true) {
            try {
                mainMenu.run();
                break;
            }
            catch (BookingAppException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
