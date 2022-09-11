package menu_items;

import database.Database;
import entities.Flight;
import exceptions.NoSuchFlightException;
import io.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DisplayFlight extends MenuItem {
    public DisplayFlight(int id) {
        super(id);
    }

    @Override
    public void run() {
        Flight flightFound;
        while (true) {
            try {
                int flightIdInput = getConsole().getPositiveInt("Please enter the id of the flight:");
                flightFound = getDatabase().getFcInMemory().getFlight(flightIdInput)
                        .orElseThrow(() -> new NoSuchFlightException("There is no matching id. Try again."));
                System.out.println(String.join(" || ",
                        "Flight", "ID", "From", "To", "Time of Departure",
                        "Time of Landing", "Flight Duration", "Capacity"
                ));
                System.out.println(String.join(" || ",
                        flightFound.toString(), String.valueOf(flightFound.getCapacity())));
                break;
            }
            catch (NoSuchFlightException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
