package menu_and_menu_items;

import entities.Booking;
import entities.Flight;
import entities.Passenger;
import entities.User;
import exceptions.booking_menu_exceptions.InsufficientCapacityException;
import exceptions.booking_menu_exceptions.NoSuchFlightException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BookFlight extends MenuItem {
    private final User user;

    public BookFlight(int id, User user) {
        super(id);
        this.user = user;
    }

    public void run() throws NoSuchFlightException, InsufficientCapacityException {
        Flight flightFound = getExistingFlight();
        List<Passenger> passengers = getPassengers(getAvailableCapacity(flightFound.getCapacity()));
        passengers.forEach(passenger -> {
            Booking bookingMade = new Booking(flightFound, user, passenger);
            getLogger().bookingInfo(user, bookingMade);
            getDatabase().getBcInMemory().saveBooking(bookingMade);
        });
        getConsole().println("Bookings were made !");
    }

    private Flight getExistingFlight() throws NoSuchFlightException {
        return getDatabase().getFcInMemory()
                .getFlight(getConsole().getPositiveInt("Please enter the id of the flight you want to book:"))
                .orElseThrow(() -> new NoSuchFlightException("There is no matching id."));
    }

    private int getAvailableCapacity(int max) throws InsufficientCapacityException {
        int numberOfTickets = getConsole().getPositiveInt("Please enter the number of tickets you want to book:");
        if (numberOfTickets > max) {
            throw new InsufficientCapacityException("There is no such amount of free seats in this flight.");
        }
        return numberOfTickets;
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
        return new Passenger(getConsole().getInput("Please enter name:"),
                getConsole().getInput("Please enter surname:"));
    }
}
