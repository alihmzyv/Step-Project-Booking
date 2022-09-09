package database.dao;

import java.util.List;
import java.util.Optional;

public class DaoInMemory<A extends Identifiable> implements DAO<A>{

    private List<A> objects;

    public DaoInMemory(List<A> objects) {
        this.objects = objects;
    }

    public DaoInMemory() {
    }

    @Override
    public void save(A object) {

    }

    @Override
    public Optional<A> get(int id) {
        return Optional.empty();
    }

    @Override
    public void saveAll(List<A> objects) {

    }

    @Override
    public List<A> getAll() {
        return null;
    }
}
