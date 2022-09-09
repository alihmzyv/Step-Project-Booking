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
    private Database database;
    private FindFlight findFlight;
    private Flight flightFound;

    public BookFlight(int id, User user) {
        super(id);
        this.user = user;
    }

    @Override
    public void run() {
        while (true) {
            findFlight.run();
            String input = getConsole().getInput("Do you want to book ?(e.g., yes or no)");
            if (input.equals("no")) {
                break;
            }
            getExistingFlight();
            List<Passenger> passengers = getPassengers(getAvailableCapacity());
            passengers.forEach(passenger ->
                    getDatabase().getBcInMemory().saveBooking(new Booking(flightFound, user, passenger)));
        }
    }

    private void getExistingFlight() {
        while (true) {
            try {
                String flightDesignatorInput = getConsole().getInput("Please enter the designator of the flight you want to book");
                flightFound = getDatabase().getFcInMemory().getAllFlights().stream()
                        .filter(flight -> flight.getFlightDesignator().equals(flightDesignatorInput))
                        .findFirst()
                        .orElseThrow(() -> new NoSuchFlightException("There is no matching designator. Try again."));
            }
            catch (NoSuchFlightException exc) {
                getConsole().println(exc.getMessage());
            }
        }
    }

    private int getAvailableCapacity() {
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
    public void setDatabase(Database database) {
        this.database = database;
        findFlight.setDatabase(database);
    }

    @Override
    public void setConsole(Console console) {

    }
}
