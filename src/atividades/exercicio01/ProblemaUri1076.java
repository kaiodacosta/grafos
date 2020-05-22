package atividades.exercicio01;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class ProblemaUri1076 {
    static int id;
    static int casosDeTeste;
    static int noDeInicio;
    static int quantidadeDeArestas;
    static int quantidadeDeVertices;
    static String linha[];
    static Grafo<Integer, Integer> grafo;
    static BufferedReader leitor;
    static Integer verticeOrigem;
    static Integer verticeDestino;
    static int contador;

    public static void main(String[] args) throws IOException {
        grafo = new Grafo<>();
        processarEntrada(System.in);
    }

    private static void processarEntrada(InputStream in) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(in);
        leitor = new BufferedReader(streamReader);
        casosDeTeste = Integer.parseInt(leitor.readLine());
        for (int i = 1; casosDeTeste >= i; i++) {
            id = 0;
            noDeInicio = Integer.parseInt(leitor.readLine());
            linha = leitor.readLine().split(" ");
            quantidadeDeVertices = Integer.parseInt(linha[0]);
            quantidadeDeArestas = Integer.parseInt(linha[1]);
            criarVertices(quantidadeDeVertices);
            criarArestas(quantidadeDeArestas);

            quantidadeDeMovimentoParaDesenharOLabirinto(noDeInicio);
            limparLixo();
        }

    }

    private static void limparLixo() {
        grafo.listaDeArestas.clear();
        grafo.listaDeVertices.clear();
    }

    private static void quantidadeDeMovimentoParaDesenharOLabirinto(int noDeInicio) {
        contador = 0;
        boolean isDescobertos[] = new boolean[grafo.listaDeVertices.size()];
        System.out.println(grafo.buscaEmProfundidade(noDeInicio, isDescobertos));
    }

    private static void criarArestas(int quantidadeDeArestas) throws IOException {
        while (quantidadeDeArestas > 0) {
            linha = leitor.readLine().split(" ");
            verticeOrigem = Integer.parseInt(linha[0]);
            verticeDestino = Integer.parseInt(linha[1]);
            Aresta<Integer, Integer> aresta;
            aresta = new Aresta<>(grafo.buscaVertice(verticeOrigem), grafo.buscaVertice(verticeDestino));
            grafo.listaDeArestas.add(aresta);
            aresta = new Aresta<>(grafo.buscaVertice(verticeDestino), grafo.buscaVertice(verticeOrigem));
            grafo.listaDeArestas.add(aresta);
            quantidadeDeArestas--;
        }
    }

    private static void criarVertices(int quantidadeDeVertices) {
        for (int i = 0; i < quantidadeDeVertices; i++) {
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

        public int buscaEmProfundidade(int origem, boolean[] isDescobertos) {
            isDescobertos[origem] = true;
            Vertice<T> escolhido = listaDeVertices.get(origem);
            List<Vertice<T>> vizinhos = listaAdjacentes(escolhido);

            for (Vertice<T> vizinho : vizinhos) {
                if (!isDescobertos[vizinho.index]) {
                    buscaEmProfundidade(vizinho.index, isDescobertos);
                    contador++;
                }

            }
            return contador++;
        }

    }
}
