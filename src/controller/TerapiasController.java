package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.PacienteDAO;
import model.DAO.TerapiaDAO;
import model.DAO.TrabajadorDAO;
import model.Paciente;
import model.Terapia;
import model.Trabajador;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.util.ResourceBundle;

public class TerapiasController extends RecivePacientBase implements Initializable {

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnRefresh, btnDelete;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXButton btnSearchPacient, btnSearch;

    @FXML
    private JFXDatePicker dateDesde;

    @FXML
    private TableView<Terapia> tblTerapias;

    TerapiaDAO teraDAO = new TerapiaDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
        btnSearchPacient.setOnAction(event -> {
            SearchPacientController controller = new SearchPacientController(this);
            MyUtils.openWindow(getClass().getResource("/fxml/SceneSearchPacient.fxml"), "Buscar Paciente", controller);
        });

        btnNew.setOnAction(event -> {
            MyUtils.openWindow(getClass().getResource("/fxml/SceneNewTerapia.fxml"), "Registro de Terapias");
        });

        btnRefresh.setOnAction(event -> refreshTable());

        btnDelete.setOnAction(event -> {
            deleteTerapia();
            refreshTable();
        });

        tblTerapias.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Terapia terapia = tblTerapias.getSelectionModel().getSelectedItem();
                if (terapia != null)
                    showDetails(terapia);
            }
        });
    }

    private void initTable() {
        TableColumn<Terapia, Integer> colID = new TableColumn<>("Paciente");
        TableColumn<Terapia, String> colTera = new TableColumn<>("Terapeuta");
        TableColumn<Terapia, Date> colFecha = new TableColumn<>("Fecha");
        TableColumn<Terapia, Time> colHora = new TableColumn<>("Hora");

        colID.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        colID.setPrefWidth(140);

        colTera.setCellValueFactory(new PropertyValueFactory<>("rfcTerapeuta"));
        colTera.setPrefWidth(130);

        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colFecha.setPrefWidth(130);

        colHora.setCellValueFactory(new PropertyValueFactory<>("hora"));
        colHora.setPrefWidth(130);

        tblTerapias.getColumns().addAll(colID, colTera, colFecha, colHora);
    }

    private void deleteTerapia() {
        Terapia tera = tblTerapias.getSelectionModel().getSelectedItem();
        if (tera != null)
            teraDAO.delete(tera);
    }

    private void showDetails(Terapia terapia) {
        PacienteDAO pacienteDAO = new PacienteDAO(MySQL.getConnection());
        TrabajadorDAO trabajadorDAO = new TrabajadorDAO(MySQL.getConnection());

        Paciente paciente = pacienteDAO.selectByID(terapia.getIdCliente() + "").get(0);
        Trabajador terapeuta = trabajadorDAO.selectByRFC(terapia.getRfcTerapeuta());

        String message = "Paciente: " + paciente.getNombre() + " " + paciente.getApellidos() + "\n" +
                "Terapeuta: " + terapeuta.getNombre() + " " + terapeuta.getApellidos();

        MyUtils.makeDialog("Detalles", null, message, Alert.AlertType.INFORMATION).show();
    }

    private void refreshTable() {
        tblTerapias.setItems(teraDAO.selectAll());
    }

    @Override
    public void recivePacient(Paciente paciente) {
        txtID.setText(paciente.getId() + "");
    }
}
