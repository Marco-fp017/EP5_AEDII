package entities;

public class PilhaDinamica {
    private No topo;

    //criar pilha
    public PilhaDinamica(){
        topo = null;
    }
    //inserir elemento
    public void inserirNo(Vertice ID){

        if(topo == null) {
            No novo = new No(ID);
            topo = novo;
            topo.setProximo(null);
        }
        else{
            No novo = new No(ID);
            novo.setProximo(topo);
            topo = novo;
        }    
    }
    
    //remover elemento e retorná-lo
    public Vertice removerNo(){
        if(topo == null) return null; //   throw new NullPointerException("a pilha está vazia");
        else {
            No id = topo;
            topo = topo.getProximo();
            return id.getID();
        } 
    }
    
    //imprime diretamente no método
    public void imprimirPilha1(){
        if(topo != null){ 
            No end = topo;
            System.out.print(end.getID().getId());
            while(end.getProximo() != null){
                System.out.print(" " + end.getID().getId());
                end = end.getProximo();
            }
            System.out.println();
        }
        else{
            System.out.println();
        }
    }    

    //retorna uma string com os valores que devem ser imprimidos
    public String imprimirPilha2(){
        if(topo == null) return "\n";
        String saida = "" + topo.getID().getId();
        No end = topo.getProximo();
        while(end != null){
            saida += " " + end.getID().getId();
            end = end.getProximo();
        }
        saida += "\n";
        return saida;
    }

    //verificar se determinado elemento existe na pilha
    public No buscarNaPilha(Vertice IdBuscado){
        if(topo.getID().equals(IdBuscado)) return topo;
        No end = topo;
        
        while(end != null){
            if(end.getID().equals(IdBuscado)) return end;
            end = end.getProximo();
        } 
        return null;
    }

    //retorna o tamalho da pilha
    public int tamanhoPilha(){
        int tam = 0;
        No end = topo;
        while(end != null){
            tam++;
            end = end.getProximo();
        }
        return tam;
    }
    
    //verifica se existem elementos na pilha
    public boolean estaVazia(){
        if(topo == null) return true;
        return false;
    }

    public No getTopo() {
        return topo;
    }
    
    public No getProximo(No no){
        return no.getProximo();
    }
}
