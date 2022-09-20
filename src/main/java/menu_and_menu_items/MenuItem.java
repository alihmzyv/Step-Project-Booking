package menu_and_menu_items;

import database.Database;
import entities.BookingAppLogger;
import io.Console;

public abstract class MenuItem implements Runnable {
    private final int id;
    private Database database;
    private Console console;
    private BookingAppLogger logger;


    //constructors
    public MenuItem(int id) {
        this.id = id;
    }

    //getter and setters
    public int getId() {
        return id;
    }
    public Database getDatabase() {
        return database;
    }
    public Console getConsole() {
        return console;
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
