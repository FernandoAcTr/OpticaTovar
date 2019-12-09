package modulos.productos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.catalogos.CatMarcaDAO;
import modulos.catalogos.CatProdDAO;
import modulos.catalogos.Marca;
import modulos.catalogos.TipoProd;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchProductController implements Initializable {

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<TipoProd> cmbType;

    @FXML
    private JFXComboBox<Marca> cmbMarca;

    @FXML
    private TableView<Producto> tblProds;

    @FXML
    private TableColumn<Producto, String> colCod;

    @FXML
    private TableColumn<Producto, String> colDesc;

    @FXML
    private TableColumn<Producto, String> colTipo;

    @FXML
    private TableColumn<Producto, String> colMarca;


    ProductDAO productDAO = new ProductDAO(MySQL.getConnection());
    ReciveProductBase reciver;

    public SearchProductController(ReciveProductBase reciver) {
        this.reciver = reciver;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
    }

    private void configUI() {
        CatMarcaDAO marcaDAO = new CatMarcaDAO(MySQL.getConnection());
        CatProdDAO catProdDAO = new CatProdDAO(MySQL.getConnection());

        cmbMarca.setItems(marcaDAO.selectAll());
        cmbType.setItems(catProdDAO.selectAll());

        Marca marca = new Marca();
        marca.setDescription("Todas");

        TipoProd tipoProd = new TipoProd();
        tipoProd.setDescription("Todos");

        cmbMarca.getItems().add(marca);
        cmbType.getItems().add(tipoProd);

        cmbMarca.getSelectionModel().selectLast();
        cmbType.getSelectionModel().selectLast();

        btnSearch.setOnAction(event -> search());
    }

    private void initTable(){
        colCod.setCellValueFactory(new PropertyValueFactory<>("codProd"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("cveTipo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("cveMarca"));

        tblProds.setItems(productDAO.selectAll());

        tblProds.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Producto pac = tblProds.getSelectionModel().getSelectedItem();
                if (pac != null) {
                    reciver.reciveProduct(pac);
                    btnSearch.getScene().getWindow().hide();
                }
            }
        });
    }

    private void search() {
        boolean byDesc = false;
        boolean byType = false;
        boolean byMarca = false;

        if (cmbType.getSelectionModel().getSelectedIndex() != cmbType.getItems().size() - 1)
            byType = true;

        if (cmbMarca.getSelectionModel().getSelectedIndex() != cmbMarca.getItems().size() - 1)
            byMarca = true;

        if (txtDescription.getText().trim().length() > 0)
            byDesc = true;

        filter(byDesc, byType, byMarca);
    }

    private void filter(boolean byDesc, boolean byType, boolean byMarca) {
        ObservableList<Producto> products = productDAO.selectAll();
        if (byDesc)
            products = productDAO.selectProductByDescription(txtDescription.getText().trim());
        else if (byType)
            products = productDAO.selectProductByType(cmbType.getSelectionModel().getSelectedItem().getClave());
        else if (byMarca)
            products = productDAO.selectProductByMarca(cmbMarca.getSelectionModel().getSelectedItem().getClave());

        tblProds.setItems(products);
    }
}
