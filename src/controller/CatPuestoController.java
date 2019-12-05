package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.DAO.CatPuestoDAO;
import model.Puesto;
import utils.MyUtils;

import java.net.URL;
import java.util.ResourceBundle;

public class CatPuestoController implements Initializable {

    @FXML
    private ListView<Puesto> tblPuesto;

    @FXML
    private TextField txtCve;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtSalario;

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    CatPuestoDAO catDao = new CatPuestoDAO(MySQL.getConnection());

    boolean modEdit = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
    }

    private void configUI() {
        disableFields(true);
        btnDelete.setDisable(true);
        btnEdit.setDisable(true);
        btnAdd.setDisable(true);
        tblPuesto.setItems(catDao.selectAll());

        btnNew.setOnAction(event -> {
            modEdit = false;
            disableButtons();
            disableFields(false);
            clean();
        });

        btnAdd.setOnAction(event -> {
            savePuesto();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deletePuesto();
            refreshTable();
            clean();
        });

        btnEdit.setOnAction(event -> {
            editPuesto();
            refreshTable();
        });

        tblPuesto.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {

                Puesto puesto = tblPuesto.getSelectionModel().getSelectedItem();
                if (puesto != null) {
                    selectPuesto(puesto);
                    modEdit = true;
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        MyUtils.stablishTextFiedlLimit(txtCve, 5);
        MyUtils.stablishNumericRestriction(txtSalario);

    }

    private void clean() {
        txtCve.setText("");
        txtDesc.setText("");
        txtSalario.setText("");
        txtCve.requestFocus();
    }

    private void disableFields(boolean disable) {
        txtDesc.setDisable(disable);
        txtSalario.setDisable(disable);
        txtCve.setDisable(modEdit);
    }

    private void disableButtons() {
        btnAdd.setDisable(modEdit);
        btnEdit.setDisable(!modEdit);
        btnDelete.setDisable(!modEdit);
    }

    private void savePuesto() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        float sal = Float.valueOf(txtSalario.getText());
        Puesto puesto = new Puesto(cve, desc, sal);
        catDao.insert(puesto);
        refreshTable();
    }

    private void editPuesto() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        float sal = Float.valueOf(txtSalario.getText());
        Puesto puesto = new Puesto(cve, desc, sal);
        catDao.update(puesto);
    }

    private void deletePuesto() {
        String cve = txtCve.getText();
        catDao.delete(new Puesto(cve, null, 0));
    }

    private void selectPuesto(Puesto puesto) {
        txtCve.setText(puesto.getClave());
        txtDesc.setText(puesto.getDescription());
        txtSalario.setText(MyUtils.trimFloat(puesto.getSalario()));
    }

    private void refreshTable() {
        tblPuesto.setItems(catDao.selectAll());
    }
}
