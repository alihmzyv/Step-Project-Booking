package database;

import database.controllers.BookingController;
import database.controllers.FlightController;
import database.controllers.UserController;
import database.dao.*;
import database.services.BookingService;
import database.services.FlightService;
import database.services.UserService;
import entities.Flight;
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

    public FlightController getFcInMemory() {
        return fcInMemory;
    }

    public BookingController getBcInMemory() {
        return bcInMemory;
    }

    public UserController getUcInMemory() {
        return ucInMemory;
    }

    public void updateLocalDatabase() {
        bcFile.setAllBookingsTo(bcInMemory.getAllBookings().get());
        ucFile.setAllUsersTo(ucInMemory.getAllUsers().get());
        fcFile.setAllFlightsTo(fcInMemory.getAllFlights().get());
        updateLocaleIdCounters();
    }

    private void updateLocaleIdCounters() {
        try(PrintWriter pw = new PrintWriter(
                new FileWriter("src/main/java/database/local_database_files/idCounters.txt"))) {
            pw.println(String.format("Flight = %d;", fcInMemory.getMaxId()));
            pw.println(String.format("Booking = %d;", bcInMemory.getMaxId()));
            pw.println(String.format("User = %d;", ucInMemory.getMaxId()));
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    public static int getIdCounter(String className) {
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
