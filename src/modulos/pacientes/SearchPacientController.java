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

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Esta clase sirve de cuadro de Dialogo para buscar un paciente. Culquier controlador que la invoque debe extender de
 * RecivePacientBase
 */
public class SearchPacientController implements Initializable {

    @FXML
    private JFXTextField txtID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableView<Paciente> tblPacientes;

    private RecivePacientBase reciver;
    private PacienteDAO pacDAO = new PacienteDAO(MySQL.getConnection());

    public SearchPacientController(RecivePacientBase reciver) {
        this.reciver = reciver;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
    }

    private void configUI() {
        tblPacientes.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Paciente pac = tblPacientes.getSelectionModel().getSelectedItem();
                if (pac != null) {
                    reciver.recivePacient(pac);
                    btnSearch.getScene().getWindow().hide();
                }
            }
        });

        btnSearch.setOnAction(event -> search());
    }

    private void initTable() {
        TableColumn<Paciente, String> colName = new TableColumn<>("Nombre");
        TableColumn<Paciente, String> colId = new TableColumn<>("ID");
        TableColumn<Paciente, String> colApe = new TableColumn<>("Apellidos");

        colName.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colName.setPrefWidth(100);

        colApe.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        colApe.setPrefWidth(250);

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(250);

        tblPacientes.getColumns().addAll(colId, colName, colApe);
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
