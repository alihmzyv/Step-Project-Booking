package entities;
import org.apache.logging.log4j.*;

public class BookingAppLogger {
    private final Logger logger;

    public BookingAppLogger(Logger logger) {
        this.logger = logger;
    }

    public void loginInfo(User user) {
        logger.info(String.format("%s logged in.", user.getUsername()));
    }

    public void registerInfo(User user) {
        logger.info(String.format("Registered: %s", user));
        logger.info(String.format("%s logged in.", user.getUsername()));
    }

    public void searchInfo(User user, int id) {
        logger.info(String.format("%s searched for a flight with id: %d.", user.getUsername(), id));
    }

    public void bookingInfo(User user, Booking booking) {
        logger.info(String.format("%s made the booking: %s.", user.getUsername(), booking));
    }

    public void cancelBookingInfo(User user, Booking booking) {
        logger.info(String.format("%s cancelled the booking: %s.", user.getUsername(), booking));
    }

    public void logoutInfo(User user) {
        logger.info(String.format("%s logged out.", user.getUsername()));
    }
}
