package database.dao;

import entities.Identifiable;
import exceptions.booking_menu_exceptions.FileDatabaseException;
import exceptions.booking_menu_exceptions.NonInitializedDatabaseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoFile<A extends Identifiable> implements DAO<A> {

    private final File file;

    public DaoFile(File file) {
        this.file = file;
    }

    @Override
    public void save(A object) {
        List<A> data = getAll().orElseGet(ArrayList::new);
        data.add(object);
        setAllTo(data);
    }

    @Override
    public Optional<A> get(int id) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("File field of DAO is an empty file or a file not containing a List of corresponding entity.");
        }
        return getAll().get().stream()
                .filter(obj -> obj.getId() == id)
                .findAny();
    }

    @Override
    public Optional<A> get(A object) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("File field of DAO is an empty file or a file not containing a List of corresponding entity.");
        }
        return getAll().get().stream()
                .filter(obj -> obj.equals(object))
                .findAny();
    }

    @Override
    public boolean remove(int id) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException("File field of DAO is an empty file or a file not containing a List of corresponding entity.");
        }
        List<A> data = getAll().get();
        if (data.removeIf(obj -> obj.getId() == id)) {
            setAllTo(data);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(A object) {
        if (isEmpty()) {
            throw new NonInitializedDatabaseException(
                    "File field of DAO is an empty file or a file not containing a List of corresponding entity.");
        }
        List<A> data = getAll().get();
        if (data.remove(object)) {
            setAllTo(data);
            return true;
        }
        return false;
    }


    @Override
    public void saveAll(List<A> objects) {
        List<A> data = getAll().orElseGet(ArrayList::new);
        data.addAll(objects);
        setAllTo(data);
    }

    public Optional<List<A>> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((List<A>) ois.readObject());
        }
        catch (EOFException exc) {
            return Optional.empty();
        }
        catch (ClassNotFoundException | IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    public void setAllTo(List<A> data) {
        if (!file.exists()) {
            throw new FileDatabaseException(new FileNotFoundException(String.format(
                    "Database file could not be found at: %s",
                    file.getAbsolutePath())));
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
        catch (IOException exc) {
            throw new FileDatabaseException(exc);
        }
    }

    @Override
    public boolean isPresent() {
        return getAll().isPresent();
    }

    @Override
    public boolean isEmpty() {
        return getAll().isEmpty();
    }

    @Override
    public int getMaxId() {
        return getAll().orElseGet(ArrayList::new).stream()
                .map(Identifiable::getId)
                .mapToInt(id -> id)
                .max()
                .orElse(1);
    }
}
