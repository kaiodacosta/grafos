package codigofonte.glazy.matriz;

public class Vertice {
    Integer numero;
    boolean descoberto;

    public Vertice(Integer numero) {
        this.numero = numero;
        this.descoberto = false;
    }

    public Vertice(Integer numero, boolean descoberto) {
        this.numero = numero;
        this.descoberto = descoberto;
    }


    public Integer getNumero() {
        return numero;
    }

    public boolean isDescoberto() {
        return descoberto;
    }

    public void setDescoberto(boolean descoberto) {
        this.descoberto = descoberto;
    }
}
