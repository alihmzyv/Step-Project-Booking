package menu_and_menu_items;

public class Exit extends ExitButton {
    public Exit(int id) {
        super(id);
    }


    @Override
    public void run() {
        getDatabase().updateLocalDatabase();
        getConsole().println("Exiting the application..");
    }
}
