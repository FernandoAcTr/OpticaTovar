package modulos.pedidos;

import connection.MySQL;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import modulos.pacientes.Paciente;
import modulos.pacientes.RecivePacientBase;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modulos.pacientes.SearchPacientController;
import utils.MyUtils;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class BuscarPedidoController implements Initializable, RecivePacientBase {

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtID;

    @FXML
    private TableView<Pedido> tblPedidos;

    @FXML
    private TableColumn<Pedido, Integer> colNoPed;

    @FXML
    private TableColumn<Pedido, String> colStatus;

    @FXML
    private TableColumn<Pedido, Double> colTotal;

    PedidoDAO pedidoDAO = new PedidoDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        configUI();
    }

    private void configUI(){
        txtID.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.F3){
                SearchPacientController controller = new SearchPacientController(this);
                MyUtils.openWindow(getClass().getResource("/fxml/SceneSearchPacient.fxml"), "Buscar Paciente", controller);
            }
        });

        btnSearch.setOnAction(event -> {
            tblPedidos.setItems(pedidoDAO.selectByCliente(Integer.valueOf(txtID.getText())));
        });
    }

    private void initTable(){
        colNoPed.setCellValueFactory(new PropertyValueFactory<>("noPedido"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tblPedidos.setItems(pedidoDAO.selectAll());

        tblPedidos.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Pedido pedido = tblPedidos.getSelectionModel().getSelectedItem();
                if (pedido != null) {
                    PedidosController pedidosController = new PedidosController(pedido);
                    MyUtils.openWindow(getClass().getResource("/fxml/ScenePedido.fxml"), "Pedidos", pedidosController);
                }
            }
        });
    }

    @Override
    public void recivePacient(Paciente paciente) {
        if(paciente != null)
            txtID.setText(paciente.getId()+"");
    }
}
