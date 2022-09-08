package dao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DAO<A extends Identifiable> {
    void save(A object);
    Optional<A> get(int id);
}
