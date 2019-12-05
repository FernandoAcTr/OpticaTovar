package model;

public class TipoConsulta extends CatalogoBase {
    private float costo;

    public TipoConsulta() {
    }

    public TipoConsulta(String clave, String description, float costo) {
        super(clave, description);
        this.costo = costo;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
