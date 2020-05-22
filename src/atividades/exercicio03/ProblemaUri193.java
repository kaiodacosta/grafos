package atividades.exercicio03;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ProblemaUri193 {

    private static int id;
    private static Grafo<Integer, Integer> grafo;
    private static String linha[];
    private static BufferedReader leitor;
    private static int quantidadeDeEstradas;
    private static int quantidadeDeCidades;
    private static List<Cidade<Integer>> visitados;
    private static int valorTotalDosPedagios;
    private static int quatidadeDePedagios;

    public static void main(String[] args) throws IOException {
        grafo = new Grafo<>();
        processarEntrada(System.in);
        dijkstra();
        descobrirValorTotalDosPedagiosCujoONumeroDePedagiosSejaPar();
    }

    private static void descobrirValorTotalDosPedagiosCujoONumeroDePedagiosSejaPar() {
        Cidade<Integer> cidadeDestino = buscaVertice(visitados.size());
        List<Cidade<Integer>> cidadesVizinhas = cidadesVizinhas(cidadeDestino);
        List<Integer> menorCaminho = new ArrayList<>();

        if (quantidadeDePedagioEntreCidadeInicioEDestino(cidadeDestino) % 2 == 0) {
            System.out.println(valorTotalDosPedagios);
            return;
        }

        cidadesVizinhas.remove(cidadeDestino.vizinhaMaisProxima);
        while (!cidadesVizinhas.isEmpty()) {

            Cidade<Integer> cidadeCorrente = cidadesVizinhas.get(0);
            valorTotalDosPedagios = distanciaEntreDuasCidades(cidadeDestino, cidadeCorrente);
            if (cidadeCorrente.vizinhaMaisProxima != cidadeDestino) {
                if (quantidadeDePedagioEntreCidadeInicioEDestino(cidadeCorrente) % 2 != 0) {
                    menorCaminho.add((Integer) valorTotalDosPedagios);
                }
            }
            cidadesVizinhas.remove(cidadeCorrente);
        }
        if (menorCaminho.isEmpty()) {
            System.out.println("-1");
            return;
        }
        Collections.sort(menorCaminho);
        System.out.println(menorCaminho.get(0));
    }

    private static int quantidadeDePedagioEntreCidadeInicioEDestino(Cidade<Integer> cidadeDestino) {
        int contador = 0;
        Cidade<Integer> cidadeCorrente = cidadeDestino;
        while (cidadeCorrente.vizinhaMaisProxima != null) {
            contador++;
            valorTotalDosPedagios = valorTotalDosPedagios + distanciaEntreDuasCidades(cidadeCorrente, cidadeCorrente.vizinhaMaisProxima);
            cidadeCorrente = cidadeCorrente.vizinhaMaisProxima;
        }
        return contador;
    }

    public static Cidade<Integer> buscaVertice(Integer rotulo) {
        for (Cidade<Integer> vertice : visitados) {
            if (rotulo.compareTo(vertice.identificador) == 0) {
                return vertice;
            }
        }
        return null;
    }

    private static void dijkstra() {
        quantidadeDeCidades = grafo.listaDeCidades.size();
        visitados = new ArrayList<>();
        Cidade<Integer> vertice = grafo.listaDeCidades.remove(0);
        vertice.distancia = 0;
        visitados.add(vertice);
        while (quantidadeDeCidades-- > 0) {
            descobrirVizinhos(vertice);
            Collections.sort(grafo.listaDeCidades);
            if (quantidadeDeCidades == 0)
                break;
            vertice = grafo.listaDeCidades.remove(0);
            visitados.add(vertice);
        }
    }

    private static void descobrirVizinhos(Cidade<Integer> cidade) {
        for (Cidade<Integer> vizinha : cidadesVizinhas(cidade)) {
            int km = distanciaEntreDuasCidades(cidade, vizinha);
            if (cidade.distancia + km < vizinha.distancia) {
                vizinha.distancia = cidade.distancia + km;
                vizinha.vizinhaMaisProxima = cidade;
            }
        }
    }

    private static int distanciaEntreDuasCidades(Cidade<Integer> cidadeOrigem, Cidade<Integer> cidadeDestino) {
        for (Estrada<Integer, Integer> estrada : grafo.listaDeEstradas) {
            if (estrada.cidadeOrigem.identificador.compareTo(cidadeOrigem.identificador) == 0
                    && estrada.cidadeDestino.identificador.compareTo(cidadeDestino.identificador) == 0) {
                return estrada.km;
            }
        }
        return -1;
    }

    private static void processarEntrada(InputStream in) throws IOException {
        InputStreamReader streamReader = new InputStreamReader(in);
        leitor = new BufferedReader(streamReader);
        linha = leitor.readLine().split(" ");
        quantidadeDeCidades = Integer.parseInt(linha[0]);
        quantidadeDeEstradas = Integer.parseInt(linha[1]);
        criarCidades(quantidadeDeCidades);
        while (quantidadeDeEstradas-- > 0) {
            linha = leitor.readLine().split(" ");
            criarEstrada(Integer.parseInt(linha[0]), Integer.parseInt(linha[1]), Integer.parseInt(linha[2]));
        }
    }

    private static class Grafo<T extends Comparable<T>, E extends Comparable<E>> {

        List<Cidade<T>> listaDeCidades;
        List<Estrada<T, E>> listaDeEstradas;

        public Grafo() {
            listaDeCidades = new LinkedList<>();
            listaDeEstradas = new LinkedList<>();
        }

        public Cidade<T> buscaCidade(T rotulo) {
            for (Cidade<T> vertice : listaDeCidades) {
                if (rotulo.compareTo(vertice.identificador) == 0) {
                    return vertice;
                }
            }
            return null;
        }
    }

    private static class Cidade<T> implements Comparable<Cidade<T>> {

        T identificador;
        private final int index;
        private Integer distancia;
        private Cidade<Integer> vizinhaMaisProxima;

        public Cidade(T rotulo) {
            this.identificador = rotulo;
            this.index = ++id;
            this.vizinhaMaisProxima = null;
            this.distancia = Integer.MAX_VALUE;
        }

        @Override
        public int compareTo(Cidade<T> tCidade) {
            if (this.distancia < tCidade.distancia)
                return -1;
            if (this.distancia > tCidade.distancia)
                return 1;
            return 0;
        }
    }

    private static class Estrada<T, E> {

        public E km;
        public Cidade<T> cidadeOrigem, cidadeDestino;

        public Estrada(Cidade<T> origem, Cidade<T> destino) {
            this(origem, destino, null);
        }

        public Estrada(Cidade<T> origem, Cidade<T> destino, E km) {
            this.cidadeOrigem = origem;
            this.cidadeDestino = destino;
            this.km = km;
        }

    }

    private static void criarEstrada(int cidadeOrigem, int cidadeDestino, int km) {
        Estrada<Integer, Integer> estrada;
        Cidade<Integer> origem = grafo.buscaCidade(cidadeOrigem);
        Cidade<Integer> destino = grafo.buscaCidade(cidadeDestino);
        estrada = new Estrada<>(origem, destino, km);
        grafo.listaDeEstradas.add(estrada);
        estrada = new Estrada<>(destino, origem, km);
        grafo.listaDeEstradas.add(estrada);
    }

    private static void criarCidades(int quantidadeCidade) {
        for (int i = 1; i <= quantidadeCidade; i++) {
            Cidade<Integer> vertice = new Cidade<>(i);
            grafo.listaDeCidades.add(vertice);
        }
    }

    private static List<Cidade<Integer>> cidadesVizinhas(Cidade<Integer> cidade) {
        List<Cidade<Integer>> cidadesVizinhas = new LinkedList<>();

        for (Estrada<Integer, Integer> estrada : grafo.listaDeEstradas) {
            if (estrada.cidadeOrigem.identificador.compareTo(cidade.identificador) == 0) {
                cidadesVizinhas.add(estrada.cidadeDestino);
            }
        }
        return cidadesVizinhas;
    }
}
