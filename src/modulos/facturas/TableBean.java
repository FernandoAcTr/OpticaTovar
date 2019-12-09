package modulos.facturas;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TableBean {
    private StringProperty producto;
    private IntegerProperty cantidad;
    private StringProperty descripcion;
    private DoubleProperty costo;
    private IntegerProperty descuento;
    private DoubleProperty subTotal;

    private double totalDescuento;

    public TableBean() {
        producto = new SimpleStringProperty();
        cantidad = new SimpleIntegerProperty();
        descripcion = new SimpleStringProperty();
        costo = new SimpleDoubleProperty();
        descuento = new SimpleIntegerProperty();
        subTotal = new SimpleDoubleProperty();
    }

    public TableBean(String producto, int cantidad, String descripcion, double costo, int descuento) {
        this.producto = new SimpleStringProperty(producto);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.costo = new SimpleDoubleProperty(costo);
        this.descuento = new SimpleIntegerProperty(descuento);

        double total = (costo - (costo * descuento /100)) * cantidad;
        
        this.subTotal = new SimpleDoubleProperty(total);
    }

    public String getProducto() {
        return producto.get();
    }

    public StringProperty productoProperty() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto.set(producto);
    }

    public int getCantidad() {
        return cantidad.get();
    }

    public IntegerProperty cantidadProperty() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad.set(cantidad);
        setSubtotal();
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public double getCosto() {
        return costo.get();
    }

    public DoubleProperty costoProperty() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo.set(costo);
        setSubtotal();
    }

    public int getDescuento() {
        return descuento.get();
    }

    public IntegerProperty descuentoProperty() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento.set(descuento);
        setTotalDescuento();
    }

    public double getSubTotal() {
        return subTotal.get();
    }

    public DoubleProperty subTotalProperty() {
        return subTotal;
    }

    private void setSubtotal() {
        double subTotal = costo.get() * cantidad.get();
        this.subTotal.set(subTotal);
    }

    public double getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento() {
        this.totalDescuento = costo.get() * cantidad.get() * descuento.get() /100;
    }

    public static ObservableList<TableBean> getDummyProductos(){
        ObservableList<TableBean> beans = FXCollections.observableArrayList();
        beans.add(new TableBean("F03", 3, "Muy bueno", 233.3, 10));
        beans.add(new TableBean("F04", 3, "Muy malo", 233.3, 15));
        return beans;
    }
}
