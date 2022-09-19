package database.file.service.booking;

import database.dao.DaoBookingFile;
import database.services.BookingService;
import entities.Booking;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.setAllBookingsTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        List<Booking> bookingsCopy = new ArrayList<>(bookings);
        bookingsCopy.add(randomBooking);
        assertEquals(Optional.of(bookingsCopy), bs.getAllBookings());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertEquals(Optional.of(List.of(randomBooking)), bs.getAllBookings());
    }

    @Test
    void saveTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        Booking randomBooking = Booking.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.saveBooking(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertEquals(Optional.empty(), bs.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertEquals(Optional.of(randomBooking), bs.getBooking(randomBooking.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () ->  bs.getBooking(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.getBooking(Booking.getRandom().getId()));
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
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> bs.getBooking(Booking.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.getBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertFalse(bs.removeBooking(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        Booking randomBooking = Booking.getRandom();
        bs.saveBooking(randomBooking);
        assertTrue(bs.removeBooking(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> bs.removeBooking(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.removeBooking(Booking.getRandom().getId()));
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
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> bs.removeBooking(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.removeBooking(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllBookingsTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.saveAllBookings(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), bs.getAllBookings());
    }

    @Test
    void saveAllBookingsTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        List<Booking> bookings2 = Booking.getRandom(100);
        bs.saveAllBookings(bookings2);
        assertEquals(Optional.of(bookings2), bs.getAllBookings());
    }

    @Test
    void saveAllBookingsTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> bs.saveAllBookings(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertEquals(bs.getAllBookings().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), bs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        BookingService bs = new BookingService(new DaoBookingFile(file));
        assertEquals(1, bs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        BookingService bs = new BookingService(new DaoBookingFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                bs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}