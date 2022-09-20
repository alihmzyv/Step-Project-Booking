package menu_and_menu_items;

import entities.User;
import exceptions.menu_exceptions.MenuException;
import exceptions.database_exceptions.NoSuchUserException;
import java.io.File;
import java.util.Optional;

public class Login extends MenuItem {

    public Login(int id) {
        super(id);
    }


    public void run() throws MenuException {
        Optional<User> userOptional = getUser(getUsernameInput(), getPasswordInput());
        if (userOptional.isEmpty()) {
            throw new NoSuchUserException("Wrong username and/or password. Try again.");
        }
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        getConsole().println("Logging in...");
        logLoggingIn(userOptional.get());
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userOptional.get(), getLogger()).run();
    }

    private String getUsernameInput() {
        return getConsole().getStringInput("Enter your username:");
    }

    private String getPasswordInput() {
        return getConsole().getStringInput("Enter your password:");
    }

    private Optional<User> getUser(String username, String password) {
        return getDatabase().getUcInMemory().getUser(username, password);
    }

    private void logLoggingIn(User user) {
        getLogger().loginInfo(user);
    }
}
