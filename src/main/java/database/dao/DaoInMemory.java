package database.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoInMemory<A extends Identifiable> implements DAO<A>{

    private List<A> objects;

    public DaoInMemory(List<A> objects) {
        this.objects = new ArrayList<>(objects);
    }

    @Override
    public void save(A object) {
        objects.add(object);
    }

    @Override
    public Optional<A> get(int id) {
        return objects.stream()
                .filter(obj -> obj.getId() == id)
                .findFirst();
    }

    @Override
    public Optional<A> get(A object) {
        return get(object.getId());
    }

    @Override
    public void saveAll(List<A> objects) {
        this.objects.addAll(objects);
    }


    @Override
    public Optional<List<A>> getAll() {
        return Optional.of(objects);
    }

    @Override
    public void setAll(List<A> data) {
        objects = new ArrayList<>(data);
    }

    @Override
    public boolean remove(int id) {
        return objects.removeIf(obj -> obj.getId() == id);
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
