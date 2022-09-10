package menu_items;

import database.Database;
import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import exceptions.InsufficientCapacityException;
import exceptions.NoSuchFlightException;
import io.Console;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookFlight extends MenuItem {
    private User user;

    public BookFlight(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        Flight flightFound = getExistingFlight();
        List<Passenger> passengers = getPassengers(getAvailableCapacity(flightFound));
        passengers.forEach(passenger ->
                getDatabase().getBcInMemory().saveBooking(new Booking(flightFound, user, passenger)));
    }

    private Flight getExistingFlight() {
        while (true) {
            try {
                String flightDesignatorInput = getConsole().getInput("Please enter the designator of the flight you want to book");
                return getDatabase().getFcInMemory().getAllFlights().get().stream()
                        .filter(flight -> flight.getFlightDesignator().equals(flightDesignatorInput))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchFlightException("There is no matching designator. Try again."));
            }
            catch (NoSuchFlightException exc) {
                getConsole().println(exc.getMessage());
            }
        }
    }

    private int getAvailableCapacity(Flight flightFound) {
        while (true) {
            try {
                int numberOfTickets = getConsole().getPositiveInt("Please enter the number of ticket you want to book.");
                if (numberOfTickets > flightFound.getCapacity()) {
                    throw new InsufficientCapacityException(String.format("%d is greater than the capacity of flight: %d", numberOfTickets, flightFound.getCapacity()));
                }
                return numberOfTickets;
            }
            catch (InsufficientCapacityException exc) {
                getConsole().println(exc.getMessage());
            }
        }
    }

    private List<Passenger> getPassengers(int countBooked) {
        return IntStream.rangeClosed(1, countBooked)
                .mapToObj(i -> {
                    System.out.printf("Enter the info about passenger %d\n", i);
                    return getPassenger();
                })
                .collect(Collectors.toList());
    }

    private Passenger getPassenger() {
        String name = getConsole().getInput("Please enter name:");
        String surname = getConsole().getInput("Please enter surname:");
        return new Passenger(name, surname);
    }
}
