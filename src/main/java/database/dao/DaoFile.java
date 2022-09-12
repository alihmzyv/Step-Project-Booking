package database.dao;

import entities.Passenger;

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
        saveAll(data);
    }

    @Override
    public Optional<A> get(int id) {
        return getAll().orElseGet(ArrayList::new).stream()
                .filter(obj -> obj.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<A> get(A object) {
        return getAll().orElseGet(ArrayList::new).stream()
                .filter(obj -> obj.equals(object))
                .findFirst();
    }

    @Override
    public void saveAll(List<A> objects) {
        List<A> data = getAll().orElseGet(ArrayList::new);
        data.addAll(objects);
        setAll(data);
    }

    public Optional<List<A>> getAll() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return Optional.of((List<A>) ois.readObject());
        }
        catch (EOFException exc) {
            return Optional.empty();
        }
        catch (ClassNotFoundException | IOException exc) {
            exc.printStackTrace();
            System.exit(1);
            return Optional.empty();
        }
    }

    public void setAll(List<A> data) {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(data);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(int id) {
        Optional<List<A>> data = getAll();
        if (data.isEmpty()) {
            return false;
        }
        return data.get().removeIf(obj -> obj.getId() == id);
    }

    @Override
    public boolean remove(A object) {
        Optional<List<A>> data = getAll();
        if (data.isEmpty()) {
            return false;
        }
        return data.get().remove(object);
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
