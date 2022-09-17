package entities;
import entities.Booking;
import entities.User;
import org.apache.logging.log4j.*;

public class BookingLogger {
    final Logger logger;

    public BookingLogger(Logger logger) {
        this.logger = logger;
    }

    public void loginInfo(User user) {
        logger.info(String.format("%s logged in.", user));
    }

    public void registerInfo(User user) {
        logger.info(String.format("%s registered and logged in.", user));
    }

    public void searchInfo(User user, int id) {
        logger.info(String.format("%s searched for a flight with id: %d.", user, id));
    }

    public void bookingInfo(User user, Booking booking) {
        logger.info(String.format("%s made the booking: %s.", user, booking));
    }

    public void cancelBookingInfo(User user, Booking booking) {
        logger.info(String.format("%s cancelled the booking: %s.", user, booking));
    }

    public void logoutInfo(User user) {
        logger.info(String.format("%s logged out.", user));
    }
}
