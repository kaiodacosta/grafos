package codigofonte.glazy.oo;

public class Vertice<T> {

    private T rotulo;
    private static int id = 0;
    private final int index;

    public Vertice(T rotulo) {
        this.rotulo = rotulo;
        this.index = id++;
    }

    public void imprime() {
        System.out.print(rotulo);
    }

    /**
     * @return the rotulo
     */
    public T getRotulo() {
        return rotulo;
    }

    /**
     * @param rotulo the rotulo to set
     */
    public void setRotulo(T rotulo) {
        this.rotulo = rotulo;
    }

    /**
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

}
