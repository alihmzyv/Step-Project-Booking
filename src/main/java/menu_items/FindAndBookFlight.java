package menu_items;

import database.Database;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import io.Console;

import java.util.List;

public class FindAndBookFlight extends MenuItem {
    private FindFlight findFlight;
    private BookFlight bookFlight;

    public FindAndBookFlight(int id, FindFlight findFlight, BookFlight bookFlight) {
        super(id);
        this.findFlight = findFlight;
        this.bookFlight = bookFlight;
    }


    @Override
    public void run() {
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
