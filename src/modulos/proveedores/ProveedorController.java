package modulos.proveedores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.proveedores.ProveedorDAO;
import modulos.proveedores.Proveedor;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class ProveedorController implements Initializable {

    @FXML
    private TableView<Proveedor> tblProveedor;

    @FXML
    private JFXTextField txtCodigo;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtDomicilio;

    @FXML
    private JFXTextField txtPais;

    @FXML
    private JFXComboBox<String> cmbEstado;

    @FXML
    private JFXTextField txtCiudad;

    @FXML
    private JFXTextField txtColonia;

    @FXML
    private JFXTextField txtCP;

    @FXML
    private JFXTextField txtfax;

    @FXML
    private JFXTextField txtPagWed;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtRFC;

    @FXML
    private JFXTextField txtDescuento;

    @FXML
    private JFXTextField txtCredito;

    @FXML
    private JFXTextField txtDiasCred;

    @FXML
    private JFXTextField txtObservaciones;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    boolean modEdit = false;

    ProveedorDAO provDao = new ProveedorDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI(){
        cmbEstado.setItems(MyUtils.getStates());
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);

        btnNew.setOnAction(event -> {
            modEdit = false;
            disableButtons();
            disableFields(false);
            clean();
        });

        btnAdd.setOnAction(event -> {
            saveProv();
            refreshTable();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteProv();
            clean();
            refreshTable();
        });

        btnEdit.setOnAction(event -> {
            editProv();
            refreshTable();
        });

        tblProveedor.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Proveedor prov = tblProveedor.getSelectionModel().getSelectedItem();
                if (prov != null) {
                    modEdit = true;
                    selectProv(prov);
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtCodigo, 4);
        MyUtils.stablishTextFiedlLimit(txtRFC, 13);

        MyUtils.stablishNumericRestriction(txtDescuento);
        MyUtils.stablishNumericRestriction(txtDiasCred);
        MyUtils.stablishNumericRestriction(txtCredito);
    }

    private void initTable() {
        TableColumn<Proveedor, String> colName = new TableColumn<>("Nombre");
        TableColumn<Proveedor, String> colTel = new TableColumn<>("Teléfono");
        TableColumn<Proveedor, String> colCity = new TableColumn<>("Ciudad");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(140);

        colTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTel.setPrefWidth(130);

        colCity.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colCity.setPrefWidth(130);

        tblProveedor.getColumns().addAll(colName, colCity, colTel);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void clean() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtObservaciones.setText("");
        txtPagWed.setText("");
        txtfax.setText("");
        cmbEstado.getSelectionModel().clearSelection();
        txtCP.setText("");
        txtPais.setText("");
        txtColonia.setText("");
        txtRFC.setText("");
        txtCiudad.setText("");
        txtDomicilio.setText("");
        txtDescuento.setText("");
        txtCredito.setText("");
        txtDiasCred.setText("");
        txtCodigo.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtCodigo.setDisable(disable);
        txtNombre.setDisable(disable);
        txtTelefono.setDisable(disable);
        txtEmail.setDisable(disable);
        txtObservaciones.setDisable(disable);
        txtPagWed.setDisable(disable);
        txtfax.setDisable(disable);
        cmbEstado.getSelectionModel().select("");
        txtCP.setDisable(disable);
        txtPais.setDisable(disable);
        txtColonia.setDisable(disable);
        txtRFC.setDisable(disable);
        txtCiudad.setDisable(disable);
        txtDomicilio.setDisable(disable);
        txtDescuento.setDisable(disable);
        txtCredito.setDisable(disable);
        txtDiasCred.setDisable(disable);
        txtCodigo.setDisable(modEdit);
    }

    private void saveProv(){
       Proveedor prov = getProveedor();
       provDao.insert(prov);
    }

    private void editProv(){
        Proveedor prov = getProveedor();
        provDao.update(prov);
    }

    private void selectProv(Proveedor prov){
        String cod = prov.getCodProveedor();
        String name = prov.getNombre();
        String tel = prov.getTelefono();
        String email = prov.getEmail();
        String obs = prov.getObservaciones();
        String pagWeb = prov.getPagWeb();
        String fax = prov.getFax();
        String estado = prov.getEstado();
        String cp = prov.getCp();
        String pais = prov.getPais();
        String col = prov.getColonia();
        String rfc = prov.getRfc();
        String ciudad = prov.getCiudad();
        String domicilio = prov.getDomicilio();
        double desc = prov.getDescuento();
        double credito = prov.getCredito();
        int diasCred = prov.getDiasCred();

        txtCodigo.setText(cod);
        txtNombre.setText(name);
        txtTelefono.setText(tel);
        txtEmail.setText(email);
        txtObservaciones.setText(obs);
        txtPagWed.setText(pagWeb);
        txtfax.setText(fax);
        cmbEstado.getSelectionModel().select(estado);
        txtCP.setText(cp);
        txtPais.setText(pais);
        txtColonia.setText(col);
        txtRFC.setText(rfc);
        txtCiudad.setText(ciudad);
        txtDomicilio.setText(domicilio);
        txtDescuento.setText(desc+"");
        txtCredito.setText(credito+"");
        txtDiasCred.setText(diasCred+"");
    }

    private void deleteProv() {
        String cve = txtCodigo.getText().trim();
        Proveedor prov = new Proveedor();
        prov.setCodProveedor(cve);
        provDao.delete(prov);
    }

    private Proveedor getProveedor(){
        String cod = txtCodigo.getText().trim();
        String name = txtNombre.getText().trim();
        String tel = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String obs = txtObservaciones.getText().trim();
        String pagWeb = txtPagWed.getText().trim();
        String fax = txtfax.getText().trim();
        String estado = cmbEstado.getSelectionModel().getSelectedItem();
        String cp = txtCP.getText().trim();
        String pais = txtPais.getText().trim();
        String col = txtColonia.getText().trim();
        String rfc = txtRFC.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String domicilio = txtDomicilio.getText().trim();
        double desc = Double.valueOf(txtDescuento.getText());
        double credito = Double.valueOf(txtCredito.getText());
        int diasCred = Integer.valueOf(txtDiasCred.getText());

        Proveedor prov = new Proveedor(cod, name, tel, email, obs, pagWeb, fax, estado, cp, pais, col, rfc, ciudad, domicilio,
                desc, credito, diasCred);

        return prov;
    }

    private void refreshTable() {
        tblProveedor.setItems(provDao.selectAll());
    }
}
