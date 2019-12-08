package modulos.catalogos;

public class Puesto extends CatalogoBase{
    private float salario;

    public Puesto() {
    }

    public Puesto(String clave, String description, float salario) {
        super(clave, description);
        this.salario = salario;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    @Override
    public String toString() {
        return description;
    }
}
