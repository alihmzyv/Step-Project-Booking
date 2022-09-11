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
        List<Passenger> passengers = getPassengers(getAvailableCapacity(flightFound.getCapacity()));
        passengers.forEach(passenger ->
                getDatabase().getBcInMemory().saveBooking(new Booking(flightFound, user, passenger)));
        System.out.println("Booking was saved!");
    }

    private Flight getExistingFlight() {
        while (true) {
            try {
                int flightIdInput = getConsole().getPositiveInt("Please enter the id of the flight you want to book:");
                return getDatabase().getFcInMemory().getFlight(flightIdInput)
                        .orElseThrow(() -> new NoSuchFlightException("There is no matching id."));
            }
            catch (NoSuchFlightException exc) {
                getConsole().println(exc.getMessage());
                System.out.println("Try again.");
            }
        }
    }

    private int getAvailableCapacity(int max) {
        while (true) {
            try {
                int numberOfTickets = getConsole().getPositiveInt("Please enter the number of tickets you want to book:");
                if (numberOfTickets > max) {
                    throw new InsufficientCapacityException(String.format("%d is greater than the capacity of flight: %d", numberOfTickets, max));
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
