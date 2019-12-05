package model;

public class Laboratorio {
    private String cveLab, nombre, domicilio, ciudad, estado;

    public Laboratorio() {
    }

    public Laboratorio(String cveLab, String nombre, String domicilio, String ciudad, String estado) {
        this.cveLab = cveLab;
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.ciudad = ciudad;
        this.estado = estado;
    }

    public String getCveLab() {
        return cveLab;
    }

    public void setCveLab(String cveLab) {
        this.cveLab = cveLab;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
