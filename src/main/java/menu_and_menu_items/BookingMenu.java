package menu_and_menu_items;

import database.Database;
import entities.BookingLogger;
import entities.User;
import io.Console;

import java.io.File;
import java.util.List;

public class BookingMenu extends Menu {

    public BookingMenu(Database database, Console console, List<MenuItem> menuItems, File menuTextFile, BookingLogger logger) {
        super(database, console, menuItems, menuTextFile, logger);
    }


    public static BookingMenu getBookingUserMenu(Database database, Console console, File userMenuTextFile, User user, BookingLogger logger) {
        List<MenuItem> userMenuItems = List.of(new DisplayAllFlights(1),
                new DisplayFlight(2, user),
                new FindAndBookFlight(3, new FindFlight(1), new BookFlight(2, user)),
                new CancelFlight(4, user),
                new MyFlights(5, user),
                new EndSession(6, user));
        return new BookingMenu(database, console, userMenuItems, userMenuTextFile, logger);
    }
}
