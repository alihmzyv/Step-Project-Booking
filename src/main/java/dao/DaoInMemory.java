package dao;

import java.util.Collection;
import java.util.List;

public interface DaoInMemory<A extends Identifiable> extends DAO<A>{
    void saveAll(List<A> objects);
    List<A> getAll();
}
