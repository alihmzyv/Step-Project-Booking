package menu_and_menu_items;

import database.Database;
import entities.BookingAppLogger;
import exceptions.booking_menu_exceptions.BookingMenuException;
import io.Console;

public abstract class MenuItem implements Runnable {
    private final int id;
    private Database database;
    private Console console;
    private BookingAppLogger logger;
    
    public MenuItem(int id) {
        this.id = id;
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

    public BookingAppLogger getLogger() {
        return logger;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setLogger(BookingAppLogger logger) {
        this.logger = logger;
    }
    public boolean isExit() {
        return this instanceof ExitButton;
    }
}
