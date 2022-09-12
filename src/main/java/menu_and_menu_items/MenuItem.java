package menu_and_menu_items;

import database.Database;
import exceptions.MenuException;
import io.Console;

public abstract class MenuItem {
    private final int id;
    private Database database;
    private Console console;
    
    public MenuItem(int id) {
        this.id = id;
    }
    public abstract void run() throws MenuException;

    public void setDatabase(Database database) {
        this.database = database;
    }

    public void setConsole(Console console) {
        this.console = console;
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

}
