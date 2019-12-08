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
import modulos.productos.ProductDAO;
import modulos.catalogos.Marca;
import modulos.productos.Producto;
import modulos.catalogos.TipoProd;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private TableView<Producto> tblProducts;

    @FXML
    private JFXTextField txtCodigo;

    @FXML
    private JFXComboBox<TipoProd> cmbTipo;

    @FXML
    private JFXComboBox<Marca> cmbMarca;

    @FXML
    private JFXTextField txtPrecio;

    @FXML
    private JFXTextField txtLinea;

    @FXML
    private JFXTextField txtStock;

    @FXML
    private JFXTextField txtColor;

    @FXML
    private JFXTextField txtDescripcion;

    @FXML
    private JFXComboBox<String> cmbGenero;

    ProductDAO prodDAO = new ProductDAO(MySQL.getConnection());
    boolean modEdit = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);

        CatMarcaDAO catMarcaDAO = new CatMarcaDAO(MySQL.getConnection());
        CatProdDAO catProdDAO = new CatProdDAO(MySQL.getConnection());

        cmbMarca.setItems(catMarcaDAO.selectAll());
        cmbTipo.setItems(catProdDAO.selectAll());
        cmbGenero.setItems(MyUtils.getGeneres());

        btnNew.setOnAction(event -> {
            modEdit = false;
            disableButtons();
            disableFields(false);
            clean();
        });

        btnAdd.setOnAction(event -> {
            saveProd();
            refreshTable();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteProd();
            clean();
            refreshTable();
        });

        btnEdit.setOnAction(event -> {
            updateProd();
            refreshTable();
        });

        tblProducts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Producto prod = tblProducts.getSelectionModel().getSelectedItem();
                if (prod != null) {
                    modEdit = true;
                    selectProd(prod);
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtCodigo, 5);
        MyUtils.stablishNumericRestriction(txtPrecio);
        MyUtils.stablishNumericRestriction(txtStock);
    }

    private void initTable() {
        TableColumn<Producto, String> colCod = new TableColumn<>("CODIGO");
        TableColumn<Producto, String> colDesc = new TableColumn<>("DESCRIPCION");
        TableColumn<Producto, String> colPrecio = new TableColumn<>("PRECIO");

        colCod.setCellValueFactory(new PropertyValueFactory<>("codProd"));
        colCod.setPrefWidth(100);

        colDesc.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colDesc.setPrefWidth(200);

        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colPrecio.setPrefWidth(100);

        tblProducts.getColumns().addAll(colCod, colDesc, colPrecio);
    }

    private void clean() {
        txtCodigo.setText("");
        txtPrecio.setText("");
        txtLinea.setText("");
        txtColor.setText("");
        txtDescripcion.setText("");
        cmbGenero.getSelectionModel().clearSelection();
        cmbTipo.getSelectionModel().clearSelection();
        cmbMarca.getSelectionModel().clearSelection();
        txtCodigo.requestFocus();
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void disableFields(boolean disable) {
        txtCodigo.setDisable(modEdit);
        txtPrecio.setDisable(disable);
        txtLinea.setDisable(disable);
        txtColor.setDisable(disable);
        txtDescripcion.setDisable(disable);
        cmbGenero.setDisable(disable);
        cmbTipo.setDisable(disable);
        cmbMarca.setDisable(disable);
    }

    private void updateProd() {
        Producto prod = getProd();
        prodDAO.update(prod);
    }

    private void saveProd() {
        Producto prod = getProd();
        prodDAO.insert(prod);
    }

    private void selectProd(Producto prod) {
        txtCodigo.setText(prod.getCodProd());
        txtPrecio.setText(prod.getPrecio() + "");
        txtLinea.setText(prod.getLinea());
        txtStock.setText(prod.getStock() + "");
        txtColor.setText(prod.getColor());
        txtDescripcion.setText(prod.getDescripcion());
        cmbGenero.getSelectionModel().select(prod.getGenero());
        cmbTipo.getSelectionModel().select(getIndexOfTipoProd(prod.getCveTipo()));
        cmbMarca.getSelectionModel().select(getIndexOfMarca(prod.getCveMarca()));
    }

    private void deleteProd() {
        Producto prod = new Producto();
        prod.setCodProd(txtCodigo.getText().trim());
        prodDAO.delete(prod);
    }

    private Producto getProd() {
        String codProd = txtCodigo.getText().trim();
        double precio = Double.valueOf(txtPrecio.getText());
        String linea = txtLinea.getText().trim();
        String color = txtColor.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String genero = cmbGenero.getSelectionModel().getSelectedItem();
        String cveMarca = cmbMarca.getSelectionModel().getSelectedItem().getClave();
        String cveTipo = cmbTipo.getSelectionModel().getSelectedItem().getClave();

        int stock;

        if (txtStock.getText().length() > 0)
            stock = Integer.valueOf(txtStock.getText());
        else stock = 0;

        return new Producto(codProd, (float) precio, linea, stock, color, descripcion, genero, cveMarca, cveTipo);
    }

    private void refreshTable() {
        tblProducts.setItems(prodDAO.selectAll());
    }

    /**
     * Obtiene el index del ObservableList de TipoProd, de una Tipo de Producto, mediante su clave
     *
     * @param cveTipo
     * @return
     */
    private int getIndexOfTipoProd(String cveTipo) {
        ObservableList<TipoProd> items = cmbTipo.getItems();

        for (int i = 0; i < items.size(); i++) {
            TipoProd p = items.get(i);
            if (p.getClave().equals(cveTipo))
                return i;
        }
        return 0;
    }

    private int getIndexOfMarca(String cveMarca) {
        ObservableList<Marca> items = cmbMarca.getItems();

        for (int i = 0; i < items.size(); i++) {
            Marca p = items.get(i);
            if (p.getClave().equals(cveMarca))
                return i;
        }
        return 0;
    }

}
