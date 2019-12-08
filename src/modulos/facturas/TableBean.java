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
    private DoubleProperty total;

    public TableBean() {
        producto = new SimpleStringProperty();
        cantidad = new SimpleIntegerProperty();
        descripcion = new SimpleStringProperty();
        costo = new SimpleDoubleProperty();
        descuento = new SimpleIntegerProperty();
        total = new SimpleDoubleProperty();
    }

    public TableBean(String producto, int cantidad, String descripcion, double costo, int descuento) {
        this.producto = new SimpleStringProperty(producto);
        this.cantidad = new SimpleIntegerProperty(cantidad);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.costo = new SimpleDoubleProperty(costo);
        this.descuento = new SimpleIntegerProperty(descuento);

        double total = (costo - (costo * descuento /100)) * cantidad;
        
        this.total = new SimpleDoubleProperty(total);
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
        setTotal();
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
        setTotal();
    }

    public int getDescuento() {
        return descuento.get();
    }

    public IntegerProperty descuentoProperty() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento.set(descuento);
        setTotal();
    }

    public double getTotal() {
        return total.get();
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public void setTotal() {
        double total = (costo.get() - (costo.get() * descuento.get() /100)) * cantidad.get();
        this.total.set(total);
    }

    public static ObservableList<TableBean> getDummyProductos(){
        ObservableList<TableBean> beans = FXCollections.observableArrayList();
        beans.add(new TableBean("F03", 3, "Muy bueno", 233.3, 10));
        beans.add(new TableBean("F04", 3, "Muy malo", 233.3, 15));
        return beans;
    }
}
