package menu_and_menu_items;

import entities.User;
import exceptions.booking_menu_exceptions.BookingMenuException;
import helpers.Helper;

import java.io.File;
import java.util.regex.Pattern;

public class Register extends MenuItem {
    public Register (int id) {
        super(id);
    }


    @Override
    public void run() throws BookingMenuException{
        String name = getNameInput();
        String surname = getSurnameInput();
        String username = getUsernameInput();
        String password = getPasswordInput();
        User userRegistered = new User(name, surname, username, password);
        saveUser(userRegistered);
        logRegistration(userRegistered);
        getConsole().println("Registration was successful!");
        getConsole().println("Logging in...");
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userRegistered, getLogger()).run();
    }

    private String getNameInput() {
        return getConsole().getStringInput("Enter your name:");
    }

    private String getSurnameInput() {
        return getConsole().getStringInput("Enter your surname:");
    }

    private String getUsernameInput() {
        while (true) {
            String username = getConsole().getStringInput("Enter your username:");
            if (!getDatabase().getUcInMemory().contains(username)) {
                return username;
            }
            getConsole().println("Username is already taken. Please try another username.");
        }
    }

    private String getPasswordInput() {
        while (true) {
            String passwordInput = getConsole().getStringInput("Enter your password:");
            if (Helper.isStrongPassword(passwordInput)){
                return passwordInput;
            }
            getConsole().println("""
                        Password should contain 6 characters and at least:
                        one upper-case and one lower-case English letter
                        one digit
                        one special character
                        no space""");
            getConsole().println("Try again");
        }
    }

    private void saveUser(User user) {
        getDatabase().getUcInMemory().saveUser(user);
    }

    private void logRegistration(User user) {
        getLogger().registerInfo(user);
    }
}
