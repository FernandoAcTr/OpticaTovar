package modulos.facturas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class FacturaController implements Initializable {

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Label lblDir;

    @FXML
    private Label lblCP;

    @FXML
    private Label lblRFC;

    @FXML
    private Label ldlDiasCred;

    @FXML
    private JFXTextField txtFolio;

    @FXML
    private TableView<TableBean> tblProd;

    @FXML
    private TableColumn<TableBean, String> colProd;

    @FXML
    private TableColumn<TableBean, Number> colQuantity;

    @FXML
    private TableColumn<TableBean, String> colDescrip;

    @FXML
    private TableColumn<TableBean, Number> colCosto;

    @FXML
    private TableColumn<TableBean, Number> colDescuento;

    @FXML
    private TableColumn<TableBean, Number> colTotal;

    @FXML
    private Label ibiSub;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblIva;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnRegistrar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        configUI();
    }

    private void initTable() {
        tblProd.getSelectionModel().setCellSelectionEnabled(true);

        colProd.setCellValueFactory(param -> param.getValue().productoProperty());
        colQuantity.setCellValueFactory(param -> param.getValue().cantidadProperty());
        colCosto.setCellValueFactory(param -> param.getValue().costoProperty());
        colDescrip.setCellValueFactory(param -> param.getValue().descripcionProperty());
        colDescuento.setCellValueFactory(param -> param.getValue().descuentoProperty());
        colTotal.setCellValueFactory(param -> param.getValue().totalProperty());

        colProd.setCellFactory(TextFieldTableCell.forTableColumn());
        colQuantity.setCellFactory(createNumberCellFactory());
        colCosto.setCellFactory(createNumberCellFactory());
        colDescrip.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescuento.setCellFactory(createNumberCellFactory());

        colProd.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setProducto(data.getNewValue());
        });

        colQuantity.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setCantidad(data.getNewValue().intValue());
        });

        colCosto.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setCosto(data.getNewValue().doubleValue());
        });

        colDescrip.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setDescripcion(data.getNewValue());
        });

        colDescuento.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setDescuento(data.getNewValue().intValue());
        });

        tblProd.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

            // switch to edit mode on keypress, but only if we aren't already in edit mode
            if (tblProd.getEditingCell() == null) {
                if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                    TablePosition focusedCellPosition = tblProd.getFocusModel().getFocusedCell();
                    tblProd.edit(focusedCellPosition.getRow(), focusedCellPosition.getTableColumn());
                }
            }

        });

        tblProd.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            switch (event.getCode()) {
                case TAB:
                    tblProd.getFocusModel().focusRightCell();
                case F3:
                    System.out.println("has Presionado F3");
            }
        });

        tblProd.setItems(TableBean.getDummyProductos());
    }

    private void configUI() {
        btnNew.setOnAction(event -> addRow());
        btnDelete.setOnAction(event -> remove());
    }

    /**
     * Number cell factory which converts strings to numbers and vice versa.
     *
     * @return
     */
    private Callback<TableColumn<TableBean, Number>, TableCell<TableBean, Number>> createNumberCellFactory() {

        Callback<TableColumn<TableBean, Number>, TableCell<TableBean, Number>> factory = TextFieldTableCell.forTableColumn(new StringConverter<Number>() {

            @Override
            public Number fromString(String string) {
                return Double.parseDouble(string);
            }

            @Override
            public String toString(Number object) {
                return object.toString();
            }
        });

        return factory;
    }

    private void addRow() {
        // get current position
        TablePosition pos = tblProd.getFocusModel().getFocusedCell();

        // clear current selection
        tblProd.getSelectionModel().clearSelection();

        // create new record and add it to the model
        TableBean data = new TableBean();
        tblProd.getItems().add(data);

        // get last row
        int row = tblProd.getItems().size() - 1;
        tblProd.getSelectionModel().select(row, pos.getTableColumn());

        // scroll to new row
        tblProd.scrollTo(data);
    }

    private void remove() {
        tblProd.getItems().remove(tblProd.getSelectionModel().getSelectedItem());
        tblProd.getSelectionModel().clearSelection();
    }
}
