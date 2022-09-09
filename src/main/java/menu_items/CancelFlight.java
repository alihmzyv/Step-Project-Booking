package menu_items;

import database.Database;
import entities.User;
import io.Console;

public class CancelFlight extends MenuItem {
    private User user;
    private Database database;

    public CancelFlight(int id) {
        super(id);
    }

    public CancelFlight(int id, User user) {
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
