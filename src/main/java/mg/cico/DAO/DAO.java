package mg.cico.DAO;

import java.util.List;

public interface DAO<T> {
    void save(T obj);
    void update(T obj);
    boolean delete(int id);
    List<T> findAll();
}
