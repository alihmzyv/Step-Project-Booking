package database.file.dao.flight;

import database.dao.DaoFlightFile;
import entities.Flight;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoFlightFileTest {

    List<Flight> randomFlights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
    private final File file = new File("src/test/java/database/file/files/flights.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomFlights);
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
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.of(randomFlights), daoFlightFile.getAll());
    }

    @Test
    void getAllTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.empty(), daoFlightFile.getAll());
    }

    @Test
    void getAllTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoFlightFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.saveAll(flights2);
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(randomFlights);
        allFlights.addAll(flights2);
        assertEquals(Optional.of(allFlights), daoFlightFile.getAll());
    }

    @Test
    void saveAllTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.saveAll(flights2);
        assertEquals(Optional.of(flights2), daoFlightFile.getAll());
    }

    @Test
    void saveAllTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.saveAll(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.setAll(flights2);
        assertEquals(Optional.of(flights2), daoFlightFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.setAll(flights2);
        assertEquals(Optional.of(flights2), daoFlightFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.setAll(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertTrue(daoFlightFile.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.isPresent());
    }

    @Test
    void isPresentTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.isPresent());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertTrue(daoFlightFile.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.isEmpty());
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.isPresent());
        assertTrue(daoFlightFile.getAll().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.isPresent());
        assertTrue(daoFlightFile.getAll().get().stream()
                .anyMatch(flight -> flight.equals(randomFlight)));
    }

    @Test
    void saveTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.save(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.empty(), daoFlightFile.get(randomFlight.getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightFile.get(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoFlightFile.get(randomFlight.getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.get(randomFlight.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.empty(), daoFlightFile.get(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightFile.get(randomFlight));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoFlightFile.get(randomFlight));
    }

    @Test
    void getWithObjTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.get(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.remove(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.remove(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoFlightFile.remove(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.remove(randomFlight.getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.remove(randomFlight));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.remove(randomFlight));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> daoFlightFile.remove(randomFlight));
    }

    @Test
    void removeWithObjTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> daoFlightFile.remove(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertTrue(daoFlightFile.isPresent());
        assertEquals(daoFlightFile.getAll().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), daoFlightFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertTrue(daoFlightFile.isEmpty());
        assertEquals(1, daoFlightFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                daoFlightFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}