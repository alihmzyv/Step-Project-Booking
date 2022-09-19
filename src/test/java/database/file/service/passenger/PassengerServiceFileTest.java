package database.file.service.passenger;

import database.dao.DaoPassengerFile;
import database.services.PassengerService;
import entities.Passenger;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PassengerServiceFileTest {

    List<Passenger> passengers = Passenger.getRandom(100);
    private final File file = new File("src/test/java/database/file/files/passengers.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(passengers);
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
    void getAllPassengersTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.of(passengers), ps.getAllPassengers());
    }

    @Test
    void getAllPassengersTest2() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.empty(), ps.getAllPassengers());
    }

    @Test
    void getAllPassengersTest3() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                ps::getAllPassengers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllPassengersToTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        ps.setAllPassengersTo(passengers2);
        assertEquals(Optional.of(passengers2), ps.getAllPassengers());
    }

    @Test
    void setAllPassengersToTest2() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        ps.setAllPassengersTo(passengers2);
        assertEquals(Optional.of(passengers2), ps.getAllPassengers());
    }

    @Test
    void setAllPassengersToTest3() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.setAllPassengersTo(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        List<Passenger> passengersCopy = new ArrayList<>(passengers);
        passengersCopy.add(randomPassenger);
        assertEquals(Optional.of(passengersCopy), ps.getAllPassengers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        assertEquals(Optional.of(List.of(randomPassenger)), ps.getAllPassengers());
    }

    @Test
    void saveTest3() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        Passenger randomPassenger = Passenger.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.savePassenger(randomPassenger));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.empty(), ps.getPassenger(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        assertEquals(Optional.of(randomPassenger), ps.getPassenger(randomPassenger.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> ps.getPassenger(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.getPassenger(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        assertEquals(Optional.empty(), ps.getPassenger(randomPassenger));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        assertEquals(Optional.of(randomPassenger), ps.getPassenger(randomPassenger));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> ps.getPassenger(Passenger.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.getPassenger(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertFalse(ps.removePassenger(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        assertTrue(ps.removePassenger(randomPassenger.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> ps.removePassenger(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.removePassenger(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertFalse(ps.removePassenger(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        ps.savePassenger(randomPassenger);
        assertTrue(ps.removePassenger(randomPassenger));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> ps.removePassenger(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.removePassenger(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllPassengersTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        ps.saveAllPassengers(passengers2);
        List<Passenger> allPassengers = new ArrayList<>();
        allPassengers.addAll(passengers);
        allPassengers.addAll(passengers2);
        assertEquals(Optional.of(allPassengers), ps.getAllPassengers());
    }

    @Test
    void saveAllPassengersTest2() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        ps.saveAllPassengers(passengers2);
        assertEquals(Optional.of(passengers2), ps.getAllPassengers());
    }

    @Test
    void saveAllPassengersTest3() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> ps.saveAllPassengers(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertEquals(ps.getAllPassengers().get().stream()
                .mapToInt(Passenger::getId)
                .max()
                .getAsInt(), ps.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        PassengerService ps = new PassengerService(new DaoPassengerFile(file));
        assertEquals(1, ps.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        PassengerService ps = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                ps::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}