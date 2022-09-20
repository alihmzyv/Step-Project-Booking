package menu_and_menu_items;

import entities.User;

public class EndSession extends ExitButton {
    private final User user;


    //constructors
    public EndSession(int id, User user) {
        super(id);
        this.user = user;
    }


    //methods
    @Override
    public void run() {
        logLoggingOut();
        getConsole().println("Logging out..");
    }

    private void logLoggingOut() {
        getLogger().logoutInfo(this.user);
    }
}
