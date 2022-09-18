package menu_and_menu_items;

import entities.Flight;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.function.Predicate;

public class SearchFlight extends MenuItem {
    public SearchFlight(int id) {
        super(id);
    }



    @Override
    public void run() {
        getConsole().println("Please enter below the details of flight you want.");
        String fromInput = getFromInput();
        String toInput = getToInput();
        LocalDate departureDateInput = getDepartureDateInput();
        int freeSeatsInput = getNumOfTicketsInput();
        Predicate<Flight> filter = flight ->
                flight.getFrom().getCity().equalsIgnoreCase(fromInput) &&
                        flight.getTo().getCity().equalsIgnoreCase(toInput) &&
                        flight.getDateOfDeparture().equals(departureDateInput) &&
                        flight.getCapacity() >= freeSeatsInput;
        getDatabase().getFcInMemory().displayFlights(filter, getConsole());
    }

    private String getFromInput() {
        return getConsole().getStringInput("From:");
    }

    private String getToInput() {
        return getConsole().getStringInput("To:");
    }

    private LocalDate getDepartureDateInput() {
        while (true) {
            try {
                return LocalDate.parse(
                        getConsole().getStringInput("Enter your departure date: (e.g. 1/1/2022)"),
                        DateTimeFormatter.ofPattern("d/M/yyyy"));
            }
            catch (DateTimeParseException exc) {
                getConsole().println("Please enter date as described. Try again.");
            }
        }
    }

    private int getNumOfTicketsInput() {
        return getConsole().getPositiveInt("Enter the number of tickets you want to buy:");
    }
}
