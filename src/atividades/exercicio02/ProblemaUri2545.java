package atividades.exercicio02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ProblemaUri2545 {

    static int id;
    static String linha[];

    static Grafo<Integer, Integer> grafo;
    static Queue<ArrayList<Vertice<Integer>>> filaOrdemTopologica;
    static int numeroDeArquivos;
    static Queue<Vertice<Integer>> listaDeGrauZero;


    public static void main(String[] args) throws IOException {
        grafo = new Grafo<>();
        processarEntrada(System.in);
    }

    private static int tempoDeProcessamentoPorOrdemTopologica() {
        int contador = 0;
        filaOrdemTopologica = new LinkedList<>();
        boolean isCiclico = false;

        while (grafo.listaDeVertices.size() > 0) {
            ArrayList<Vertice<Integer>> vertices = new ArrayList<>();

            int numeroDeVerticeComGrauZero = listaDeGrauZero.size();
            while ((numeroDeVerticeComGrauZero--) > 0) {
                removerArestasDoVertice(listaDeGrauZero.element());
                vertices.add(listaDeGrauZero.element());
                grafo.listaDeVertices.remove(listaDeGrauZero.remove());
            }
//            Iterator<Vertice<Integer>> iterator = grafo.listaDeVertices.iterator();
//            int numeroDeVerticeComGrauZero = listaDeGrauZero.size();
//            while (iterator.hasNext()) {
//
//                vertice = iterator.next();
//                if (vertice.grauDeEntrada == 0) {
//                    isCiclico = false;
//                    vertices.add(vertice);
//                    removerArestasDoVertice(vertice);
//                    iterator.remove();
//                }
//
//            }
            if (vertices.size() == 0) {
                isCiclico = true;
                break;
            }
            filaOrdemTopologica.add(vertices);
            contador++;
        }
        return (isCiclico) ? -1 : contador;
    }

    private static void removerArestasDoVertice(Vertice<Integer> vertice) {

        Aresta<Integer, Integer> aresta;
        Iterator<Aresta<Integer, Integer>> iterator = grafo.listaDeArestas.iterator();
        while (iterator.hasNext()) {
            aresta = iterator.next();
            if (aresta.origem.rotulo.compareTo(vertice.rotulo) == 0) {
                if ((--aresta.destino.grauDeEntrada) == 0)
                    listaDeGrauZero.add(aresta.destino);
                iterator.remove();
            }

        }
    }

    private static void processarEntrada(InputStream in) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(in);
        BufferedReader leitor = new BufferedReader(streamReader);

        do {
            numeroDeArquivos = Integer.parseInt(leitor.readLine());
            criarVertices(numeroDeArquivos);

            for (int i = 1; i <= numeroDeArquivos; i++) {
                linha = leitor.readLine().split(" ");
                int m = Integer.parseInt(linha[0]);
                for (int j = 1; j <= m; j++) {
                    criarAresta(Integer.parseInt(linha[j]), i);
                }
            }
            System.out.println(tempoDeProcessamentoPorOrdemTopologica());

            limparLixo();
        } while (leitor.ready());
    }

    private static void limparLixo() {
        grafo.listaDeArestas.clear();
        grafo.listaDeVertices.clear();
        listaDeGrauZero.clear();
        filaOrdemTopologica.clear();
        id = 0;
    }

    private static void criarAresta(int origem, int destino) {
        Aresta<Integer, Integer> aresta;
        Vertice<Integer> verticeDestino = grafo.buscaVertice(destino);
        verticeDestino.grauDeEntrada++;
        listaDeGrauZero.remove(verticeDestino);
        aresta = new Aresta<>(grafo.buscaVertice(origem), verticeDestino);
        grafo.listaDeArestas.add(aresta);
    }

    private static void criarVertices(int quantidadeDeVertices) {
        listaDeGrauZero = new LinkedList<>();
        for (int i = 1; i <= quantidadeDeVertices; i++) {
            Vertice<Integer> vertice = new Vertice<>(i);
            grafo.listaDeVertices.add(vertice);
            listaDeGrauZero.add(vertice);
        }
    }

    public static class Vertice<T> {

        T rotulo;
        private final int index;
        protected int grauDeEntrada;

        public Vertice(T rotulo) {
            this.grauDeEntrada = 0;
            this.rotulo = rotulo;
            this.index = ++id;
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
    }
}
