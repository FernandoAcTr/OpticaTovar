package modulos.laboratorios;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.laboratorios.LaboratorioDAO;
import modulos.laboratorios.Laboratorio;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class LabController implements Initializable {

    @FXML
    private TableView<Laboratorio> tblLaboratorio;

    @FXML
    private JFXTextField txtClave;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXComboBox<String> cmbEstado;

    @FXML
    private JFXTextField txtDomicilio;

    @FXML
    private JFXTextField txtCiudad;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    LaboratorioDAO labDao = new LaboratorioDAO(MySQL.getConnection());
    boolean modEdit = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
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
            saveLab();
            refreshTable();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteLab();
            clean();
            refreshTable();
        });

        btnEdit.setOnAction(event -> {
            editLab();
            refreshTable();
        });

        tblLaboratorio.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Laboratorio lab = tblLaboratorio.getSelectionModel().getSelectedItem();
                if (lab != null) {
                    modEdit = true;
                    selectLab(lab);
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtClave, 5);
    }

    private void initTable() {
        TableColumn<Laboratorio, String> colName = new TableColumn<>("Nombre");
        TableColumn<Laboratorio, String> colCity = new TableColumn<>("Ciudad");
        TableColumn<Laboratorio, String> colState = new TableColumn<>("Estado");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(140);

        colState.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colState.setPrefWidth(130);

        colCity.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        colCity.setPrefWidth(130);

        tblLaboratorio.getColumns().addAll(colName, colState, colCity);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void clean() {
        txtClave.setText("");
        txtNombre.setText("");
        txtDomicilio.setText("");
        txtCiudad.setText("");
        cmbEstado.getSelectionModel().clearSelection();
        txtClave.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtClave.setDisable(modEdit);
        txtNombre.setDisable(disable);
        txtDomicilio.setDisable(disable);
        txtCiudad.setDisable(disable);
        cmbEstado.setDisable(disable);
    }

    private void saveLab() {
        Laboratorio lab = getLab();
        labDao.insert(lab);
    }

    private void editLab() {
        Laboratorio lab = getLab();
        labDao.update(lab);
    }

    private void selectLab(Laboratorio lab) {
        txtClave.setText(lab.getCveLab());
        txtCiudad.setText(lab.getCiudad());
        txtDomicilio.setText(lab.getDomicilio());
        txtNombre.setText(lab.getNombre());
        cmbEstado.getSelectionModel().select(lab.getEstado());
    }

    private void deleteLab() {
        String cve = txtClave.getText();
        Laboratorio lab = new Laboratorio();
        lab.setCveLab(cve);
        labDao.delete(lab);
    }

    private Laboratorio getLab() {
        String cve = txtClave.getText().trim();
        String name = txtNombre.getText().trim();
        String domicilio = txtDomicilio.getText().trim();
        String city = txtCiudad.getText().trim();
        String state = cmbEstado.getSelectionModel().getSelectedItem();

        return new Laboratorio(cve, name, domicilio, city, state);
    }

    private void refreshTable() {
        tblLaboratorio.setItems(labDao.selectAll());
    }

}
