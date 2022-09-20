package menu_and_menu_items;

public class Exit extends ExitButton {

    //constructors
    public Exit(int id) {
        super(id);
    }


    //methods
    @Override
    public void run() {
        getDatabase().updateLocalDatabase();
        getConsole().println("Exiting the application..");
    }
}
