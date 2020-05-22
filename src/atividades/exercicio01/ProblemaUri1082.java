package atividades.exercicio01;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ProblemaUri1082 {
    static int id;
    static int numeroDeCasos;
    static int quantidadeDeArestas;
    static int quantidadeDeVertices;
    static String linha[];
    static Character[] ROTULOS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };
    static Grafo<Character, Character> grafo;
    static BufferedReader leitor;
    static Character verticeOrigem;
    static Character verticeDestino;


    public static void main(String[] args) throws IOException {
        grafo = new Grafo<>();
        processarEntrada(System.in);
    }

    private static void processarEntrada(InputStream in) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(in);
        leitor = new BufferedReader(streamReader);
        numeroDeCasos = Integer.parseInt(leitor.readLine());
        for (int i = 1; numeroDeCasos >= i; i++) {
            id = 0;
            System.out.println("Case #" + i + ":");

            linha = leitor.readLine().split(" ");
            quantidadeDeVertices = Integer.parseInt(linha[0]);
            quantidadeDeArestas = Integer.parseInt(linha[1]);

            criarVertices(quantidadeDeVertices);
            criarArestas(quantidadeDeArestas);

            encontrarComponentesConexos();
            limparLixo();
        }

    }

    private static void limparLixo() {
        grafo.listaDeArestas.clear();
        grafo.listaDeVertices.clear();
    }

    private static void encontrarComponentesConexos() {
        boolean isDescobertos[] = new boolean[grafo.listaDeVertices.size()];
        System.out.println(grafo.buscaEmLargura(0, isDescobertos) + " connected components");
        System.out.println();
    }

    private static void criarArestas(int quantidadeDeArestas) throws IOException {
        while (quantidadeDeArestas > 0) {
            linha = leitor.readLine().split(" ");
            verticeOrigem = linha[0].charAt(0);
            verticeDestino = linha[1].charAt(0);
            Aresta<Character, Character> aresta;
            aresta = new Aresta<>(grafo.buscaVertice(verticeOrigem), grafo.buscaVertice(verticeDestino));
            grafo.listaDeArestas.add(aresta);
            aresta = new Aresta<>(grafo.buscaVertice(verticeDestino), grafo.buscaVertice(verticeOrigem));
            grafo.listaDeArestas.add(aresta);
            quantidadeDeArestas--;
        }
    }

    private static void criarVertices(int quantidadeDeVertices) {
        for (int i = 0; i < quantidadeDeVertices; i++) {
            Vertice<Character> vertice = new Vertice<>(ROTULOS[i]);
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

    private static class Grafo<T extends Comparable<T>, E extends Comparable<E>> {

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

        public int buscaEmLargura(int origem, boolean[] isDescobertos) {
            Queue<Vertice<T>> fila = new LinkedList<>();
            Vertice<T> origemDaBusca = listaDeVertices.get(origem);
            fila.add(origemDaBusca);
            isDescobertos[origemDaBusca.index] = true;

            while (!fila.isEmpty()) {
                Vertice<T> escolhido = fila.remove();
                System.out.print(escolhido.rotulo + ", ");
                List<Vertice<T>> vizinhos = listaAdjacentes(escolhido);

                for (Vertice<T> vizinho : vizinhos) {
                    if (!isDescobertos[vizinho.index]) {
                        isDescobertos[vizinho.index] = true;
                        fila.add(vizinho);
                    }
                }
            }
            System.out.println();

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

    }
}
