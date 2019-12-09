package modulos.facturas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.proveedores.Proveedor;
import modulos.proveedores.ProveedorDAO;
import utils.MyUtils;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class ConsultaFacturaController implements Initializable {

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXComboBox<Proveedor> cmbProv;

    @FXML
    private JFXTextField txtFolio;

    @FXML
    private TableView<Factura> tblFacts;

    @FXML
    private TableColumn<Factura, Integer> colFolio;

    @FXML
    private TableColumn<Factura, String> colName;

    @FXML
    private TableColumn<Factura, Date> colFecha;

    FacturaDAO facturaDAO = new FacturaDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();

        configUI();
    }

    private void configUI(){
        ProveedorDAO proveedorDAO = new ProveedorDAO(MySQL.getConnection());
        cmbProv.setItems(proveedorDAO.selectAll());

        cmbProv.valueProperty().addListener((observable, oldValue, newValue) -> {
            tblFacts.setItems(facturaDAO.selectByProv(newValue.getCodProveedor()));
        });
    }

    private void initTable(){
        colFolio.setCellValueFactory(new PropertyValueFactory<>("folio"));
        colName.setCellValueFactory(new PropertyValueFactory<>("nombreProv"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaExp"));

        tblFacts.setItems(facturaDAO.selectAll());

        tblFacts.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Factura fac = tblFacts.getSelectionModel().getSelectedItem();
                if (fac != null) {
                    FacturaController controller = new FacturaController(fac);
                    MyUtils.openWindow(getClass().getResource("/fxml/SceneFactura.fxml"), "Compras", controller);
                }
            }
        });
    }
}
