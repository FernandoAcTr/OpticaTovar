package modulos.facturas;

import java.sql.Date;

public class Factura {
    private int folio;
    private String factura;
    private Date fechaExp;
    private double subtotal, descuento, impuesto, total;
    private String codProv;

    public Factura() {
    }

    public Factura(int folio, String factura, Date fechaExp, double subtotal,
                   double descuento, double impuesto, double total, String codProv) {
        this.folio = folio;
        this.factura = factura;
        this.fechaExp = fechaExp;
        this.subtotal = subtotal;
        this.descuento = descuento;
        this.impuesto = impuesto;
        this.total = total;
        this.codProv = codProv;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public Date getFechaExp() {
        return fechaExp;
    }

    public void setFechaExp(Date fechaExp) {
        this.fechaExp = fechaExp;
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

    public String getCodProv() {
        return codProv;
    }

    public void setCodProv(String codProv) {
        this.codProv = codProv;
    }
}
