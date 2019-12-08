package modulos.trabajadores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import modulos.catalogos.CatPuestoDAO;
import modulos.trabajadores.TrabajadorDAO;
import modulos.catalogos.Puesto;
import modulos.trabajadores.Trabajador;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class TrabajadorController implements Initializable {

    @FXML
    private TableView<Trabajador> tblTrabajador;

    @FXML
    private JFXTextField txtNombre;

    @FXML
    private JFXTextField txtApellidos;

    @FXML
    private JFXComboBox<String> cmbGenero;

    @FXML
    private JFXTextField txtRFC;

    @FXML
    private JFXTextField txtDomicilio;

    @FXML
    private JFXTextField txtCiudad;

    @FXML
    private JFXComboBox<String> cmbEstado;

    @FXML
    private JFXComboBox<Puesto> cmbPuesto;

    @FXML
    private JFXTextField txtTelefono;

    @FXML
    private JFXDatePicker dateFechaNac;

    @FXML
    private JFXDatePicker dateFechaCont;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    boolean modEdit = false;

    TrabajadorDAO trabDao = new TrabajadorDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
        CatPuestoDAO catPuestoDAO = new CatPuestoDAO(MySQL.getConnection());

        cmbEstado.setItems(MyUtils.getStates());
        cmbGenero.setItems(MyUtils.getGeneres());
        cmbPuesto.setItems(catPuestoDAO.selectAll());
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
            saveTrab();
            refreshTable();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteTrab();
            clean();
            refreshTable();
        });

        btnEdit.setOnAction(event -> {
            editTrab();
            refreshTable();
        });

        tblTrabajador.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Trabajador trab = tblTrabajador.getSelectionModel().getSelectedItem();
                if (trab != null) {
                    modEdit = true;
                    selectTrab(trab);
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtRFC, 13);
    }

    private void initTable() {
        TableColumn<Trabajador, String> colName = new TableColumn<>("Nombre");
        TableColumn<Trabajador, String> colApe = new TableColumn<>("Apellidos");
        TableColumn<Trabajador, String> colGenero = new TableColumn<>("Genero");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(150);

        colApe.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApe.setPrefWidth(150);

        colGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        colGenero.setPrefWidth(100);

        tblTrabajador.getColumns().addAll(colName, colApe, colGenero);
    }

    private void clean() {
        txtRFC.setText("");
        cmbGenero.getSelectionModel().clearSelection();
        txtNombre.setText("");
        txtApellidos.setText("");
        txtDomicilio.setText("");
        txtCiudad.setText("");
        cmbEstado.getSelectionModel().clearSelection();
        txtTelefono.setText("");
        cmbPuesto.getSelectionModel().clearSelection();
        dateFechaNac.setValue(null);
        dateFechaCont.setValue(null);
        txtRFC.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtRFC.setDisable(modEdit);
        cmbGenero.setDisable(disable);
        txtNombre.setDisable(disable);
        txtApellidos.setDisable(disable);
        txtDomicilio.setDisable(disable);
        txtCiudad.setDisable(disable);
        cmbEstado.setDisable(disable);
        txtTelefono.setDisable(disable);
        cmbPuesto.setDisable(disable);
        dateFechaNac.setDisable(disable);
        dateFechaCont.setDisable(disable);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void saveTrab() {
        Trabajador trab = getTrabajador();
        trabDao.insert(trab);
    }

    private void editTrab() {
        Trabajador trab = getTrabajador();
        trabDao.update(trab);
    }

    private void selectTrab(Trabajador trab) {
        String rfc = trab.getRfc();
        String genero = trab.getGenero();
        String nombre = trab.getNombre();
        String apellidos = trab.getApellidos();
        String domicilio = trab.getDomicilio();
        String ciudad = trab.getCiudad();
        String estado = trab.getEstado();
        String tel = trab.getTelefono();
        String cvePuesto = trab.getCvePuesto();
        Date fechaNac = trab.getFechaNac();
        Date fechaContra = trab.getFechaContra();

        txtRFC.setText(rfc);
        cmbGenero.getSelectionModel().select(genero);
        txtNombre.setText(nombre);
        txtApellidos.setText(apellidos);
        txtDomicilio.setText(domicilio);
        txtCiudad.setText(ciudad);
        cmbEstado.getSelectionModel().select(estado);
        txtTelefono.setText(tel);
        cmbPuesto.getSelectionModel().select(getIndexOfPuesto(cvePuesto));
        dateFechaNac.setValue(fechaNac.toLocalDate());
        dateFechaCont.setValue(fechaContra.toLocalDate());
    }

    private void deleteTrab() {
        String rfc = txtRFC.getText();
        Trabajador trab = new Trabajador();
        trab.setRfc(rfc);
        trabDao.delete(trab);
    }

    private Trabajador getTrabajador() {
        String rfc = txtRFC.getText().trim();
        String genero = cmbGenero.getSelectionModel().getSelectedItem();
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String domicilio = txtDomicilio.getText().trim();
        String ciudad = txtCiudad.getText().trim();
        String estado = cmbEstado.getSelectionModel().getSelectedItem();
        String tel = txtTelefono.getText().trim();
        String cvePuesto = cmbPuesto.getSelectionModel().getSelectedItem().getClave();
        Date fechaNac = Date.valueOf(dateFechaNac.getValue());
        Date fechaContra = Date.valueOf(dateFechaCont.getValue());

        return new Trabajador(rfc, genero, nombre, apellidos, domicilio, fechaNac, ciudad, estado, tel, fechaContra, cvePuesto);
    }

    private void refreshTable() {
        tblTrabajador.setItems(trabDao.selectAll());
    }

    /**
     * Obtiene el index del ObservableList de un objeto Puesto, mediante su clave
     * @param cvePuesto
     * @return
     */
    private int getIndexOfPuesto(String cvePuesto) {
        ObservableList<Puesto> items = cmbPuesto.getItems();

        for (int i = 0; i < items.size(); i++) {
            Puesto p = items.get(i);
            if (p.getClave().equals(cvePuesto))
                return i;
        }
        return 0;
    }
}
