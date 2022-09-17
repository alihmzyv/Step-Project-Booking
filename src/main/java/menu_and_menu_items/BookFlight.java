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


    @Override
    public void run() {
        Flight flightInput = getFlightInput();
        int numOfTicketsInput = getCapacityInput(flightInput.getCapacity());
        List<Passenger> passengersInput = getPassengersInput(numOfTicketsInput);
        passengersInput.forEach(passenger -> {
            Booking bookingMade = new Booking(this.user, flightInput, passenger);
            saveBooking(bookingMade);
            logBooking(bookingMade);
        });
        getConsole().println("Bookings were made !");
    }

    private Flight getFlightInput() {
        return getDatabase().getFcInMemory()
                .getFlight(getConsole().getPositiveInt("Please enter the id of the flight you want to book:"))
                .orElseThrow(() -> new NoSuchFlightException("There is no matching id."));
    }

    private int getCapacityInput(int max) {
        int numberOfTickets = getConsole().getPositiveInt("Please enter the number of tickets you want to book:");
        if (numberOfTickets > max) {
            throw new InsufficientCapacityException("There is no such amount of free seats in this flight.");
        }
        return numberOfTickets;
    }

    private List<Passenger> getPassengersInput(int countBooked) {
        return IntStream.rangeClosed(1, countBooked)
                .mapToObj(i -> {
                    System.out.printf("Enter the info about passenger %d\n", i);
                    return getPassengerInput();
                })
                .collect(Collectors.toList());
    }

    private Passenger getPassengerInput() {
        return new Passenger(getConsole().getStringInput("Please enter name:"),
                getConsole().getStringInput("Please enter surname:"));
    }

    private void saveBooking(Booking bookingMade) {
        getDatabase().getBcInMemory().saveBooking(bookingMade);
    }

    private void logBooking(Booking booking) {
        getLogger().bookingInfo(this.user, booking);
    }
}
