package menu_items;

import database.Database;
import io.Console;

public class Exit extends ExitButton {
    public Exit(int id) {
        super(id);
    }


    @Override
    public void run() {
        getDatabase().updateLocal();
        System.out.println("Exiting the application..");
    }
}
