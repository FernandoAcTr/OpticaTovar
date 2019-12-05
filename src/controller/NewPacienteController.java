package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.DAO.EmpresaDAO;
import model.DAO.PacienteDAO;
import model.Empresa;
import model.Paciente;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class NewPacienteController implements Initializable {

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXComboBox<Empresa> cmbEmpresa;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellido;

    @FXML
    private JFXTextField txtCiudad;

    @FXML
    private JFXComboBox<String> cmbEstado;

    @FXML
    private JFXTextField txtDireccion;

    @FXML
    private JFXTextField txtColonia;

    @FXML
    private JFXTextField txtCP;

    @FXML
    private JFXComboBox<String> cmbGenero;

    @FXML
    private JFXTextField txtOcup;

    @FXML
    private JFXDatePicker dateFechaNac;

    @FXML
    private JFXTextField txtEdad;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXTextField txtCorreo;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    boolean modEdit = false;
    PacienteDAO pacDao = new PacienteDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI(){
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);

        EmpresaDAO empresaDAO = new EmpresaDAO(MySQL.getConnection());
        cmbEstado.setItems(MyUtils.getStates());
        cmbGenero.setItems(MyUtils.getGeneres());
        cmbEmpresa.setItems(empresaDAO.selectAll());

        btnNew.setOnAction(event -> {
            modEdit = false;
            disableButtons();
            disableFields(false);
            clean();
            setNextID();
        });

        btnAdd.setOnAction(event -> {
            savePac();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deletePaciente();
            clean();
        });

        btnEdit.setOnAction(event -> updatePac());

        MyUtils.stablishNumericRestriction(txtEdad);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void disableFields(boolean disable) {
        txtNombre.setDisable(disable);
        txtApellido.setDisable(disable);
        txtDireccion.setDisable(disable);
        txtColonia.setDisable(disable);
        txtCP.setDisable(disable);
        cmbEstado.setDisable(disable);
        txtCiudad.setDisable(disable);
        txtTelefono.setDisable(disable);
        cmbGenero.setDisable(disable);
        txtCorreo.setDisable(disable);
        txtOcup.setDisable(disable);
        dateFechaNac.setValue(null);
        txtEdad.setDisable(disable);
        cmbEmpresa.setDisable(disable);
    }

    private void clean(){
        txtID.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtColonia.setText("");
        txtCP.setText("");
        cmbEstado.getSelectionModel().clearSelection();
        txtCiudad.setText("");
        txtTelefono.setText("");
        cmbGenero.getSelectionModel().clearSelection();
        txtCorreo.setText("");
        txtOcup.setText("");
        dateFechaNac.setValue(null);
        txtEdad.setText("");
        cmbEmpresa.getSelectionModel().clearSelection();
        txtNombre.requestFocus();
    }

    private void selectPac(Paciente pac) {
        txtID.setText(pac.getId() + "");
        txtNombre.setText(pac.getNombre());
        txtApellido.setText(pac.getApellidos());
        txtDireccion.setText(pac.getDomicilio());
        txtColonia.setText(pac.getColonia());
        txtCP.setText(pac.getCodPost());
        cmbEstado.getSelectionModel().select(pac.getEstado());
        txtCiudad.setText(pac.getCiudad());
        txtTelefono.setText(pac.getTelefono());
        cmbGenero.getSelectionModel().select(pac.getGenero());
        txtCorreo.setText(pac.getEmail());
        txtOcup.setText(pac.getOcupacion());
        dateFechaNac.setValue(pac.getFechaNac().toLocalDate());
        txtEdad.setText(pac.getEdad() + "");
        cmbEmpresa.getSelectionModel().select(getIndexOfEmpresa(pac.getCveEmpresa()));
    }

    private void savePac() {
        Paciente pac = getPaciente();
        pacDao.insert(pac);
    }

    private void updatePac() {
        Paciente pac = getPaciente();
        pacDao.update(pac);
    }

    private void deletePaciente() {
        int id = Integer.valueOf(txtID.getText());
        Paciente pac = new Paciente();
        pac.setId(id);
        pacDao.delete(pac);
    }

    private void setNextID(){
        int nextID = pacDao.getNextID();
        txtID.setText(nextID+"");
    }

    private Paciente getPaciente() {
        int id = Integer.valueOf(txtID.getText());
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellido.getText().trim();
        String domicilio = txtDireccion.getText().trim();
        String colonia = txtColonia.getText().trim();
        String codPost = txtCP.getText().trim();
        String estado = cmbEstado.getSelectionModel().getSelectedItem();
        String ciudad = txtCiudad.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String genero = cmbGenero.getSelectionModel().getSelectedItem();
        String email = txtCorreo.getText().trim();
        String ocupacion = txtOcup.getText().trim();
        Date fechaNac = Date.valueOf(dateFechaNac.getValue());
        byte edad = Byte.valueOf(txtEdad.getText());
        String cveEmpresa = cmbEmpresa.getSelectionModel().getSelectedItem().getCveEmpresa();

        return new Paciente(id, nombre, apellidos, domicilio, colonia, codPost, estado, ciudad, telefono, genero, email,
                ocupacion, fechaNac, edad, cveEmpresa);
    }

    /**
     * Obtiene el index del ObservableList de Empresas, de una Empresa, mediante su clave
     *
     * @param cveEmpresa
     * @return
     */
    private int getIndexOfEmpresa(String cveEmpresa) {
        ObservableList<Empresa> items = cmbEmpresa.getItems();

        for (int i = 0; i < items.size(); i++) {
            Empresa p = items.get(i);
            if (p.getCveEmpresa().equals(cveEmpresa))
                return i;
        }
        return 0;
    }
}
