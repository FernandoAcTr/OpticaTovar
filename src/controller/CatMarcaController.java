package controller;

import com.jfoenix.controls.JFXButton;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.DAO.CatMarcaDAO;
import model.Marca;

import java.net.URL;
import java.util.ResourceBundle;

public class CatMarcaController implements Initializable {

    @FXML
    private ListView<Marca> tblMarca;

    @FXML
    private TextField txtCve;

    @FXML
    private TextField txtDesc;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnNew;

    CatMarcaDAO catDao = new CatMarcaDAO(MySQL.getConnection());

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
            saveMarca();
            clean();
            modEdit = false;
            disableFields(false);
            disableButtons();
        });

        btnDelete.setOnAction(event -> {
            deleteMarca();
            refreshTable();
            clean();
        });

        btnEdit.setOnAction(event -> {
            editMarca();
            refreshTable();
        });

        tblMarca.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Marca marca = tblMarca.getSelectionModel().getSelectedItem();
                if (marca != null) {
                    selectMarca(marca);
                    modEdit = true;
                    disableButtons();
                    disableFields(false);
                }
            }
        });

        txtCve.setOnKeyTyped(event -> {
            if (txtCve.getText().length() >= 5)
                event.consume();
        });
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

    private void saveMarca() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        Marca marca = new Marca(cve, desc);
        catDao.insert(marca);
        tblMarca.setItems(catDao.selectAll());
        refreshTable();
    }

    private void editMarca() {
        String cve = txtCve.getText().trim();
        String desc = txtDesc.getText().trim();
        Marca marca = new Marca(cve, desc);
        catDao.update(marca);
    }

    private void deleteMarca() {
        String cve = txtCve.getText();
        catDao.delete(new Marca(cve, null));
    }

    private void selectMarca(Marca marca) {
        txtCve.setText(marca.getClave());
        txtDesc.setText(marca.getDescription());
    }

    private void refreshTable() {
        tblMarca.setItems(catDao.selectAll());
    }
}
