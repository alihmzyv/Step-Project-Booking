package menu_items;

import database.Database;
import entities.Flight;
import exceptions.NoSuchFlightException;
import io.Console;

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
                String flightDesignatorInput = getConsole().getInput("Please enter the designator of the flight:");
                flightFound = getDatabase().getFcInMemory().getAllFlights().get().stream()
                        .filter(flight -> flight.getFlightDesignator().equals(flightDesignatorInput))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchFlightException("There is no matching designator. Try again."));
                getConsole().println(flightFound);
                break;
            }
            catch (NoSuchFlightException exc) {
                System.out.println(exc.getMessage());
            }
        }
    }
}
