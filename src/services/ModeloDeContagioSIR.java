package services;

import entities.PilhaDinamica;
import entities.Vertice;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ModeloDeContagioSIR {

    public static final double c = 0.85; //probabilidade de contaminação
    public static final double r = 0.2; //probabilidade de recuperação
    
    private final Vertice source;
    private final int sourceId;
    private final Random random = new Random();
    
    public ModeloDeContagioSIR(List<Vertice> Grafo){
        sourceId = random.nextInt(Grafo.size());    //escolhendo aleatoriamente um vértice que será o paciente 0
        this.source = Grafo.get(sourceId);          //paciente 0
        Grafo.get(sourceId).setSIR('I');
        contagio(Grafo);
    }
    
    private void contagio(List<Vertice> Grafo) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Informe o caminho de saída: ");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(sc.nextLine()))){
            bw.write("S,I,R");
            bw.newLine();
            
            PilhaDinamica pilha = new PilhaDinamica();  //pilha de vértices infectados
            int[] passos = null;
            
            //marcando o vértice inicial como infectado e colocando-o na pilha de infectados
            Grafo.get(sourceId).setSIR('I');
            Grafo.get(sourceId).setVisitado();
            pilha.inserirNo(Grafo.get(sourceId));

            while(!pilha.estaVazia()){
                Vertice v = pilha.removerNo();
                double x = random.nextDouble();

                if (x <= r){  //se a pessoa se recuperou
                    Grafo.get(Grafo.indexOf(v)).setSIR('R');
                    Grafo.get(Grafo.indexOf(v)).setVisitado();  //marca-se um vértice recuperado como visitado
                    passos = frequenciaSIR(Grafo);
                    bw.write(passos[0]+","+passos[1]+","+passos[2]);
                    bw.newLine();
                }
                else{
                    //visito todos os vértices adjacentes a 'v' e faço uma verificação de possível infecção
                    for(Vertice w : Grafo.get(Grafo.indexOf(v)).getAdj()){
                        double y = random.nextDouble();
                        //se um vértice nao foi visitado, significa que ele está marcado como S
                        if(!(Grafo.get(Grafo.indexOf(w)).getVisitado()) && (y <= c)){
                            w.setSIR('I');
                            Grafo.get(Grafo.indexOf(w)).setSIR('I');
                            Grafo.get(Grafo.indexOf(w)).setVisitado();
                            pilha.inserirNo(Grafo.get(Grafo.indexOf(w)));
                            passos = frequenciaSIR(Grafo);
                            bw.write(passos[0]+","+passos[1]+","+passos[2]);
                            bw.newLine();
                        }
                    }
                }   
            }
        } 
        catch(IOException e){ e.getMessage(); }
          finally{sc.close();}
    }    
    
    private int[] frequenciaSIR(List<Vertice> Grafo){
        int[] frequencias = {0,0,0};
        for(Vertice v : Grafo) {
            switch (v.getSIR()) {
                case 'S':
                    frequencias[0]++;
                    break;
                case 'I':
                    frequencias[1]++;
                    break;
                case 'R':
                    frequencias[2]++;
                    break;
            }
        }
//        for(int i = 0; i < frequencias.length; i++) System.out.print(frequencias[i] + " | ");
//        System.out.println();
        return frequencias;
    }
}
