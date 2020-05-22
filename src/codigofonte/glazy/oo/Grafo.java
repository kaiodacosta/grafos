package codigofonte.glazy.oo;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Grafo<T extends Comparable<T>, E extends Comparable<E>> {

    private List<Vertice<T>> listaDeVertices;
    private List<Aresta<T, E>> listaDeArestas;

    public Grafo() {
        listaDeVertices = new LinkedList<>();
        listaDeArestas = new LinkedList<>();
    }

    public Vertice<T> buscaVertice(T rotulo) {
        for (Vertice<T> vertice : listaDeVertices) {
            if (rotulo.compareTo(vertice.getRotulo()) == 0) {
                return vertice;
            }
        }
        return null;
    }

    public void adicionaVertice(Vertice<T> vertice) {
        listaDeVertices.add(vertice);
    }

    public void adicionaAresta(Vertice<T> origem, Vertice<T> destino, E info) {
        listaDeArestas.add(new Aresta<>(origem, destino, info));
    }

    public boolean isAresta(T origem, T destino) {
        return isAresta(this.buscaVertice(origem), this.buscaVertice(destino));
    }

    private boolean isAresta(Vertice<T> origem, Vertice<T> destino) {
        for (Aresta<T, E> aresta : listaDeArestas) {
            if (aresta.getOrigem().getRotulo().compareTo(origem.getRotulo()) == 0
                    && aresta.getDestino().getRotulo().compareTo(destino.getRotulo()) == 0) {
                return true;
            }
        }
        return false;
    }


    public List<Vertice<T>> listaAdjacentes(T rotulo) {
        return listaAdjacentes(this.buscaVertice(rotulo));
    }

    private List<Vertice<T>> listaAdjacentes(Vertice<T> vertice) {
        List<Vertice<T>> vizinhosDoVertice = new LinkedList<>();

        for (Aresta<T, E> aresta : this.listaDeArestas) {
            if (aresta.getOrigem().getRotulo().compareTo(vertice.getRotulo()) == 0) {
                vizinhosDoVertice.add(aresta.getDestino());
            }
        }
        return vizinhosDoVertice;
    }

    public int qtdComponentes() {
        boolean isDescobertos[] = new boolean[this.listaDeVertices.size()];
        return buscaEmLargura(0, isDescobertos);
    }

    public int buscaEmLargura(int origem, boolean[] isDescobertos) {
        Queue<Vertice<T>> fila;
        fila = new LinkedList<>();

        Vertice<T> origemDaBusca = listaDeVertices.get(origem);

        fila.add(origemDaBusca);
        isDescobertos[origemDaBusca.getIndex()] = true;

        while (!fila.isEmpty()) {
            Vertice<T> escolhido = fila.remove();
            List<Vertice<T>> vizinhos = listaAdjacentes(escolhido);

            for (Vertice<T> vizinho : vizinhos) {
                if (!isDescobertos[vizinho.getIndex()]) {
                    isDescobertos[vizinho.getIndex()] = true;
                    fila.add(vizinho);
                }
            }
        }

        boolean todosEncontrados = true;
        int i = 0;
        for (; i < isDescobertos.length; i++) {
            if (!isDescobertos[i]) {
                todosEncontrados = false;
                break;
            }
        }

        if (!todosEncontrados) {
            return buscaEmLargura(i, isDescobertos) + 1;
        } else {
            return 1;
        }
    }

    /**
     * @return the listaDeVertices
     */
    public List<Vertice<T>> getListaDeVertices() {
        return listaDeVertices;
    }

    /**
     * @param listaDeVertices the listaDeVertices to set
     */
    public void setListaDeVertices(List<Vertice<T>> listaDeVertices) {
        this.listaDeVertices = listaDeVertices;
    }

    /**
     * @return the listaDeArestas
     */
    public List<Aresta<T, E>> getListaDeArestas() {
        return listaDeArestas;
    }

    /**
     * @param listaDeArestas the listaDeArestas to set
     */
    public void setListaDeArestas(List<Aresta<T, E>> listaDeArestas) {
        this.listaDeArestas = listaDeArestas;
    }

}
