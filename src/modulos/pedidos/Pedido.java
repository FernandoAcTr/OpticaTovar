package modulos.pedidos;

import java.sql.Date;

public class Pedido {
    private int noPedido;
    private String status;
    private Date fecha;
    private double subtotal, descuento, impuesto, total;
    private String obs;
    private String rfcTrab;
    private int idCliente;

    public Pedido() {
    }

    public Pedido(int noPedido, String status, Date fecha, double subtotal, double descuento,
                  double impuesto, double total, String obs, String rfcTrab, int idCliente) {
        this.noPedido = noPedido;
        this.status = status;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.total = total;
        this.obs = obs;
        this.rfcTrab = rfcTrab;
        this.idCliente = idCliente;
    }

    public int getNoPedido() {
        return noPedido;
    }

    public void setNoPedido(int noPedido) {
        this.noPedido = noPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getRfcTrab() {
        return rfcTrab;
    }

    public void setRfcTrab(String rfcTrab) {
        this.rfcTrab = rfcTrab;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
