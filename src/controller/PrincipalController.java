package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import utils.MyUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private MenuItem mnuTypeMate;

    @FXML
    private MenuItem mnuPayForm;

    @FXML
    private MenuItem mnuMarca;

    @FXML
    private MenuItem mnuTypeProd;

    @FXML
    private MenuItem mnuTypeReview;

    @FXML
    private MenuItem mnuTypeJob;

    @FXML
    private MenuItem mnuTypeLDC;

    @FXML
    private MenuItem mnuClose, mnuEmpresas, mnuProveedores, mnuTrabajadores, mnuLabs;

    @FXML
    private JFXButton btnPacientes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configComponents();
    }

    private void configComponents() {
        btnPacientes.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneBuscarPaciente.fxml"), "Buscar Paciente"));
        mnuMarca.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatMarcas.fxml"), "Catalogo de Marcas"));
        mnuPayForm.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatFormaPago.fxml"), "Catalogo de Formas de Pago"));
        mnuTypeJob.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatPuesto.fxml"), "Catalogo de Puestos"));
        mnuTypeLDC.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatTipoLDC.fxml"), "Catalogo de Tipos de LDC"));
        mnuTypeMate.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatTipoMaterial.fxml"), "Catalogo de Tipos de Material"));
        mnuTypeProd.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatTipoProd.fxml"), "Catalogo de Tipos de Producto"));
        mnuTypeReview.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/CatTipoConsulta.fxml"), "Catalogo de Tipos de Consulta"));

        mnuEmpresas.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneEmpresa.fxml"), "Empresas con Convenio"));
        mnuProveedores.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneProveedor.fxml"), "Proveedores"));
        mnuTrabajadores.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneTrabajador.fxml"), "Trabajadores"));
        mnuLabs.setOnAction(event -> MyUtils.openWindow(getClass().getResource("/fxml/SceneLaboratorio.fxml"), "Trabajadores"));

        mnuClose.setOnAction(event -> {
            MySQL.Disconnect();
            System.exit(0);
        });

    }
}
