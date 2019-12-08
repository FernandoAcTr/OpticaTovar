package modulos.catalogos;

public abstract class CatalogoBase {
    protected String clave;
    protected String description;

    public CatalogoBase() {
    }

    public CatalogoBase(String clave, String description) {
        this.clave = clave;
        this.description = description;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
