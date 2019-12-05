package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DAO.PacienteDAO;
import model.Paciente;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class BuscarPacienteController implements Initializable {

      @FXML
    private JFXButton btnNew, btnRefresh;

    @FXML
    private TableView<Paciente> tblPacientes;

    PacienteDAO pacDAO = new PacienteDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI(){
        btnNew.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneNuevoCliente.fxml"), "Nuevo Paciente"));
        btnRefresh.setOnAction(event -> refreshTable());
    }

    private void initTable() {
        TableColumn<Paciente, String> colName = new TableColumn<>("Nombre");
        TableColumn<Paciente, String> colId = new TableColumn<>("ID");
        TableColumn<Paciente, String> colApe = new TableColumn<>("Apellidos");
        TableColumn<Paciente, String> colEdad = new TableColumn<>("Edad");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(170);

        colApe.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApe.setPrefWidth(170);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(130);

        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colEdad.setPrefWidth(130);

        tblPacientes.getColumns().addAll(colId, colName, colApe, colEdad);
    }

    private void refreshTable() {
        tblPacientes.setItems(pacDAO.selectAll());
    }
}
