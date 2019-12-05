package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.DAO.CatConsultaDAO;
import model.TipoConsulta;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CatConsultaController implements Initializable {

    @FXML
    private ListView<TipoConsulta> tblTipo;

    @FXML
    private TextField txtCve;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtCosto;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    CatConsultaDAO catDao = new CatConsultaDAO(MySQL.getConnection());

    boolean modEdit = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        refreshTable();
    }

    private void configUI() {
        disableFields(true);
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
            saveTipo();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteTipo();
            refreshTable();
            clean();
        });

        btnEdit.setOnAction(event -> {
            editTipo();
            refreshTable();
        });

        tblTipo.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TipoConsulta tipo = tblTipo.getSelectionModel().getSelectedItem();
                if (tipo != null) {
                    selectTipo(tipo);
                    modEdit = true;
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtCve, 5);
        MyUtils.stablishNumericRestriction(txtCosto);
    }

    private void clean() {
        txtCve.setText("");
        txtDesc.setText("");
        txtCosto.setText("");
        txtCve.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtDesc.setDisable(disable);
        txtCosto.setDisable(disable);
        txtCve.setDisable(modEdit);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void saveTipo() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        float cos = Float.valueOf(txtCosto.getText());
        TipoConsulta tipo = new TipoConsulta(cve, desc, cos);
        catDao.insert(tipo);
        refreshTable();
    }

    private void editTipo() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        float cos = Float.valueOf(txtCosto.getText());
        TipoConsulta tipo = new TipoConsulta(cve, desc, cos);
        catDao.update(tipo);
    }

    private void selectTipo(TipoConsulta tipo) {
        txtCve.setText(tipo.getClave());
        txtDesc.setText(tipo.getDescription());
        txtCosto.setText(MyUtils.trimFloat(tipo.getCosto()));
    }

    private void deleteTipo() {
        String cve = txtCve.getText();
        catDao.delete(new TipoConsulta(cve, null, 0));
    }

    private void refreshTable() {
        tblTipo.setItems(catDao.selectAll());
    }

}
