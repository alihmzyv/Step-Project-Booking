package menu_and_menu_items;

import entities.User;
import exceptions.MenuException;
import exceptions.booking_menu_exceptions.BookingMenuException;
import exceptions.booking_menu_exceptions.NoSuchBookingException;
import exceptions.booking_menu_exceptions.NoSuchUserException;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Login extends MenuItem {

    public Login(int id) {
        super(id);
    }


    public void run() throws BookingMenuException {
        String username = getConsole().getInput("Enter your username:");
        String password = getConsole().getInput("Enter your password:");
        Optional<User> userOptional = getDatabase().getUcInMemory().getUser(username, password);
        if (userOptional.isEmpty()) {
            throw new NoSuchUserException("Wrong username and/or password. Try again.");
        }
        File userMenuTextFile = new File("src/main/java/menus_text_files/userMenu.txt");
        BookingMenu.getBookingUserMenu(getDatabase(), getConsole(), userMenuTextFile, userOptional.get()).run();
        System.out.println("Logging in...");
    }
}
