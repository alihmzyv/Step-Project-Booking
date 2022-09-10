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
        String fromInput = getConsole().getInput("From:").toLowerCase();
        String toInput = getConsole().getInput("To:").toLowerCase();
        LocalDate dateInput;
        while (true) {
            try {
                dateInput = LocalDate.parse(
                        getConsole().getInput("Enter your departure date: (e.g. 1/1/2022)"),
                        DateTimeFormatter.ofPattern("d/M/yyyy"));
                break;
            }
            catch (DateTimeParseException exc) {
                System.out.println("Please enter date as described. Try again.");
            }
        }
        int freeSeatsInput = getConsole().getPositiveInt("Enter the number of tickets you want to buy:");
        LocalDate finalDateInput = dateInput;
        System.out.println("\t\t\t" + "=".repeat(111));
        System.out.println(String.join(" || ",
                "\t\t\tFlight", "From", "To", "Time of Departure", "Time of Landing", "Flight Duration"));
        System.out.println("\t\t\t" + "-".repeat(111));
        getDatabase().getFcInMemory().getAllFlights().get().stream()
                .filter(flight ->
                        flight.getFrom().getCity().toLowerCase().equals(fromInput) &&
                        flight.getTo().getCity().toLowerCase().equals(toInput) &&
                        flight.getDate().equals(finalDateInput) &&
                        flight.getCapacity() >= freeSeatsInput)
                .forEach(flight -> System.out.println("\t\t\t" + flight));
        System.out.println("\t\t\t" + "=".repeat(111));
    }
}
