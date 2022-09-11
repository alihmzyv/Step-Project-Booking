package menu_items;

import entities.Flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Flight> matchingFlights = getDatabase().getFcInMemory().getAllFlights().get().stream()
                .filter(flight ->
                        flight.getFrom().getCity().toLowerCase().equals(fromInput) &&
                        flight.getTo().getCity().toLowerCase().equals(toInput) &&
                        flight.getDateOfDeparture().equals(finalDateInput) &&
                        flight.getCapacity() >= freeSeatsInput)
                .collect(Collectors.toList());
        getConsole().printAsTable(List.of("ID", "Flight", "From", "To", "Time of Departure", "Time of Landing", "Flight Duration"),
                matchingFlights,
                100);
    }
}
