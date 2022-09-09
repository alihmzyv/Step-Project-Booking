package database.dao;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class DaoFile<A extends Identifiable> implements DAO<A> {

    private File file;

    public DaoFile(File file) {
        this.file = file;
    }

    @Override
    public void save(A object) {
        throw new RuntimeException("not impl");
    }

    @Override
    public Optional<A> get(int id) {
        throw new RuntimeException("not impl");
    }

    @Override
    public void saveAll(List<A> objects) {
        throw new RuntimeException("not impl");
    }

    @Override
    public List<A> getAll() {
        throw new RuntimeException("not impl");
    }
}
