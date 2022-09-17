package menu_and_menu_items;

import database.Database;
import entities.BookingAppLogger;
import io.Console;

public class SearchAndBookFlight extends MenuItem {
    private final SearchFlight searchFlight;
    private final BookFlight bookFlight;

    public SearchAndBookFlight(int id, SearchFlight searchFlight, BookFlight bookFlight) {
        super(id);
        this.searchFlight = searchFlight;
        this.bookFlight = bookFlight;
    }


    @Override
    public void run() {
        searchFlight.run();
        String answer = getAnswerInput();
        if (answer.equals("y")) {
            bookFlight.run();
        }
        getConsole().println("Going back to menu...");
    }

    @Override
    public void setDatabase(Database database) {
        super.setDatabase(database);
        searchFlight.setDatabase(database);
        bookFlight.setDatabase(database);
    }

    @Override
    public void setConsole(Console console) {
        super.setConsole(console);
        searchFlight.setConsole(console);
        bookFlight.setConsole(console);
    }

    @Override
    public void setLogger(BookingAppLogger logger) {
        super.setLogger(logger);
        searchFlight.setLogger(logger);
        bookFlight.setLogger(logger);
    }

    private String getAnswerInput() {
        return getConsole().getStringInput("Do you want to book? (e.g. y/n)");
    }
}
