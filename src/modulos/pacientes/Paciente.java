package modulos.pacientes;

import java.sql.Date;

public class Paciente {
    private int id;
    private String nombre;
    private String apellidos;
    private String domicilio;
    private String colonia;
    private String codPost;
    private String estado;
    private String ciudad;
    private String telefono;
    private String genero;
    private String email, ocupacion;
    private Date fechaNac;
    private byte edad;
    private String cveEmpresa;

    public Paciente() {
    }

    public Paciente(int id, String nombre, String apellidos, String domicilio, String colonia,
                    String codPost, String estado, String ciudad, String telefono, String genero, String email,
                    String ocupacion, Date fechaNac, byte edad, String cveEmpresa) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.domicilio = domicilio;
        this.colonia = colonia;
        this.codPost = codPost;
        this.estado = estado;
        this.ciudad = ciudad;
        this.telefono = telefono;
        this.genero = genero;
        this.email = email;
        this.ocupacion = ocupacion;
        this.fechaNac = fechaNac;
        this.edad = edad;
        this.cveEmpresa = cveEmpresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodPost() {
        return codPost;
    }

    public void setCodPost(String codPost) {
        this.codPost = codPost;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public byte getEdad() {
        return edad;
    }

    public void setEdad(byte edad) {
        this.edad = edad;
    }

    public String getCveEmpresa() {
        return cveEmpresa;
    }

    public void setCveEmpresa(String cveEmpresa) {
        this.cveEmpresa = cveEmpresa;
    }
}
