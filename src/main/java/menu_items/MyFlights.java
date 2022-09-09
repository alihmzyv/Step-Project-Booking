package menu_items;

import database.Database;
import entities.User;
import io.Console;

public class MyFlights extends MenuItem {
    private User user;
    private Database database;
    public MyFlights(int id) {
        super(id);
    }

    public MyFlights(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {

    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    protected void setConsole(Console console) {

    }
}
