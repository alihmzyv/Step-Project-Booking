package database.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A object);
    Optional<A> get(int id);
    void saveAll(List<A> objects);
    List<A> getAll();
}
