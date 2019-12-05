package model.DAO;

import javafx.collections.ObservableList;

public interface BasicDAO {
    boolean insert(Object bean);
    boolean update(Object bean);
    ObservableList selectAll();
    boolean delete(Object bean);
}
