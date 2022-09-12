package menu_and_menu_items;

public class EndSession extends ExitButton {


    public EndSession(int id) {
        super(id);
    }

    @Override
    public void run() {
        System.out.println("Logging out..");
    }
}
