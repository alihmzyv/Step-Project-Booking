package menu_and_menu_items;

import entities.User;
import exceptions.MenuException;
import exceptions.booking_menu_exceptions.BookingMenuException;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

public class Register extends MenuItem {
    public Register (int id) {
        super(id);
    }


    @Override
    public void run() throws BookingMenuException{
        String name = getConsole().getInput("Enter your name:");
        String surname = getConsole().getInput("Enter your surname:");
        String username = getConsole().getInput("Enter your username:");
        String password;
        while (true) {
            password = getConsole().getInput("Enter your password:");
            if (!Pattern.matches("^(?=.*[\\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", password)){
                System.out.println("""
                        Password should contain 6 characters and at least:
                        one upper-case and one lower-case English letter
                        one digit
                        one special character
                        no space""");
                System.out.println("Try again");
                continue;
            }
            break;
        }
        User userRegistered = new User(name, surname, username, password);
        getDatabase().getUcInMemory().saveUser(userRegistered);
        getLogger().registerInfo(userRegistered);
        System.out.println("Registration was successful!");
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        System.out.println("Logging in...");
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userRegistered, getLogger()).run();
    }
}
