package database.services.dao_file.booking;

import database.dao.DaoBookingFile;
import database.services.BookingService;
import entities.Booking;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceFileTest {

    List<Booking> bookings = Booking.getRandom(100);
    private final File file = new File("src/test/java/database/services/dao_file/booking/bookings.bin");
    private final File fileNonExisting = new File("src/test/java/database/services/dao_file/booking/none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(bookings);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }


    @Test
    void getAllBookingsTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.of(bookings), fs.getAllBookings());
    }

    @Test
    void getAllBookingsTest2() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), fs.getAllBookings());
    }

    @Test
    void getAllBookingsTest3() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getAllBookings);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllBookingsToTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), fs.getAllBookings());
    }

    @Test
    void setAllBookingsToTest2() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), fs.getAllBookings());
    }

    @Test
    void setAllBookingsToTest3() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.setAllBookingsTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        List<Booking> bookingsCopy = new ArrayList<>(bookings);
        bookingsCopy.add(randomBooking);
        assertEquals(Optional.of(bookingsCopy), fs.getAllBookings());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        assertEquals(Optional.of(List.of(randomBooking)), fs.getAllBookings());
    }

    @Test
    void saveTest3() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        Booking randomBooking = Booking.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveBooking(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), fs.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), fs.getBooking(randomBooking.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), fs.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getBooking(Booking.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        assertEquals(Optional.empty(), fs.getBooking(randomBooking));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), fs.getBooking(randomBooking));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), fs.getBooking(Booking.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertFalse(fs.removeBooking(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        assertTrue(fs.removeBooking(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertFalse(fs.removeBooking(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeBooking(Booking.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertFalse(fs.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        fs.saveBooking(randomBooking);
        assertTrue(fs.removeBooking(randomBooking));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertFalse(fs.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removeBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllBookingsTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.saveAllBookings(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), fs.getAllBookings());
    }

    @Test
    void saveAllBookingsTest2() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        fs.saveAllBookings(bookings2);
        assertEquals(Optional.of(bookings2), fs.getAllBookings());
    }

    @Test
    void saveAllBookingsTest3() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveAllBookings(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(fs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        BookingService fs = new BookingService(new DaoBookingFile(file));
        assertEquals(1, fs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        BookingService fs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}