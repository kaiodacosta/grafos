package atividades.exercicio05;

import atividades.exercicio01.ProblemaUri1076;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        procesarEntrada(System.in);
    }

    private static void procesarEntrada(InputStream inputStream) {
        InputStreamReader streamReader = new InputStreamReader(inputStream);
        BufferedReader leitor = new BufferedReader(streamReader);

    }

    public static class RedeOtica<T extends Comparable<T>, E extends Comparable<E>> {

        public List<Taba<T>> listaDeTabas;
        public List<Ramo<T, E>> listaDeRamos;

        public RedeOtica() {
            listaDeTabas = new LinkedList<>();
            listaDeRamos = new LinkedList<>();
        }
    }
    public static class Taba<T> {

        T rotulo;

        public Taba(T rotulo) {
            this.rotulo = rotulo;
        }
    }
    public static class Ramo<T, E> {

        public E impactoAmbiental;
        public Taba<T> origem, destino;

        public Ramo(Taba<T> origem, Taba<T> destino, E impactoAmbiental) {
            this.origem = origem;
            this.destino = destino;
            this.impactoAmbiental = impactoAmbiental;
        }
    }
}
