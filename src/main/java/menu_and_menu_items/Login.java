package menu_and_menu_items;

import entities.User;
import exceptions.booking_menu_exceptions.BookingMenuException;
import exceptions.booking_menu_exceptions.NoSuchUserException;
import java.io.File;
import java.util.Optional;

public class Login extends MenuItem {

    public Login(int id) {
        super(id);
    }


    public void run() throws BookingMenuException {
        Optional<User> userOptional = getUser(getUsernameInput(), getPasswordInput());
        if (userOptional.isEmpty()) {
            throw new NoSuchUserException("Wrong username and/or password. Try again.");
        }
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        getConsole().println("Logging in...");
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userOptional.get(), getLogger()).run();
        logLoggingIn(userOptional.get());
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
