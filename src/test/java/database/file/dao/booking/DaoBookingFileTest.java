package database.file.dao.booking;

import database.dao.DaoBookingFile;
import entities.Booking;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoBookingFileTest {
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
    void getAllTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.of(bookings), daoBookingFile.getAll());
    }

    @Test
    void getAllTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.empty(), daoBookingFile.getAll());
    }

    @Test
    void getAllTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoBookingFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.setAllTo(bookings2);
        assertEquals(Optional.of(bookings2), daoBookingFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.setAllTo(bookings2);
        assertEquals(Optional.of(bookings2), daoBookingFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.setAllTo(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        List<Booking> bookingsCopy = new ArrayList<>(bookings);
        bookingsCopy.add(randomBooking);
        assertEquals(Optional.of(bookingsCopy), daoBookingFile.getAll());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        assertEquals(Optional.of(List.of(randomBooking)), daoBookingFile.getAll());
    }

    @Test
    void saveTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        Booking randomBooking = Booking.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.save(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.empty(), daoBookingFile.get(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingFile.get(randomBooking.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoBookingFile.get(Booking.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.get(Booking.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        assertEquals(Optional.empty(), daoBookingFile.get(randomBooking));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingFile.get(randomBooking));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoBookingFile.get(Booking.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.get(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertFalse(daoBookingFile.remove(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.remove(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoBookingFile.remove(Booking.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.remove(Booking.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertFalse(daoBookingFile.remove(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        Booking randomBooking = Booking.getRandom();
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.remove(randomBooking));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoBookingFile.remove(Booking.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.remove(Booking.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.saveAll(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(bookings);
        allBookings.addAll(bookings2);
        assertEquals(Optional.of(allBookings), daoBookingFile.getAll());
    }

    @Test
    void saveAllTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.saveAll(bookings2);
        assertEquals(Optional.of(bookings2), daoBookingFile.getAll());
    }

    @Test
    void saveAllTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        List<Booking> bookings2 = Booking.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoBookingFile.saveAll(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(daoBookingFile.getAll().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), daoBookingFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(1, daoBookingFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoBookingFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}