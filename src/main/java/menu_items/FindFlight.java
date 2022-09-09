package menu_items;

import database.Database;
import entities.Flight;
import entities.IndexedDisplayer;
import io.Console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.List;

public class FindFlight extends MenuItem {
    public FindFlight(int id) {
        super(id);
    }

    @Override
    public void run() {
        getConsole().println("Please enter below the details of flight you want.");
        String fromInput = getConsole().getInput("From:");
        String toInput = getConsole().getInput("To:");
        LocalDate dateInput;
        while (true) {
            try {
                dateInput = LocalDate.parse(
                        getConsole().getInput("Enter your departure date: (e.g. 1.1.2022)"),
                        DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                break;
            }
            catch (DateTimeParseException exc) {
                System.out.println("Please enter date as described. Try again.");
            }
        }
        int freeSeatsInput = getConsole().getPositiveInt("Enter the number of ticket you want to buy:");
        LocalDate finalDateInput = dateInput;
        List<Flight> flightListMatching = getDatabase().getFcInMemory().getAllFlights().stream()
                .filter(flight ->
                        flight.getFrom().getCity().toString().equals(fromInput) &&
                        flight.getTo().getCity().toString().equals(toInput) &&
                        flight.getDate().equals(finalDateInput) &&
                        flight.getCapacity() >= freeSeatsInput)
                .toList();
        IndexedDisplayer.displayIndexed(flightListMatching);
    }
}
