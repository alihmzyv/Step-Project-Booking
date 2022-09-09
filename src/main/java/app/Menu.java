package app;

import exceptions.WrongMenuItemAppException;
import io.Console;
import io.RealConsole;

import java.io.*;
import java.util.List;

public class Menu extends MenuItem implements Runnable {
    private List<MenuItem> menuItems;
    private File menuTextFile;
    protected Database database;

    public Menu(int id, Database database, File menuTextFile, List<MenuItem> menuItems) {
        super(id);
        this.database = database;
        this.menuTextFile = menuTextFile;
        this.menuItems = menuItems;
        menuItems
                .forEach(menuItem -> menuItem.setDatabase(this.database));
    }

    @Override
    public void run() {
        Console console = new RealConsole();
        while (true) {
            try {
                displayMenu();
                int menuItemIdInput = console.getPositiveInt("Please select an item from the menu displayed above.");
                menuItems.stream()
                        .filter(menuItem -> menuItem.id == menuItemIdInput)
                        .findFirst()
                        .orElseThrow(() -> new WrongMenuItemAppException("Wrong menu item. Try again."))
                        .run();
            }
            catch (WrongMenuItemAppException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }

    public void displayMenu() {
        try(BufferedReader br = new BufferedReader(new FileReader(menuTextFile))) {
            throw new RuntimeException("not impl");
        }
        catch (FileNotFoundException exc) {
            System.out.println("Could not find the main menu text file.");
        }
        catch (IOException exc) {
            System.out.println("Could not read the main menu.");
        }
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
