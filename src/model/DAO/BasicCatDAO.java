package model.DAO;

import javafx.collections.ObservableList;
import model.CatalogoBase;

public interface BasicCatDAO {
    boolean insert(CatalogoBase catBase);
    boolean update(CatalogoBase catBase);
    ObservableList selectAll();
    boolean delete(CatalogoBase catBase);
}
