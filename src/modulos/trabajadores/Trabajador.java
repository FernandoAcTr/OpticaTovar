package modulos.trabajadores;

import java.sql.Date;

public class Trabajador {
    private String rfc, genero, nombre, apellidos, domicilio;
    private Date fechaNac;
    private String ciudad, estado, telefono;
    private Date fechaContra;
    private String cvePuesto;

    public Trabajador() {
    }

    public Trabajador(String rfc, String genero, String nombre, String apellidos,
                      String domicilio, Date fechaNac, String ciudad, String estado,
                      String telefono, Date fechaContra, String cvePuesto) {
        this.rfc = rfc;
        this.genero = genero;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.fechaNac = fechaNac;
        this.ciudad = ciudad;
        this.estado = estado;
        this.telefono = telefono;
        this.fechaContra = fechaContra;
        this.cvePuesto = cvePuesto;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaContra() {
        return fechaContra;
    }

    public void setFechaContra(Date fechaContra) {
        this.fechaContra = fechaContra;
    }

    public String getCvePuesto() {
        return cvePuesto;
    }

    public void setCvePuesto(String cvePuesto) {
        this.cvePuesto = cvePuesto;
    }

    @Override
    public String toString() {
        return nombre + " " + apellidos;
    }
}
