package services;

import entities.Vertice;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class ModeloDeContagioSIR {

    public static final double c = 0.63; //probabilidade de contaminação
    public static final double r = 0.38; //probabilidade de recuperação
    
    private final int sourceId;
    private final Random random = new Random();
    
    public ModeloDeContagioSIR(List<Vertice> Grafo){
        sourceId = random.nextInt(Grafo.size());    //escolhendo aleatoriamente um vértice que será o paciente 0
        contagio(Grafo);
    }
    
    private void contagio(List<Vertice> Grafo) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Informe o caminho de saída: ");
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(sc.nextLine()))){
            bw.write("S,I,R");
            bw.newLine();
            
            //Lista contendo todos os vértices infectados
            List<Vertice> infectadosGrafo = new LinkedList<>();
            
            int[] passos = null;
            
            //marcando o vértice inicial como infectado e colocando-o na lista de infectados
            Grafo.get(sourceId).setSIR('I');
            infectadosGrafo.add(Grafo.get(sourceId));
            
            while(!infectadosGrafo.isEmpty()){
                //recebo o primeiro vértice da lista de infectados
                Vertice v = infectadosGrafo.remove(0);
                
                double x = random.nextDouble();
                if (x <= r){  //se a pessoa se recuperou
                    v.setSIR('R'); 
                    Grafo.get(Grafo.indexOf(v)).setSIR('R');    //marca-se o vértice como recuperado
                    if(infectadosGrafo.contains(v)) infectadosGrafo.remove(v);   //e remove-o da lista de infectados
                    
                    passos = frequenciaSIR(Grafo);
                    bw.write(passos[0]+","+passos[1]+","+passos[2]);
                    bw.newLine();
                }
                else{
                    //visito todos os vértices adjacentes a 'v' e faço uma verificação de possível infecção
                    for(Vertice w : Grafo.get(Grafo.indexOf(v)).getAdj()){
                        double y = random.nextDouble();
                        
                        //se um vértice está suscetivel e foi contaminado
                        if((Grafo.get(Grafo.indexOf(w)).getSIR() == 'S') && (y <= c)){
                            w.setSIR('I');
                            Grafo.get(Grafo.indexOf(w)).setSIR('I');
                            if(!infectadosGrafo.contains(w)) infectadosGrafo.add(w);
                            
                            passos = frequenciaSIR(Grafo);
                            bw.write(passos[0]+","+passos[1]+","+passos[2]);
                            bw.newLine();
                        }
                    }
                } 
                if(Grafo.get(Grafo.indexOf(v)).getSIR() == 'I' && !infectadosGrafo.contains(v)) infectadosGrafo.add(v);
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
