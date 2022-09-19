package database.file.dao.flight;

import database.dao.DaoFlightFile;
import entities.Flight;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoFlightFileTest {

    List<Flight> flights = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
    private final File file = new File("src/test/java/database/file/files/flights.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(flights);
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
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.of(flights), daoFlightFile.getAll());
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoFlightFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.setAllTo(flights2);
        assertEquals(Optional.of(flights2), daoFlightFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.setAllTo(flights2);
        assertEquals(Optional.of(flights2), daoFlightFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.setAllTo(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        List<Flight> flightsCopy = new ArrayList<>(flights);
        flightsCopy.add(randomFlight);
        assertEquals(Optional.of(flightsCopy), daoFlightFile.getAll());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        assertEquals(Optional.of(List.of(randomFlight)), daoFlightFile.getAll());
    }

    @Test
    void saveTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.save(randomFlight));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(Optional.empty(), daoFlightFile.get(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightFile.get(randomFlight.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoFlightFile.get(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.get(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        assertEquals(Optional.empty(), daoFlightFile.get(randomFlight));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        assertEquals(Optional.of(randomFlight), daoFlightFile.get(randomFlight));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoFlightFile.get(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void getWithObjTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.get(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.remove(randomFlight.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS).getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertFalse(daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        Flight randomFlight = Flight.getRandom(1, 168, ChronoUnit.HOURS);
        daoFlightFile.save(randomFlight);
        assertTrue(daoFlightFile.remove(randomFlight));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
    }

    @Test
    void removeWithObjTest4() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.remove(Flight.getRandom(1, 168, ChronoUnit.HOURS)));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        List<Flight> flights2 = Flight.getRandom(100, 1, 168, ChronoUnit.HOURS);
        daoFlightFile.saveAll(flights2);
        List<Flight> allFlights = new ArrayList<>();
        allFlights.addAll(flights);
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
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoFlightFile.saveAll(flights2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(daoFlightFile.getAll().get().stream()
                .mapToInt(Flight::getId)
                .max()
                .getAsInt(), daoFlightFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoFlightFile daoFlightFile = new DaoFlightFile(file);
        assertEquals(1, daoFlightFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoFlightFile daoFlightFile = new DaoFlightFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoFlightFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}