package atividades.exercicio02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProblemaUri2429 {

    static int id;

    static String linha[];
    static Grafo<Integer, Integer> grafo;
    static BufferedReader leitor;
    static Integer verticeOrigem;
    static Integer verticeDestino;

    public static void main(String[] args) throws IOException {
        grafo = new Grafo<>();
        processarEntrada(System.in);
        System.out.println(verificarSeAsCidadesSaoConexas());
    }

    private static Character verificarSeAsCidadesSaoConexas() {
        boolean isDescobertos[] = new boolean[grafo.listaDeVertices.size()];
        grafo.buscaEmLargura(1, isDescobertos);

        for (boolean b : isDescobertos) {
            if (!b)
                return 'N';
        }
        inverterArestas();
        isDescobertos = new boolean[grafo.listaDeVertices.size()];
        grafo.buscaEmLargura(1, isDescobertos);
        for (boolean b : isDescobertos) {
            if (!b)
                return 'N';
        }
        return 'S';
    }

    private static void inverterArestas() {
        Vertice<Integer> verticeAuxiliar;
        for (Aresta<Integer, Integer> aresta : grafo.listaDeArestas) {
            verticeAuxiliar = aresta.origem;
            aresta.origem = aresta.destino;
            aresta.destino = verticeAuxiliar;
        }
    }

    private static void processarEntrada(InputStream in) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(in);
        leitor = new BufferedReader(streamReader);
        int n = Integer.parseInt(leitor.readLine());
        criarVertices(n);
        criarArestas(n);
    }

    private static void criarArestas(int quantidadeDeArestas) throws IOException {
        while ((quantidadeDeArestas--) > 0) {
            linha = leitor.readLine().split(" ");
            verticeOrigem = Integer.parseInt(linha[0]);
            verticeDestino = Integer.parseInt(linha[1]);
            Aresta<Integer, Integer> aresta;
            aresta = new Aresta<>(grafo.buscaVertice(verticeOrigem), grafo.buscaVertice(verticeDestino));
            grafo.listaDeArestas.add(aresta);
        }
    }

    private static void criarVertices(int quantidadeDeVertices) {
        for (int i = 1; i <= quantidadeDeVertices; i++) {
            Vertice<Integer> vertice = new Vertice<>(i);
            grafo.listaDeVertices.add(vertice);
        }
    }

    public static class Vertice<T> {

        T rotulo;
        private final int index;

        public Vertice(T rotulo) {
            this.rotulo = rotulo;
            this.index = id++;
        }
    }

    public static class Aresta<T, E> {

        public E chave;
        public Vertice<T> origem, destino;

        public Aresta(Vertice<T> origem, Vertice<T> destino) {
            this(origem, destino, null);
        }

        public Aresta(Vertice<T> origem, Vertice<T> destino, E chave) {
            this.origem = origem;
            this.destino = destino;
            this.chave = chave;
        }
    }

    public static class Grafo<T extends Comparable<T>, E extends Comparable<E>> {

        public List<Vertice<T>> listaDeVertices;
        public List<Aresta<T, E>> listaDeArestas;

        public Grafo() {
            listaDeVertices = new LinkedList<>();
            listaDeArestas = new LinkedList<>();
        }

        public Vertice<T> buscaVertice(T rotulo) {
            for (Vertice<T> vertice : listaDeVertices) {
                if (rotulo.compareTo(vertice.rotulo) == 0) {
                    return vertice;
                }
            }
            return null;
        }

        private List<Vertice<T>> listaAdjacentes(Vertice<T> vertice) {
            List<Vertice<T>> vizinhosDoVertice = new LinkedList<>();

            for (Aresta<T, E> aresta : this.listaDeArestas) {
                if (aresta.origem.rotulo.compareTo(vertice.rotulo) == 0) {
                    vizinhosDoVertice.add(aresta.destino);
                }
            }
            return vizinhosDoVertice;
        }

        public void buscaEmLargura(int origem, boolean[] isDescobertos) {
            Queue<Vertice<T>> fila = new LinkedList<>();
            Vertice<T> origemDaBusca = listaDeVertices.get(origem);
            fila.add(origemDaBusca);
            isDescobertos[origemDaBusca.index] = true;

            while (!fila.isEmpty()) {
                Vertice<T> escolhido = fila.remove();
                List<Vertice<T>> vizinhos = listaAdjacentes(escolhido);

                for (Vertice<T> vizinho : vizinhos) {
                    if (!isDescobertos[vizinho.index]) {
                        isDescobertos[vizinho.index] = true;
                        fila.add(vizinho);
                    }
                }
            }


        }

    }
}