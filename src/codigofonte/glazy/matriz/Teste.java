package codigofonte.glazy.matriz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Teste {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String linha[];

        linha = br.readLine().split(" ");
        int numeroDeVertices, numeroDeArestas, verticeOrigem, verticeDestino;

        numeroDeVertices = Integer.parseInt(linha[0]);
        GrafoMatriz gm = new GrafoMatriz(numeroDeVertices);

        numeroDeArestas = Integer.parseInt(linha[1]);
        for (int i = 0; i < numeroDeArestas; i++) {
            linha = br.readLine().split(" ");
            verticeOrigem = Integer.parseInt(linha[0]);
            verticeDestino = Integer.parseInt(linha[1]);
            gm.addAresta(verticeOrigem, verticeDestino);
        }

        Vertice[] explorados = gm.percursoEmLargura(1);
        for (int j = 0; j < numeroDeVertices; j++) {
            if (explorados[j].isDescoberto()) {
                System.out.printf("Vértice %d descoberto\n", j);
            } else {
                System.out.printf("Vértice %d NÃO descoberto\n", j);
            }
        }

    }

}