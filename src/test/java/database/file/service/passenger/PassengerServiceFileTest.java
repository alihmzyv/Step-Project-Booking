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
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.of(passengers), fs.getAllPassengers());
    }

    @Test
    void getAllPassengersTest2() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.empty(), fs.getAllPassengers());
    }

    @Test
    void getAllPassengersTest3() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getAllPassengers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllPassengersToTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        fs.setAllPassengersTo(passengers2);
        assertEquals(Optional.of(passengers2), fs.getAllPassengers());
    }

    @Test
    void setAllPassengersToTest2() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        fs.setAllPassengersTo(passengers2);
        assertEquals(Optional.of(passengers2), fs.getAllPassengers());
    }

    @Test
    void setAllPassengersToTest3() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.setAllPassengersTo(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        List<Passenger> passengersCopy = new ArrayList<>(passengers);
        passengersCopy.add(randomPassenger);
        assertEquals(Optional.of(passengersCopy), fs.getAllPassengers());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        assertEquals(Optional.of(List.of(randomPassenger)), fs.getAllPassengers());
    }

    @Test
    void saveTest3() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        Passenger randomPassenger = Passenger.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.savePassenger(randomPassenger));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertEquals(Optional.empty(), fs.getPassenger(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        assertEquals(Optional.of(randomPassenger), fs.getPassenger(randomPassenger.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> fs.getPassenger(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getPassenger(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        assertEquals(Optional.empty(), fs.getPassenger(randomPassenger));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        assertEquals(Optional.of(randomPassenger), fs.getPassenger(randomPassenger));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> fs.getPassenger(Passenger.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.getPassenger(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertFalse(fs.removePassenger(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        assertTrue(fs.removePassenger(randomPassenger.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> fs.removePassenger(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removePassenger(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertFalse(fs.removePassenger(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        Passenger randomPassenger = Passenger.getRandom();
        fs.savePassenger(randomPassenger);
        assertTrue(fs.removePassenger(randomPassenger));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> fs.removePassenger(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.removePassenger(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllPassengersTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        fs.saveAllPassengers(passengers2);
        List<Passenger> allPassengers = new ArrayList<>();
        allPassengers.addAll(passengers);
        allPassengers.addAll(passengers2);
        assertEquals(Optional.of(allPassengers), fs.getAllPassengers());
    }

    @Test
    void saveAllPassengersTest2() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        fs.saveAllPassengers(passengers2);
        assertEquals(Optional.of(passengers2), fs.getAllPassengers());
    }

    @Test
    void saveAllPassengersTest3() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> fs.saveAllPassengers(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertEquals(fs.getAllPassengers().get().stream()
                .mapToInt(Passenger::getId)
                .max()
                .getAsInt(), fs.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        PassengerService fs = new PassengerService(new DaoPassengerFile(file));
        assertEquals(1, fs.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        PassengerService fs = new PassengerService(new DaoPassengerFile(fileNonExisting));
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                fs::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}