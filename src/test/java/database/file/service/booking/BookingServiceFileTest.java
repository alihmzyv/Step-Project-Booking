package database.file.service.booking;

import database.dao.DaoBookingFile;
import database.services.BookingService;
import entities.Booking;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookingServiceFileTest {

    List<Booking> bookings = Booking.getRandom(100);
    private final File file = new File("src/test/java/database/file/files/bookings.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(bookings);
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }

    private void makeEmpty() {
        try {
            new FileOutputStream(file).close();
        }
        catch (IOException exc) {
            throw new LocalDatabaseException(exc);
        }
    }


    @Test
    void getAllBookingsTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.of(bookings), bs.getAllBookings());
    }

    @Test
    void getAllBookingsTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), bs.getAllBookings());
    }

    @Test
    void getAllBookingsTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                bs::getAllBookings);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllBookingsToTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bs.getAllBookings());
    }

    @Test
    void setAllBookingsToTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.setAllBookingsTo(bookings2);
        assertEquals(Optional.of(bookings2), bs.getAllBookings());
    }

    @Test
    void setAllBookingsToTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        List<Booking> bookings2 = Booking.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.setAllBookingsTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertTrue(bs.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertFalse(bs.isPresent());
    }

    @Test
    void isPresentTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertFalse(bs.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertTrue(bs.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertTrue(bs.isPresent());
        assertTrue(bs.getAllBookings().get().stream()
                .anyMatch(booking -> booking.equals(randomBooking)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertTrue(bs.isPresent());
        assertTrue(bs.getAllBookings().get().stream()
                .anyMatch(booking -> booking.equals(randomBooking)));
    }

    @Test
    void saveTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        Booking randomBooking = Booking.getRandom();
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.saveBooking(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        assertEquals(Optional.empty(), bs.getBooking(randomBooking));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bs.getBooking(randomBooking));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> bs.getBooking(Booking.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.getBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertFalse(bs.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertTrue(bs.removeBooking(randomBooking));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> bs.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> bs.removeBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertTrue(bs.isPresent());
        assertEquals(bs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertTrue(bs.isEmpty());
        assertEquals(1, bs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                bs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}