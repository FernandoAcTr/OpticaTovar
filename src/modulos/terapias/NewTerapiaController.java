package modulos.terapias;

import com.jfoenix.controls.*;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import modulos.pacientes.RecivePacientBase;
import modulos.pacientes.SearchPacientController;
import modulos.terapias.TerapiaDAO;
import modulos.trabajadores.TrabajadorDAO;
import modulos.pacientes.Paciente;
import modulos.terapias.Terapia;
import modulos.trabajadores.Trabajador;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;

public class NewTerapiaController implements Initializable, RecivePacientBase {
    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXTimePicker timePicker;

    @FXML
    private JFXComboBox<Trabajador> cmbTerapeuta;

    @FXML
    private JFXButton btnSave;

    TerapiaDAO teraDAO = new TerapiaDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        TrabajadorDAO trabDAO = new TrabajadorDAO(MySQL.getConnection());
        cmbTerapeuta.setItems(trabDAO.selectTerapeutas());

        btnSearch.setOnAction(event -> {
            SearchPacientController controller = new SearchPacientController(this);
            MyUtils.openWindow(getClass().getResource("/fxml/SceneSearchPacient.fxml"), "Buscar Paciente", controller);
        });

        btnSave.setOnAction(event -> {
            saveTerapia();
            close();
        });
    }

    @Override
    public void recivePacient(Paciente paciente) {
        txtID.setText(paciente.getId() + "");
    }

    private void saveTerapia(){
        int id = Integer.valueOf(txtID.getText());
        Date fecha = Date.valueOf(datePicker.getValue());
        Time time = Time.valueOf(timePicker.getValue());
        String rfc = cmbTerapeuta.getSelectionModel().getSelectedItem().getRfc();

        Terapia terapia = new Terapia(id, fecha, time, rfc);
        teraDAO.insert(terapia);
    }

    private void close(){
        btnSearch.getScene().getWindow().hide();
    }
}
