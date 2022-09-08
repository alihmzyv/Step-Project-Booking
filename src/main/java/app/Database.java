package app;

import controllers.BookingController;
import controllers.FlightController;
import controllers.PassengerController;
import controllers.UserController;
import dao.*;
import services.BookingService;
import services.FlightService;
import services.PassengerService;
import services.UserService;

import java.io.File;

public class Database {
    private FlightController fcFile;
    private BookingController bcFile;
    private UserController ucFile;
    private PassengerController pcFile;

    public Database() {
        fcFile = new FlightController(
                new FlightService(
                        new DaoFlightFile(
                                new File("src/main/java/database_files/flights.bin"))));
        fcFile.updateAllFlights();
        bcFile = new BookingController(
                new BookingService(
                        new DaoBookingFile(
                                new File("src/main/java/database_files/bookings.bin"))));
        ucFile = new UserController(
                new UserService(
                        new DaoUserFile(
                                new File("src/main/java/database_files/users.bin"))));
        pcFile = new PassengerController(
                new PassengerService(
                        new DaoPassengerFile(
                                new File("src/main/java/database_files/passengers.bin"))));
    }

    public FlightController getFlightControllerInMemory() {
        return new FlightController(new FlightService(new DaoFlightInMemory(fcFile.getAllFlights())));
    }

    public BookingController getBookingControllerInMemory() {
        return new BookingController(new BookingService(new DaoBookingInMemory(bcFile.getAllBookings())));
    }

    public UserController getUserControllerInMemory() {
        return new UserController(new UserService(new DaoUserInMemory(ucFile.getAllUsers())));
    }

    public PassengerController getPassengerControllerInMemory() {
        return new PassengerController(new PassengerService(new DaoPassengerInMemory(pcFile.getAllPassengers())));
    }
}
