package database.file.dao.booking;

import database.dao.DaoBookingFile;
import entities.Booking;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoBookingFileTest {
    List<Booking> randomBookings = Booking.getRandom(100);
    Booking randomBooking = Booking.getRandom();
    private final File file = new File("src/test/java/database/file/files/bookings.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomBookings);
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
    void getAllTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.of(randomBookings), daoBookingFile.getAll());
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
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoBookingFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.saveAll(bookings2);
        List<Booking> allBookings = new ArrayList<>();
        allBookings.addAll(randomBookings);
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
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.saveAll(randomBookings));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.setAll(bookings2);
        assertEquals(Optional.of(bookings2), daoBookingFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        List<Booking> bookings2 = Booking.getRandom(100);
        daoBookingFile.setAll(bookings2);
        assertEquals(Optional.of(bookings2), daoBookingFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        List<Booking> bookings2 = Booking.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.setAll(bookings2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertTrue(daoBookingFile.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertFalse(daoBookingFile.isPresent());
    }

    @Test
    void isPresentTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertFalse(daoBookingFile.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertTrue(daoBookingFile.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.isPresent());
        assertTrue(daoBookingFile.getAll().get().stream()
                .anyMatch(booking -> booking.equals(randomBooking)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.isPresent());
        assertTrue(daoBookingFile.getAll().get().stream()
                .anyMatch(booking -> booking.equals(randomBooking)));
    }

    @Test
    void saveTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.save(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.empty(), daoBookingFile.get(randomBooking.getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        daoBookingFile.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingFile.get(randomBooking.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoBookingFile.get(randomBooking.getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.get(randomBooking.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertEquals(Optional.empty(), daoBookingFile.get(randomBooking));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        daoBookingFile.save(randomBooking);
        assertEquals(Optional.of(randomBooking), daoBookingFile.get(randomBooking));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoBookingFile.get(randomBooking));
    }

    @Test
    void getWithObjTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.get(randomBooking));
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
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.remove(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoBookingFile.remove(randomBooking.getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.remove(randomBooking.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertFalse(daoBookingFile.remove(randomBooking));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        daoBookingFile.save(randomBooking);
        assertTrue(daoBookingFile.remove(randomBooking));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoBookingFile.remove(randomBooking));
    }

    @Test
    void removeWithObjTest4() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoBookingFile.remove(randomBooking));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertTrue(daoBookingFile.isPresent());
        assertEquals(daoBookingFile.getAll().get().stream()
                .mapToInt(Booking::getId)
                .max()
                .getAsInt(), daoBookingFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoBookingFile daoBookingFile = new DaoBookingFile(file);
        assertTrue(daoBookingFile.isEmpty());
        assertEquals(1, daoBookingFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoBookingFile daoBookingFile = new DaoBookingFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoBookingFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}