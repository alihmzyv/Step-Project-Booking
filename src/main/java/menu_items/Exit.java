package menu_items;

import database.Database;
import io.Console;

public class Exit extends MenuItem {
    private Database database;
    public Exit(int id) {
        super(id);
    }


    @Override
    public void run() {
        System.exit(1);
    }

}
