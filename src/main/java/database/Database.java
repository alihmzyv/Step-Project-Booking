package database;

import database.controllers.BookingController;
import database.controllers.FlightController;
import database.controllers.UserController;
import database.dao.*;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import entities.Booking;
import entities.Flight;
import entities.User;
import exceptions.database_exceptions.LocalDatabaseException;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Database {
    private final FlightController fcFile;
    private final BookingController bcFile;
    private final UserController ucFile;
    private final FlightController fcInMemory;
    private final BookingController bcInMemory;
    private final UserController ucInMemory;


    //constructors
    /*
     * Instantiates the file controllers with the dao implementations corresponding to their ending
     * (e.g., fcInMemory instantiated with FlightService whose dao is DaoInMemory) and instantiates the inMemory
     * controllers with the list of all objects of these controllers if present, or with new Arraylists.
     * * updates the flights of fcInMemory.
     */
    public Database() {
        fcFile = new FlightController(
                new FlightService(
                        new DaoFlightFile(new File("src/main/java/database/local_database_files/flights.bin"))));
        if (fcFile.isEmpty()) {
            fcFile.setAllFlightsTo(Flight.getRandom(100, 1, 168, ChronoUnit.HOURS));
        }
        ucFile = new UserController
                (new UserService(
                        new DaoUserFile(new File("src/main/java/database/local_database_files/users.bin"))));
        bcFile = new BookingController(
                new BookingService
                        (new DaoBookingFile(new File("src/main/java/database/local_database_files/bookings.bin"))),
                ucFile.getService(),
                fcFile.getService()
        );

        fcInMemory = new FlightController(new FlightService(new DaoFlightInMemory(fcFile.getAllFlights().orElseGet(ArrayList::new))));
        fcInMemory.updateAllFlights();
        ucInMemory = new UserController(new UserService(new DaoUserInMemory(ucFile.getAllUsers().orElseGet(ArrayList::new))));
        bcInMemory = new BookingController(new BookingService(new DaoBookingInMemory(bcFile.getAllBookings().orElseGet(ArrayList::new))),
                ucInMemory.getService(),
                fcInMemory.getService()
        );
    }


    //methods
    public FlightController getFcInMemory() {
        return fcInMemory;
    }

    public BookingController getBcInMemory() {
        return bcInMemory;
    }

    public UserController getUcInMemory() {
        return ucInMemory;
    }

    /*
     * Sets the local databases to the in-memory databases and updates the local id counters.
     */
    public void updateLocalDatabase() {
        bcFile.setAllBookingsTo(bcInMemory.getAllBookings().get());
        ucFile.setAllUsersTo(ucInMemory.getAllUsers().get());
        fcFile.setAllFlightsTo(fcInMemory.getAllFlights().get());
        updateLocaleIdCounters();
    }

    /*
     * Creates idCounters.txt file if it does not exist and writes the last idCounter fields of classes
     * * throws LocalDatabaseException whose cause can be IOException due to a problem occurred during writing.
     */
    private void updateLocaleIdCounters() {
        try(PrintWriter pw = new PrintWriter(
                new FileWriter("src/main/java/database/local_database_files/idCounters.txt"))) {
            pw.println(String.format("Flight = %d;", Flight.getIdCounter()));
            pw.println(String.format("Booking = %d;", Booking.getIdCounter()));
            pw.println(String.format("User = %d;", User.getIdCounter()));
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    /*
     * Returns the local idCounter corresponding to the given className.
     * throws LocalDatabaseException whose cause can be IOException due to a problem occurred during writing.
     */
    public static int getIdCounter(String className) {
        File idCountersFile = new File("src/main/java/database/local_database_files/idCounters.txt");
        try {
            if (!idCountersFile.exists()) {
                idCountersFile.createNewFile();
            }
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
        try(BufferedReader ois = new BufferedReader(
                new FileReader("src/main/java/database/local_database_files/idCounters.txt"))) {
            return ois.lines()
                    .filter(line -> line.startsWith(className))
                    .findAny()
                    .map(line -> line.charAt(line.length() - 2))
                    .map(ch -> Integer.parseInt(ch.toString()))
                    .orElse(1);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }
}
