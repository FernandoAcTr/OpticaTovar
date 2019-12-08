package modulos.pacientes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class BuscarPacienteController implements Initializable {

    @FXML
    private JFXButton btnNew, btnRefresh, btnSearch;

    @FXML
    private TableView<Paciente> tblPacientes;

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtLastName;

    PacienteDAO pacDAO = new PacienteDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        refreshTable();
    }

    private void configUI() {
        btnNew.setOnAction(event -> {
            NewPacienteController controller = new NewPacienteController();
            MyUtils.openWindow(getClass().getResource("/fxml/SceneNuevoCliente.fxml"), "Nuevo Paciente", controller);
        });

        btnRefresh.setOnAction(event -> refreshTable());

        tblPacientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Paciente pac = tblPacientes.getSelectionModel().getSelectedItem();
                if (pac != null) {
                    NewPacienteController controller = new NewPacienteController(pac);
                    MyUtils.openWindow(getClass().getResource("/fxml/SceneNuevoCliente.fxml"), "Nuevo Paciente", controller);
                }
            }
        });

        btnSearch.setOnAction(event -> search());
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

    private void search() {
        ObservableList list;
        String name = txtName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String ID = txtID.getText().trim();

        if (ID.length() > 0)
            list = pacDAO.selectByID(ID);
        else if (name.length() > 0)
            if (lastName.length() > 0)
                list = pacDAO.selectByNameANDLastName(name, lastName);
            else
                list = pacDAO.selectByName(name);
        else if (lastName.length() > 0)
            list = pacDAO.selectByLastName(lastName);
        else
            list = pacDAO.selectAll();

        tblPacientes.setItems(list);
    }
}
