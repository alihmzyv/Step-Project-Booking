package menu_and_menu_items;

import database.Database;
import entities.BookingLogger;
import exceptions.booking_menu_exceptions.BookingMenuException;
import io.Console;

public abstract class MenuItem {
    private final int id;
    private Database database;
    private Console console;
    private BookingLogger logger;
    
    public MenuItem(int id) {
        this.id = id;
    }
    public abstract void run() throws BookingMenuException;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setLogger(BookingLogger logger) {
        this.logger = logger;
    }

    public int getId() {
        return id;
    }

    public Console getConsole() {
        return console;
    }

    public Database getDatabase() {
        return database;
    }

    public BookingLogger getLogger() {
        return logger;
    }
}
