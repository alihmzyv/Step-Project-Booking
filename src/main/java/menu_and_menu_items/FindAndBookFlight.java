package menu_and_menu_items;

import database.Database;
import exceptions.booking_menu_exceptions.InsufficientCapacityException;
import exceptions.booking_menu_exceptions.NoSuchFlightException;
import io.Console;

public class FindAndBookFlight extends MenuItem {
    private final FindFlight findFlight;
    private final BookFlight bookFlight;

    public FindAndBookFlight(int id, FindFlight findFlight, BookFlight bookFlight) {
        super(id);
        this.findFlight = findFlight;
        this.bookFlight = bookFlight;
    }


    public void run() throws NoSuchFlightException, InsufficientCapacityException {
        findFlight.run();
        String answer = getConsole().getInput("Do you want to book? (e.g. y/n)");
        if (answer.equals("y")) {
            bookFlight.run();
        }
        System.out.println("Going back to menu..");
    }

    @Override
    public void setDatabase(Database database) {
        super.setDatabase(database);
        findFlight.setDatabase(database);
        bookFlight.setDatabase(database);
    }

    @Override
    public void setConsole(Console console) {
        super.setConsole(console);
        findFlight.setConsole(console);
        bookFlight.setConsole(console);
    }
}
