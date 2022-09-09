package database;

import controllers.BookingController;
import controllers.FlightController;
import controllers.PassengerController;
import controllers.UserController;
import database.dao.*;
import services.BookingService;
import services.FlightService;
import services.PassengerService;
import services.UserService;

import java.io.File;

public class Database {
    private FlightController fcFile;
    private FlightController fcInMemory;
    private BookingController bcFile;
    private BookingController bcInMemory;
    private UserController ucFile;
    private UserController ucInMemory;
    private PassengerController pcFile;
    private PassengerController pcInMemory;


    public Database() {
        fcFile = new FlightController(
                new FlightService(
                        new DaoFlightFile(new File("src/main/java/database_files/flights.bin"))));
        fcFile.updateAllFlights();
        ucFile = new UserController
                (new UserService(
                        new DaoUserFile(new File("src/main/java/database_files/users.bin"))));
        pcFile = new PassengerController(
                new PassengerService(
                        new DaoPassengerFile(new File("src/main/java/database_files/passengers.bin"))));
        bcFile = new BookingController(
                new BookingService
                        (new DaoBookingFile(new File("src/main/java/database_files/bookings.bin"))),
                ucFile.getService(),
                fcFile.getService(),
                pcFile.getService());
        fcInMemory = new FlightController(new FlightService(new DaoFlightInMemory(fcFile.getAllFlights())));
        ucInMemory = new UserController(new UserService(new DaoUserInMemory(ucFile.getAllUsers())));
        pcInMemory = new PassengerController(new PassengerService(new DaoPassengerInMemory(pcFile.getAllPassengers())));
        bcInMemory = new BookingController(new BookingService(new DaoBookingInMemory(bcFile.getAllBookings())),
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

    public PassengerController getPcInMemory() {
        return pcInMemory;
    }

    public void update() {
        throw new RuntimeException("not impl");
    }
}
