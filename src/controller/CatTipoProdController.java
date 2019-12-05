package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.DAO.CatProdDAO;
import model.TipoProd;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CatTipoProdController implements Initializable {

    @FXML
    private ListView<TipoProd> tblTipo;

    @FXML
    private TextField txtCve;

    @FXML
    private TextField txtDesc;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    boolean modEdit = false;

    CatProdDAO catDao = new CatProdDAO(MySQL.getConnection());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        disableFields(true);
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        tblTipo.setItems(catDao.selectAll());

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
                TipoProd tipo = tblTipo.getSelectionModel().getSelectedItem();
                if (tipo != null) {
                    selectTipo(tipo);
                    modEdit = true;
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtCve, 5);
    }

    private void clean() {
        txtCve.setText("");
        txtDesc.setText("");
        txtCve.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtDesc.setDisable(disable);
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
        TipoProd tipo = new TipoProd(cve, desc);
        catDao.insert(tipo);
        refreshTable();
    }

    private void editTipo() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        TipoProd tipo = new TipoProd(cve, desc);
        catDao.update(tipo);
    }

    private void selectTipo(TipoProd tipo) {
        txtCve.setText(tipo.getClave());
        txtDesc.setText(tipo.getDescription());
    }

    private void deleteTipo() {
        String cve = txtCve.getText();
        catDao.delete(new TipoProd(cve, null));
    }

    private void refreshTable() {
        tblTipo.setItems(catDao.selectAll());
    }
}
