package menu_and_menu_items;

import database.Database;
import entities.BookingAppLogger;
import exceptions.booking_menu_exceptions.BookingMenuException;
import exceptions.booking_menu_exceptions.WrongMenuItemMenuException;
import helpers.Helper;
import io.Console;

import java.io.*;
import java.util.List;

public class Menu implements Runnable {
    private final Database database;
    private final Console console;
    private final List<MenuItem> menuItems;
    private final String menuText;
    private final BookingAppLogger logger;
    public Menu(Database database, Console console, List<MenuItem> menuItems, BookingAppLogger logger, File menuTextFile) {
        this.database = database;
        this.console = console;
        this.menuItems = menuItems;
        this.logger = logger;
        this.menuItems
                .forEach(menuItem -> {
                    menuItem.setDatabase(this.database);
                    menuItem.setConsole(this.console);
                    menuItem.setLogger(this.logger);
                });
        this.menuText = Helper.getString(menuTextFile);
    }


    @Override
    public void run() {
        while (true) {
            console.println(menuText);
            int menuItemIdInput = console.getPositiveInt("Please select an item from the menu displayed above:");
            try {
                MenuItem menuItemChosen = menuItems.stream()
                        .filter(menuItem -> menuItem.getId() == menuItemIdInput)
                        .findAny()
                        .orElseThrow(() -> new WrongMenuItemMenuException("Wrong menu item. Try again."));
                menuItemChosen.run();
                if (menuItemChosen.isExit()) {
                    break;
                }
            }
            catch (BookingMenuException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
