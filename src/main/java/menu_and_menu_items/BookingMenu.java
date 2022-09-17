package menu_and_menu_items;

import database.Database;
import entities.BookingAppLogger;
import entities.User;
import io.Console;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class BookingMenu extends Menu {

    public BookingMenu(Database database, Console console, List<MenuItem> menuItems, File menuTextFile, BookingAppLogger logger) {
        super(database, console, menuItems, logger, menuTextFile);
    }


    public static BookingMenu getBookingUserMenu(Database database, Console console, File userMenuTextFile, User user, BookingAppLogger logger) {
        List<MenuItem> userMenuItems = List.of(new DisplaySomeFlights(1, Duration.ofHours(24)),
                new DisplaySomeFlights(2, Duration.ofDays(7)),
                new DisplayFlight(3, user),
                new SearchAndBookFlight(4, new SearchFlight(1), new BookFlight(2, user)),
                new CancelBooking(5, user),
                new MyBookings(6, user),
                new EndSession(7, user));
        return new BookingMenu(database, console, userMenuItems, userMenuTextFile, logger);
    }
}
