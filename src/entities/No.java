package entities;

public class No {
    private Vertice ID;
    private No proximo;

    public No(Vertice ID){
       this.ID = ID;
       this.proximo = null;
   }

    //retorna ID (valor) do nó
    public Vertice getID() {
        return ID;
    }
    
    //define ID (valor) do nó
    public void setID(Vertice ID) {
        this.ID = ID;
    }

    //retorna nó anterior
    public No getProximo() {
        return proximo;
    }

    //define nó anterior
    public void setProximo(No prox) {
        this.proximo = prox;
    }   
}
