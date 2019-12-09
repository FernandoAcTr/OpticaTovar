package modulos.pedidos;

import com.jfoenix.controls.*;
import connection.MySQL;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import modulos.catalogos.CatFormaPagoDAO;
import modulos.catalogos.FormaPago;
import modulos.empresas.Empresa;
import modulos.empresas.EmpresaDAO;
import modulos.facturas.TableBean;
import modulos.pacientes.Paciente;
import modulos.pacientes.PacienteDAO;
import modulos.pacientes.RecivePacientBase;
import modulos.pacientes.SearchPacientController;
import modulos.productos.ProductDAO;
import modulos.productos.Producto;
import modulos.productos.ReciveProductBase;
import modulos.productos.SearchProductController;
import modulos.trabajadores.Trabajador;
import modulos.trabajadores.TrabajadorDAO;
import utils.MyUtils;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class PedidosController implements Initializable, ReciveProductBase, RecivePacientBase {

    @FXML
    private JFXButton btnNew;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXTextField txtNoPedido, txtIdCliente;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDir;

    @FXML
    private JFXDatePicker dateFecha;

    @FXML
    private JFXComboBox<FormaPago> cmbFormaPago;

    @FXML
    private JFXComboBox<Trabajador> cmbTrab;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private Label lblEmpresa;

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
    private JFXTextArea txtObs;

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

    Pedido pedido = new Pedido();

    ProductDAO productDAO = new ProductDAO(MySQL.getConnection());
    PedidoDAO pedidoDAO = new PedidoDAO(MySQL.getConnection());
    boolean modSave = false;
    boolean modSelect = false;

    public PedidosController(Pedido pedido){
        if(pedido != null) {
            this.pedido = pedido;
            modSelect = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configUI();
        initTable();
        if (modSelect)
            selectAllData(pedido);
    }

    private void configUI() {
        btnNew.setOnAction(event -> addRow());
        btnDelete.setOnAction(event -> removeRow());

        CatFormaPagoDAO catFormaPagoDAO = new CatFormaPagoDAO(MySQL.getConnection());
        TrabajadorDAO trabajadorDAO = new TrabajadorDAO(MySQL.getConnection());

        cmbFormaPago.setItems(catFormaPagoDAO.selectAll());
        cmbStatus.setItems(MyUtils.getStatusOptions());
        cmbTrab.setItems(trabajadorDAO.selectMostrador());

        btnSave.setOnAction(event -> savePedido());

        btnAdd.setOnAction(event -> clearAndAdd());

        txtIdCliente.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.F3){
                SearchPacientController controller = new SearchPacientController(this);
                MyUtils.openWindow(getClass().getResource("/fxml/SceneSearchPacient.fxml"), "Buscar Paciente", controller);
            }
        });

        disableFields(true);
    }

    private void disableFields(boolean disable) {
        btnSave.setDisable(!modSave);
        txtNoPedido.setDisable(disable);
        txtIdCliente.setDisable(disable);
    }

    private void editableFields(boolean editable){
        txtObs.setEditable(editable);
        txtIdCliente.setEditable(editable);
        cmbStatus.setDisable(!editable);
        cmbTrab.setDisable(!editable);
        cmbFormaPago.setDisable(!editable);
        dateFecha.setEditable(false);
    }

    private void clearAndAdd() {
        tblProd.getItems().clear();
        txtNoPedido.setText(pedidoDAO.getNextFolio() + "");
        lblTotal.setText("$00.00");
        lblDesc.setText("$00.00");
        lblIva.setText("$00.00");
        lblSub.setText("$00.00");
        txtIdCliente.clear();
        txtObs.clear();
        lblDir.setText("");
        lblName.setText("");
        lblEmpresa.setText("");
        dateFecha.setValue(null);
        modSave = true;
        cmbStatus.getSelectionModel().clearSelection();
        cmbFormaPago.getSelectionModel().clearSelection();
        cmbTrab.getSelectionModel().clearSelection();
        disableFields(false);
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
            bean.setCosto(prod.getPrecio());
            bean.setDescripcion(prod.getDescripcion());
        }
    }

    /*
     * ***********************************************************************************************************************
     */

    private void calcularTotal() {
        double subTotal = 0, iva = 0, desc = 0, total;

        for (TableBean bean : tblProd.getItems()) {
            subTotal += bean.getSubTotal();
            bean.setTotalDescuento();
            desc += bean.getTotalDescuento();
        }

        iva = subTotal * 0.16;
        total = subTotal - desc + iva;

        pedido.setSubtotal(subTotal);
        pedido.setDescuento(desc);
        pedido.setImpuesto(iva);
        pedido.setTotal(total);

        lblSub.setText("$" + MyUtils.formatDouble(subTotal));
        lblIva.setText("$" + MyUtils.formatDouble(iva));
        lblDesc.setText("$" + MyUtils.formatDouble(desc));
        lblTotal.setText("$" + MyUtils.formatDouble(total));
    }

    private void selectPaciente(Paciente paciente){
        lblName.setText(paciente.getNombre());
        EmpresaDAO empresaDAO = new EmpresaDAO(MySQL.getConnection());
        lblEmpresa.setText(empresaDAO.selectByID(paciente.getCveEmpresa()).getNombre());
        lblDir.setText(paciente.getDomicilio());
    }

    private void savePedido() {
        int noPedido = Integer.valueOf(txtNoPedido.getText());
        String status = cmbStatus.getSelectionModel().getSelectedItem();
        Date fechaExp = Date.valueOf(dateFecha.getValue());
        String rfcTrab = cmbTrab.getSelectionModel().getSelectedItem().getRfc();
        int idCliente = Integer.valueOf(txtIdCliente.getText());

        pedido.setNoPedido(noPedido);
        pedido.setStatus(status);
        pedido.setRfcTrab(rfcTrab);
        pedido.setIdCliente(idCliente);
        pedido.setFecha(fechaExp);

        if (pedidoDAO.insert(pedido))
            if (pedidoDAO.registerProducts(tblProd.getItems(), noPedido)) {
                MyUtils.makeDialog("Venta Realizada", null,
                        "La venta fue registrada exitosamente", Alert.AlertType.INFORMATION).show();
                modSave = false;
                disableFields(true);
            }

    }

    @Override
    public void reciveProduct(Producto producto) {
        TableBean bean = tblProd.getSelectionModel().getSelectedItem();
        bean.setProducto(producto.getCodProd());
    }

    @Override
    public void recivePacient(Paciente paciente) {
        if(paciente != null) {
            txtIdCliente.setText(paciente.getId() + "");
            selectPaciente(paciente);
        }
    }

    private void selectAllData(Pedido pedido) {
        TrabajadorDAO trabajadorDAO = new TrabajadorDAO(MySQL.getConnection());
        PacienteDAO pacienteDAO = new PacienteDAO(MySQL.getConnection());
        EmpresaDAO empresaDAO = new EmpresaDAO(MySQL.getConnection());

        Paciente paciente = pacienteDAO.selectByID(pedido.getIdCliente()+"").get(0);
        Trabajador trabajador = trabajadorDAO.selectByRFC(pedido.getRfcTrab());

        cmbTrab.setValue(getTrabajadorByID(trabajador.getRfc()));
        cmbStatus.setValue(pedido.getStatus());

        lblSub.setText("$" + MyUtils.formatDouble(pedido.getSubtotal()));
        lblIva.setText("$" + MyUtils.formatDouble(pedido.getImpuesto()));
        lblDesc.setText("$" + MyUtils.formatDouble(pedido.getDescuento()));
        lblTotal.setText("$" + MyUtils.formatDouble(pedido.getTotal()));

        txtIdCliente.setText(paciente.getId()+"");
        lblDir.setText(paciente.getDomicilio());
        Empresa empresa = empresaDAO.selectByID(paciente.getCveEmpresa());
        lblEmpresa.setText(empresa.getNombre());
        dateFecha.setValue(pedido.getFecha().toLocalDate());
        txtNoPedido.setText(pedido.getNoPedido()+"");

        txtObs.setText(pedido.getObs());
        disableFields(false);
        editableFields(false);

        tblProd.setItems(pedidoDAO.selectProductByNoPed(pedido.getNoPedido()));
    }

    private Trabajador getTrabajadorByID(String rfc) {
        for (Trabajador prov : cmbTrab.getItems())
            if (prov.getRfc().equals(rfc))
                return prov;

        return null;
    }
}
