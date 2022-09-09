package menu_items;

import database.Database;
import io.Console;

public abstract class MenuItem implements Runnable{
    private final int id;
    private Database database;
    private Console console;
    
    public MenuItem(int id) {
        this.id = id;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    protected void setConsole(Console console) {
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
