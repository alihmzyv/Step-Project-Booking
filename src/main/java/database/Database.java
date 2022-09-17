package database;

import database.controllers.BookingController;
import database.controllers.FlightController;
import database.controllers.PassengerController;
import database.controllers.UserController;
import database.dao.*;
import database.services.BookingService;
import database.services.FlightService;
import database.services.PassengerService;
import database.services.UserService;
import entities.Flight;

import java.io.*;
import java.util.ArrayList;

public class Database {
    private final FlightController fcFile;
    private final BookingController bcFile;
    private final UserController ucFile;
    private final PassengerController pcFile;
    private final FlightController fcInMemory;
    private final BookingController bcInMemory;
    private final UserController ucInMemory;
    private final PassengerController pcInMemory;


    public Database() {
        fcFile = new FlightController(
                new FlightService(
                        new DaoFlightFile(new File("src/main/java/database/local_database_files/flights.bin"))));
        if (fcFile.isEmpty()) {
            fcFile.setAllFlightsTo(Flight.getRandom(100));
        }
        ucFile = new UserController
                (new UserService(
                        new DaoUserFile(new File("src/main/java/database/local_database_files/users.bin"))));
        pcFile = new PassengerController(
                new PassengerService(
                        new DaoPassengerFile(new File("src/main/java/database/local_database_files/passengers.bin"))));
        bcFile = new BookingController(
                new BookingService
                        (new DaoBookingFile(new File("src/main/java/database/local_database_files/bookings.bin"))),
                ucFile.getService(),
                fcFile.getService(),
                pcFile.getService());

        fcInMemory = new FlightController(new FlightService(new DaoFlightInMemory(fcFile.getAllFlights().orElseGet(ArrayList::new))));
        fcInMemory.updateAllFlights();
        ucInMemory = new UserController(new UserService(new DaoUserInMemory(ucFile.getAllUsers().orElseGet(ArrayList::new))));
        pcInMemory = new PassengerController(new PassengerService(new DaoPassengerInMemory(pcFile.getAllPassengers().orElseGet(ArrayList::new))));
        bcInMemory = new BookingController(new BookingService(new DaoBookingInMemory(bcFile.getAllBookings().orElseGet(ArrayList::new))),
                ucInMemory.getService(),
                fcInMemory.getService(),
                pcInMemory.getService());
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
        pcFile.setAllPassengersTo(pcInMemory.getAllPassengers().get());
        updateLocaleIdCounters();
    }

    private void updateLocaleIdCounters() {
        try(PrintWriter pw = new PrintWriter(
                new FileWriter("src/main/java/database/local_database_files/idCounters.txt"))) {
            pw.println(String.format("Flight = %d;", fcInMemory.getMaxId()));
            pw.println(String.format("Booking = %d;", bcInMemory.getMaxId()));
            pw.println(String.format("User = %d;", ucInMemory.getMaxId()));
            pw.println(String.format("Passenger = %d;", pcInMemory.getMaxId()));
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
        catch (FileNotFoundException e) {
            System.out.printf("Could not find idCounter file at %s\n",
                    "src/main/java/database/local_database_files/idCounters.txt");
            System.exit(1);
            return - 1;
        }
        catch (IOException exc) {
            System.out.printf("Could not read idCounter file at %s\n",
                    "src/main/java/database/local_database_files/idCounters.txt");
            System.exit(1);
            return - 1;
        }
    }
}
