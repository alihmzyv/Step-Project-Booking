package database.file.service.user;

import database.dao.DaoUserFile;
import database.services.UserService;
import entities.Booking;
import entities.User;
import exceptions.database_exceptions.LocalDatabaseException;
import exceptions.database_exceptions.NoSuchUserException;
import exceptions.database_exceptions.NonInstantiatedDaoException;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceFileTest {

    List<User> randomUsers = User.getRandom(100);
    User randomUser = User.getRandom();
    private final File file = new File("src/test/java/database/file/files/users.bin");
    private final File fileNonExisting = new File("none.bin");

    private void makeFull() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(randomUsers);
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
    void getAllUsersTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.of(randomUsers), us.getAllUsers());
    }

    @Test
    void getAllUsersTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getAllUsers());
    }

    @Test
    void getAllUsersTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                us::getAllUsers);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void setAllUsersToTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        List<User> randomUsers2 = User.getRandom(100);
        us.setAllUsers(randomUsers2);
        assertEquals(Optional.of(randomUsers2), us.getAllUsers());
    }

    @Test
    void setAllUsersToTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        List<User> randomUsers2 = User.getRandom(100);
        us.setAllUsers(randomUsers2);
        assertEquals(Optional.of(randomUsers2), us.getAllUsers());
    }

    @Test
    void setAllUsersToTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        List<User> randomUsers2 = User.getRandom(100);
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> us.setAllUsers(randomUsers2));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isPresentTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertTrue(us.isPresent());
    }

    @Test
    void isPresentTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertFalse(us.isPresent());
    }

    @Test
    void isPresentTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                us::isPresent);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void isEmptyTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertFalse(us.isEmpty());
    }

    @Test
    void isEmptyTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertTrue(us.isEmpty());
    }

    @Test
    void isEmptyTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                us::isEmpty);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void saveTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.isPresent());
        assertTrue(us.getAllUsers().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void saveTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.isPresent());
        assertTrue(us.getAllUsers().get().stream()
                .anyMatch(user -> user.equals(randomUser)));
    }

    @Test
    void saveTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> us.saveUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithObjTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getUser(randomUser));
    }

    @Test
    void getWithObjTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser));
    }

    @Test
    void getWithObjTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> us.getUser(randomUser));
    }

    @Test
    void getWithObjTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> us.getUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void getWithUsernamePsswrdTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertEquals(Optional.empty(), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertEquals(Optional.of(randomUser), us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () ->
                us.getUser(randomUser.getUsername(), randomUser.getPassword()));
    }

    @Test
    void getWithUsernamePsswrdTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> us.getUser(randomUser.getUsername(), randomUser.getPassword()));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }


    @Test
    void removeWithObjTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertFalse(us.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest3() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> us.removeUser(randomUser));
    }

    @Test
    void removeWithObjTest4() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                () -> us.removeUser(randomUser));
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }

    @Test
    void addBookingTest1() {
        makeFull();
        User randomUser = User.getRandom();
        Booking randomBooking = Booking.getRandom();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NoSuchUserException.class, () -> us.addBooking(randomUser, randomBooking));
    }

    @Test
    void addBookingTest2() {
        User randomUser = User.getRandom();
        Booking randomBooking = Booking.getRandom();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        us.addBooking(randomUser, randomBooking);
        assertTrue(us.getUser(randomUser).get().hasBooking(randomBooking));
    }

    @Test
    void removeBookingTest1() {
        User randomUser = User.getRandom();
        Booking randomBooking = Booking.getRandom();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NoSuchUserException.class, () -> us.removeBooking(randomUser, randomBooking));
    }

    @Test
    void removeBookingTest2() {
        User randomUser = User.getRandom();
        Booking randomBooking = Booking.getRandom();
        UserService us = new UserService(new DaoUserFile(file));
        randomUser.addBooking(randomBooking);
        us.saveUser(randomUser);
        assertTrue(us.removeBooking(randomUser, randomBooking));
    }

    @Test
    void containsTest1() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertThrowsExactly(NonInstantiatedDaoException.class, () -> us.contains(randomUser.getUsername()));
    }

    @Test
    void containsTest2() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        us.saveUser(randomUser);
        assertTrue(us.contains(randomUser.getUsername()));
    }

    @Test
    void getMaxIdTest1() {
        makeFull();
        UserService us = new UserService(new DaoUserFile(file));
        assertTrue(us.isPresent());
        assertEquals(us.getAllUsers().get().stream()
                .mapToInt(User::getId)
                .max()
                .getAsInt(), us.getMaxId());
    }

    @Test
    void getMaxIdTest2() {
        makeEmpty();
        UserService us = new UserService(new DaoUserFile(file));
        assertTrue(us.isEmpty());
        assertEquals(1, us.getMaxId());
    }

    @Test
    void getMaxIdTest3() {
        UserService us = new UserService(new DaoUserFile(fileNonExisting));
        LocalDatabaseException exc = assertThrowsExactly(LocalDatabaseException.class,
                us::getMaxId);
        assertEquals(FileNotFoundException.class, exc.getCause().getClass());
    }
}