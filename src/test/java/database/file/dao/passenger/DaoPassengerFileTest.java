package database.file.dao.passenger;

import database.dao.DaoPassengerFile;
import entities.Passenger;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoPassengerFileTest {
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
    void getAllTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertEquals(Optional.of(passengers), daoPassengerFile.getAll());
    }

    @Test
    void getAllTest2() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertEquals(Optional.empty(), daoPassengerFile.getAll());
    }

    @Test
    void getAllTest3() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoPassengerFile::getAll);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllToTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        daoPassengerFile.setAllTo(passengers2);
        assertEquals(Optional.of(passengers2), daoPassengerFile.getAll());
    }

    @Test
    void setAllToTest2() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        daoPassengerFile.setAllTo(passengers2);
        assertEquals(Optional.of(passengers2), daoPassengerFile.getAll());
    }

    @Test
    void setAllToTest3() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.setAllTo(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void saveTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        List<Passenger> passengersCopy = new ArrayList<>(passengers);
        passengersCopy.add(randomPassenger);
        assertEquals(Optional.of(passengersCopy), daoPassengerFile.getAll());
    }

    @Test
    void saveTest2() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        assertEquals(Optional.of(List.of(randomPassenger)), daoPassengerFile.getAll());
    }

    @Test
    void saveTest3() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        Passenger randomPassenger = Passenger.getRandom();
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.save(randomPassenger));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithIdTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertEquals(Optional.empty(), daoPassengerFile.get(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest2() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        assertEquals(Optional.of(randomPassenger), daoPassengerFile.get(randomPassenger.getId()));
    }

    @Test
    void getWithIdTest3() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoPassengerFile.get(Passenger.getRandom().getId()));
    }

    @Test
    void getWithIdTest4() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.get(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        assertEquals(Optional.empty(), daoPassengerFile.get(randomPassenger));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        assertEquals(Optional.of(randomPassenger), daoPassengerFile.get(randomPassenger));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoPassengerFile.get(Passenger.getRandom()));
    }

    @Test
    void getWithObjTest4() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.get(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithIdTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertFalse(daoPassengerFile.remove(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest2() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        assertTrue(daoPassengerFile.remove(randomPassenger.getId()));
    }

    @Test
    void removeWithIdTest3() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoPassengerFile.remove(Passenger.getRandom().getId()));
    }

    @Test
    void removeWithIdTest4() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.remove(Passenger.getRandom().getId()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void removeWithObjTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertFalse(daoPassengerFile.remove(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        Passenger randomPassenger = Passenger.getRandom();
        daoPassengerFile.save(randomPassenger);
        assertTrue(daoPassengerFile.remove(randomPassenger));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertThrowsExactly(NonInitializedDatabaseException.class, () -> daoPassengerFile.remove(Passenger.getRandom()));
    }

    @Test
    void removeWithObjTest4() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.remove(Passenger.getRandom()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveAllTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        daoPassengerFile.saveAll(passengers2);
        List<Passenger> allPassengers = new ArrayList<>();
        allPassengers.addAll(passengers);
        allPassengers.addAll(passengers2);
        assertEquals(Optional.of(allPassengers), daoPassengerFile.getAll());
    }

    @Test
    void saveAllTest2() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        daoPassengerFile.saveAll(passengers2);
        assertEquals(Optional.of(passengers2), daoPassengerFile.getAll());
    }

    @Test
    void saveAllTest3() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        List<Passenger> passengers2 = Passenger.getRandom(100);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                () -> daoPassengerFile.saveAll(passengers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertEquals(daoPassengerFile.getAll().get().stream()
                .mapToInt(Passenger::getId)
                .max()
                .getAsInt(), daoPassengerFile.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(file);
        assertEquals(1, daoPassengerFile.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        DaoPassengerFile daoPassengerFile = new DaoPassengerFile(fileNonExisting);
        FileDatabaseException exc = assertThrowsExactly(FileDatabaseException.class,
                daoPassengerFile::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}