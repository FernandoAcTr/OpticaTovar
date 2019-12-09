package modulos.facturas;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import modulos.productos.ProductDAO;
import modulos.productos.Producto;
import modulos.productos.ReciveProductBase;
import modulos.productos.SearchProductController;
import modulos.proveedores.Proveedor;
import modulos.proveedores.ProveedorDAO;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class FacturaController extends ReciveProductBase implements Initializable {

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
    private Label lblDiasCred;

    @FXML
    private JFXTextField txtFolio, txtFactura;

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
    private JFXComboBox<Proveedor> cmbProveedor;

    @FXML
    private Label lblSub;

    @FXML
    private Label lblDesc;

    @FXML
    private Label lblIva;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXDatePicker dateFech;

    FacturaDAO facturaDAO = new FacturaDAO(MySQL.getConnection());
    ProductDAO productDAO = new ProductDAO(MySQL.getConnection());

    Factura factura = new Factura();
    boolean modSave = false;
    boolean modSelect = false;

    public FacturaController(Factura factura) {
        if (factura != null) {
            this.factura = factura;
            modSelect = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTable();
        configUI();
        if (modSelect)
            selectAllData(factura);
    }

    private void configUI() {
        btnNew.setOnAction(event -> addRow());
        btnDelete.setOnAction(event -> removeRow());

        ProveedorDAO proveedorDAO = new ProveedorDAO(MySQL.getConnection());
        cmbProveedor.setItems(proveedorDAO.selectAll());

        cmbProveedor.valueProperty().addListener((observable, oldValue, newValue) -> selectProv(newValue));

        btnSave.setOnAction(event -> saveFactura());

        btnAdd.setOnAction(event -> clearAndAdd());

        disableFields(true);
    }

    private void disableFields(boolean disable) {
        btnSave.setDisable(!modSave);
        txtFactura.setDisable(disable);
        cmbProveedor.setDisable(disable);
    }

    /**
     * *******************************Metodos para configuracion de TableView *******************************
     * ********************************************************************************************************
     */

    private void initTable() {
        tblProd.getSelectionModel().setCellSelectionEnabled(true);

        colProd.setCellValueFactory(param -> param.getValue().productoProperty());
        colQuantity.setCellValueFactory(param -> param.getValue().cantidadProperty());
        colCosto.setCellValueFactory(param -> param.getValue().costoProperty());
        colDescrip.setCellValueFactory(param -> param.getValue().descripcionProperty());
        colDescuento.setCellValueFactory(param -> param.getValue().descuentoProperty());
        colTotal.setCellValueFactory(param -> param.getValue().subTotalProperty());

        colProd.setCellFactory(TextFieldTableCell.forTableColumn());
        colQuantity.setCellFactory(createNumberCellFactory());
        colCosto.setCellFactory(createNumberCellFactory());
        colDescrip.setCellFactory(TextFieldTableCell.forTableColumn());
        colDescuento.setCellFactory(createNumberCellFactory());

        colProd.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setProducto(data.getNewValue());
            onCommitProduct(bean);
        });

        colQuantity.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setCantidad(data.getNewValue().intValue());
            calcularTotal();
        });

        colCosto.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setCosto(data.getNewValue().doubleValue());
            calcularTotal();
        });

        colDescrip.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setDescripcion(data.getNewValue());
        });

        colDescuento.setOnEditCommit(data -> {
            TableBean bean = data.getRowValue();
            bean.setDescuento(data.getNewValue().intValue());
            calcularTotal();
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
                    break;
                case F3:
                    TablePosition pos = tblProd.getFocusModel().getFocusedCell();
                    if (pos.getColumn() == 0) {
                        SearchProductController controller = new SearchProductController(this);
                        MyUtils.openWindow(getClass().getResource("/fxml/SceneSearchProduct.fxml"),
                                "Buscar Producto", controller);
                    }
            }
        });
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

    private void removeRow() {
        tblProd.getItems().remove(tblProd.getSelectionModel().getSelectedItem());
        tblProd.getSelectionModel().clearSelection();
    }

    private void onCommitProduct(TableBean bean) {
        Producto prod = productDAO.selectProductByID(bean.getProducto());
        if (prod == null) {
            MyUtils.makeDialog("Producto NO EXISTENTE", "",
                    "El producto no existe en almacen", Alert.AlertType.WARNING).show();
            removeRow();
            addRow();
        } else {
            bean.setDescripcion(prod.getDescripcion());
        }
    }

    /*
     * ***********************************************************************************************************************
     */

    private void selectProv(Proveedor proveedor) {
        if (proveedor != null) {
            lblDir.setText(proveedor.getDomicilio());
            lblCP.setText(proveedor.getCp());
            lblDiasCred.setText(proveedor.getDiasCred() + "");
            lblRFC.setText(proveedor.getRfc());
        }
    }

    private void saveFactura() {
        int folio = Integer.valueOf(txtFolio.getText());
        String fact = txtFactura.getText().trim();
        Date fechaExp = Date.valueOf(dateFech.getValue());
        String codProd = cmbProveedor.getSelectionModel().getSelectedItem().getCodProveedor();

        factura.setFolio(folio);
        factura.setCodProv(codProd);
        factura.setFactura(fact);
        factura.setFechaExp(fechaExp);

        if (facturaDAO.insert(factura))
            if (facturaDAO.registerProducts(tblProd.getItems(), folio)) {
                MyUtils.makeDialog("Compra Realizada", null,
                        "La compra fue registrada exitosamente", Alert.AlertType.INFORMATION).show();
                modSave = false;
                disableFields(true);
            }

    }

    private void clearAndAdd() {
        tblProd.getItems().clear();
        txtFolio.setText(facturaDAO.getNextFolio() + "");
        txtFactura.setText("");
        lblTotal.setText("$00.00");
        lblDesc.setText("$00.00");
        lblIva.setText("$00.00");
        lblSub.setText("$00.00");
        lblRFC.setText("");
        lblCP.setText("");
        lblDir.setText("");
        lblDiasCred.setText("");
        dateFech.setValue(null);
        modSave = true;
        cmbProveedor.getSelectionModel().clearSelection();
        disableFields(false);
    }

    private void calcularTotal() {
        double subTotal = 0, iva = 0, desc = 0, total;

        for (TableBean bean : tblProd.getItems()) {
            subTotal += bean.getSubTotal();
            bean.setTotalDescuento();
            desc += bean.getTotalDescuento();
        }

        iva = subTotal * 0.16;
        total = subTotal - desc + iva;

        factura.setSubtotal(subTotal);
        factura.setDescuento(desc);
        factura.setImpuesto(iva);
        factura.setTotal(total);

        lblSub.setText("$" + MyUtils.formatDouble(subTotal));
        lblIva.setText("$" + MyUtils.formatDouble(iva));
        lblDesc.setText("$" + MyUtils.formatDouble(desc));
        lblTotal.setText("$" + MyUtils.formatDouble(total));
    }

    @Override
    public void reciveProduct(Producto producto) {
        TableBean bean = tblProd.getSelectionModel().getSelectedItem();
        bean.setProducto(producto.getCodProd());
    }

    private void selectAllData(Factura factura) {
        ProveedorDAO proveedorDAO = new ProveedorDAO(MySQL.getConnection());
        Proveedor proveedor = proveedorDAO.selectProvByID(factura.getCodProv());

        selectProv(proveedor);
        cmbProveedor.getSelectionModel().select(getProveedorByID(proveedor.getCodProveedor()));

        txtFolio.setText(factura.getFolio() + "");
        txtFactura.setText(factura.getFactura());
        dateFech.setValue(factura.getFechaExp().toLocalDate());

        lblSub.setText("$" + MyUtils.formatDouble(factura.getSubtotal()));
        lblIva.setText("$" + MyUtils.formatDouble(factura.getImpuesto()));
        lblDesc.setText("$" + MyUtils.formatDouble(factura.getDescuento()));
        lblTotal.setText("$" + MyUtils.formatDouble(factura.getTotal()));

        disableFields(false);
        txtFactura.setEditable(false);
        txtFolio.setEditable(false);
        cmbProveedor.setDisable(false);
        btnAdd.setDisable(true);
        btnNew.setVisible(false);
        btnDelete.setVisible(false);

        tblProd.setItems(facturaDAO.selectProductByFact(factura.getFolio()));
    }

    private Proveedor getProveedorByID(String cod) {
        for (Proveedor prov : cmbProveedor.getItems())
            if (prov.getCodProveedor().equals(cod))
                return prov;

        return null;
    }
}
