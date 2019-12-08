package modulos.catalogos;

import javafx.collections.ObservableList;

public interface BasicCatDAO {
    boolean insert(CatalogoBase catBase);
    boolean update(CatalogoBase catBase);
    ObservableList selectAll();
    boolean delete(CatalogoBase catBase);
}
