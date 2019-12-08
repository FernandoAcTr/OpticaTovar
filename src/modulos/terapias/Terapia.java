package modulos.terapias;

import java.sql.Date;
import java.sql.Time;

public class Terapia {
    private int idCliente;
    private Date fecha;
    private Time hora;
    private String rfcTerapeuta;

    public Terapia() {
    }

    public Terapia(int idCliente, Date fecha, Time hora, String rfcTerapeuta) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.hora = hora;
        this.rfcTerapeuta = rfcTerapeuta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getRfcTerapeuta() {
        return rfcTerapeuta;
    }

    public void setRfcTerapeuta(String rfcTerapeuta) {
        this.rfcTerapeuta = rfcTerapeuta;
    }
}
