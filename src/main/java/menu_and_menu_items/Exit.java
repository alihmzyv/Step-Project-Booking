package menu_and_menu_items;

public class Exit extends ExitButton {
    public Exit(int id) {
        super(id);
    }


    @Override
    public void run() {
        getDatabase().updateLocalDatabase();
        System.out.println("Exiting the application..");
    }
}
