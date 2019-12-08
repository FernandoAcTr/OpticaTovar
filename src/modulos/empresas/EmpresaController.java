package modulos.empresas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.empresas.EmpresaDAO;
import modulos.empresas.Empresa;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class EmpresaController implements Initializable {

    @FXML
    private TableView<Empresa> tblEmpresa;

    @FXML
    private JFXTextField txtClave;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtDescuento;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtRFC;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private JFXTextArea txtObs;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    boolean modEdit = false;

    EmpresaDAO empresaDAO = new EmpresaDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
        cmbStatus.setItems(MyUtils.getStatusOptions());
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
            saveEmpresa();
            refreshTable();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteEmpresa();
            clean();
            refreshTable();
        });

        btnEdit.setOnAction(event -> {
            editEmpresa();
            refreshTable();
        });

        tblEmpresa.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Empresa empresa = tblEmpresa.getSelectionModel().getSelectedItem();
                if (empresa != null) {
                    modEdit = true;
                    selectEmpresa(empresa);
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtClave, 5);
        MyUtils.stablishTextFiedlLimit(txtRFC, 13);

        MyUtils.stablishNumericRestriction(txtDescuento);
    }

    private void initTable() {
        TableColumn<Empresa, String> colName = new TableColumn<>("Nombre");
        TableColumn<Empresa, String> colTel = new TableColumn<>("Teléfono");
        TableColumn<Empresa, String> colStatus = new TableColumn<>("Status");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(140);

        colTel.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colTel.setPrefWidth(130);

        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStatus.setPrefWidth(130);

        tblEmpresa.getColumns().addAll(colName, colTel, colStatus);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void clean() {
        txtClave.setText("");
        txtDescuento.setText("");
        txtEmail.setText("");
        txtNombre.setText("");
        txtObs.setText("");
        txtRFC.setText("");
        txtTelefono.setText("");
        cmbStatus.getSelectionModel().clearSelection();
        txtClave.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtClave.setDisable(disable);
        txtDescuento.setDisable(disable);
        txtEmail.setDisable(disable);
        txtNombre.setDisable(disable);
        txtObs.setDisable(disable);
        txtRFC.setDisable(disable);
        txtTelefono.setDisable(disable);
        txtClave.setDisable(modEdit);
    }

    private void saveEmpresa() {
        Empresa empresa = getEmpresa();
        empresaDAO.insert(empresa);
    }

    private void editEmpresa() {
        Empresa empresa = getEmpresa();
        empresaDAO.update(empresa);
    }

    private void deleteEmpresa() {
        String cve = txtClave.getText().trim();
        Empresa emp = new Empresa();
        emp.setCveEmpresa(cve);
        empresaDAO.delete(emp);
    }

    private void selectEmpresa(Empresa empresa) {
        String cve = empresa.getCveEmpresa();
        double desc = empresa.getDescuento();
        String email = empresa.getEmail();
        String name = empresa.getNombre();
        String obs = empresa.getObservaciones();
        String rfc = empresa.getRfc();
        String tel = empresa.getTelefono();
        String status = empresa.getStatus();

        txtClave.setText(cve);
        txtDescuento.setText(MyUtils.trimFloat((float) desc));
        txtEmail.setText(email);
        txtTelefono.setText(tel);
        txtNombre.setText(name);
        txtObs.setText(obs);
        txtRFC.setText(rfc);
        cmbStatus.getSelectionModel().select(status);
    }

    private Empresa getEmpresa() {
        String cve = txtClave.getText().trim();
        double desc;
        if (txtDescuento.getText().length() == 0)
            desc = 0;
        else
            desc = Double.valueOf(txtDescuento.getText());
        String email = txtEmail.getText().trim();
        String name = txtNombre.getText().trim();
        String obs = txtObs.getText().trim();
        String rfc = txtRFC.getText().trim();
        String tel = txtTelefono.getText().trim();
        String status = cmbStatus.getSelectionModel().getSelectedItem();

        Empresa empresa = new Empresa(cve, name, status, desc, email, obs, rfc, tel);

        return empresa;
    }

    private void refreshTable() {
        tblEmpresa.setItems(empresaDAO.selectAll());
    }
}
