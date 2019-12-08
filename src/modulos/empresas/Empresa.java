package modulos.empresas;

public class Empresa {
    private String cveEmpresa, nombre, status;
    private double descuento;
    private String email, observaciones, rfc, telefono;

    public Empresa() {
    }

    public Empresa(String cveEmpresa, String nombre, String status, double descuento, String email, String observaciones, String rfc, String telefono) {
        this.cveEmpresa = cveEmpresa;
        this.nombre = nombre;
        this.status = status;
        this.descuento = descuento;
        this.email = email;
        this.observaciones = observaciones;
        this.rfc = rfc;
        this.telefono = telefono;
    }

    public String getCveEmpresa() {
        return cveEmpresa;
    }

    public void setCveEmpresa(String cveEmpresa) {
        this.cveEmpresa = cveEmpresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
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

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
