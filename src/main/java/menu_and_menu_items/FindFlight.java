package menu_and_menu_items;

import entities.Flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FindFlight extends MenuItem {
    public FindFlight(int id) {
        super(id);
    }


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
        LocalDate finalDateInput = dateInput;
        int freeSeatsInput = getConsole().getPositiveInt("Enter the number of tickets you want to buy:");
        List<Flight> directFlights = getDatabase().getFcInMemory().getAllFlights().orElseGet(ArrayList::new).stream()
                .filter(flight ->
                        flight.getFrom().getCity().toLowerCase().equals(fromInput) &&
                        flight.getTo().getCity().toLowerCase().equals(toInput) &&
                        flight.getDateOfDeparture().equals(finalDateInput) &&
                        flight.getCapacity() >= freeSeatsInput)
                .collect(Collectors.toList());
        if (directFlights.isEmpty()) {
            System.out.printf("%s%s\n", "\t".repeat(10),"There is no direct flight matching your search.");
        }
        else {
            getConsole().printAsTable("DIRECT FLIGHTS",
                    List.of("ID", "FLIGHT", "FROM", "TO", "TIME OF DEPARTURE", "TIME OF LANDING", "FLIGHT DURATION"),
                    directFlights,
                    125);
        }
    }
}
