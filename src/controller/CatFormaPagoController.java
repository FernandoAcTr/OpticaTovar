package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.DAO.CatFormaPagoDAO;
import model.FormaPago;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CatFormaPagoController implements Initializable {

    @FXML
    private ListView<FormaPago> tblFormas;

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

    CatFormaPagoDAO catDao = new CatFormaPagoDAO(MySQL.getConnection());

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
            savePayForm();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deletePayForm();
            refreshTable();
            clean();
        });

        btnEdit.setOnAction(event -> {
            editPayForm();
            refreshTable();
        });

        tblFormas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FormaPago payForm = tblFormas.getSelectionModel().getSelectedItem();
                if (payForm != null) {
                    selectPayForm(payForm);
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

    private void savePayForm() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        FormaPago payForm = new FormaPago(cve, desc);
        catDao.insert(payForm);
        refreshTable();
    }

    private void editPayForm() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        FormaPago payForm = new FormaPago(cve, desc);
        catDao.update(payForm);
    }

    private void deletePayForm() {
        String cve = txtCve.getText();
        catDao.delete(new FormaPago(cve, null));
    }

    private void selectPayForm(FormaPago payForm) {
        txtCve.setText(payForm.getClave());
        txtDesc.setText(payForm.getDescription());
    }

    private void refreshTable() {
        tblFormas.setItems(catDao.selectAll());
    }

}
