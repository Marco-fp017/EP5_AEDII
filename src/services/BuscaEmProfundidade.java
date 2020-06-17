package services;

import entities.Vertice;
import entities.PilhaDinamica;
import java.util.ArrayList;
import java.util.List;

public class BuscaEmProfundidade {
    private Vertice[] edgeTo;   // último vértice encontrado antes de encontrar um vértice 
    private final Vertice source;   //id de início da componente
    
    List<Vertice> componente = new ArrayList<>();   //lista para guardar a componente conexa que parte do id 'source'

    public BuscaEmProfundidade(List<Vertice> Grafo, Vertice source) {
        this.edgeTo = new Vertice[tamToEdge(Grafo)+1];
        this.source = source;
        bfs(Grafo, source);
    }

    private void bfs(List<Vertice> Grafo, Vertice s) {
        PilhaDinamica pilha = new PilhaDinamica();  //pilha de IDs
        
        Grafo.get(Grafo.indexOf(s)).setVisitado();  //marcar a fonte como visitada na lista e na pilha
        s.setVisitado();
        pilha.inserirNo(s);  // e colocá-la na pilha.
        
        while(!pilha.estaVazia()){
            Vertice v = pilha.removerNo();  // Remova o próximo vértice da pilha e guarda ele
            if(!componente.contains(v)) componente.add(v);
            
            for(Vertice w : Grafo.get(Grafo.indexOf(v)).getAdj()){
                if(!w.getVisitado()){   // Para cada vértice adjacente não marcado
                    edgeTo[w.getId()] = v; // indico que o último vértice que eu visitei antes de chegar neste foi o v
                    Grafo.get(Grafo.indexOf(w)).setVisitado();  //marcando todos os vértices adjacentes a v como visitados
                    w.setVisitado();
                    pilha.inserirNo(w); //impilho todos os adjacentes a v na pilha
                    
                    if(!componente.contains(w)) componente.add(w);  //adicionando na lista todos os elementos conexos à source 
                }
            }
        }
    }

    public List<Vertice> getComponente() 
    { return componente; }
    
    public void closeComponente()
    { componente.clear(); }

    //retorna o tamanho que deve conter o método toEdge
    public int tamToEdge(List<Vertice> Grafo){
        int maiorID = 0;
        for(Vertice id : Grafo) if(id.getId() > maiorID) maiorID = id.getId();

        return maiorID;
    }

    //verifica se existe um caminho entre quaisquer dois vértices do grafo
    public boolean existeCaminho(Vertice inicio, Vertice fim){
        if(edgeTo[inicio.getId()] != null && edgeTo[fim.getId()] != null) return true;
        
        return false;
    }
}