package model;

public class Proveedor {
    private String codProveedor, nombre, telefono, email, observaciones, pagWeb, fax, estado, cp, pais, colonia, rfc, ciudad, domicilio;
    private double descuento, credito;
    private int diasCred;

    public Proveedor() {
    }

    public Proveedor(String codProveedor, String nombre, String telefono, String email,
                     String observaciones, String pagWeb, String fax, String estado, String cp, String pais,
                     String colonia, String rfc, String ciudad, String domicilio, double descuento, double credito,
                     int diasCred) {

        this.codProveedor = codProveedor;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.observaciones = observaciones;
        this.pagWeb = pagWeb;
        this.fax = fax;
        this.estado = estado;
        this.cp = cp;
        this.pais = pais;
        this.colonia = colonia;
        this.rfc = rfc;
        this.ciudad = ciudad;
        this.domicilio = domicilio;
        this.descuento = descuento;
        this.credito = credito;
        this.diasCred = diasCred;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getPagWeb() {
        return pagWeb;
    }

    public void setPagWeb(String pagWeb) {
        this.pagWeb = pagWeb;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public int getDiasCred() {
        return diasCred;
    }

    public void setDiasCred(int diasCred) {
        this.diasCred = diasCred;
    }
}
