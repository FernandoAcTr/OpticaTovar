package model;

public class Producto {
    private String codProd;
    private float precio;
    private String linea;
    private int stock;
    private String color;
    private String descripcion;
    private String genero;
    private String cveMarca;
    private String cveTipo;

    public Producto() {
    }

    public Producto(String codProd, float precio, String linea, int stock, String color, String descripcion, String genero, String cveMarca, String cveTipo) {
        this.codProd = codProd;
        this.precio = precio;
        this.linea = linea;
        this.stock = stock;
        this.color = color;
        this.descripcion = descripcion;
        this.genero = genero;
        this.cveMarca = cveMarca;
        this.cveTipo = cveTipo;
    }

    public String getCodProd() {
        return codProd;
    }

    public void setCodProd(String codProd) {
        this.codProd = codProd;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCveMarca() {
        return cveMarca;
    }

    public void setCveMarca(String cveMarca) {
        this.cveMarca = cveMarca;
    }

    public String getCveTipo() {
        return cveTipo;
    }

    public void setCveTipo(String cveTipo) {
        this.cveTipo = cveTipo;
    }
}
