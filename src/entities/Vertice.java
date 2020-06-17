package entities;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
    private final int id;
    private boolean visitado;
    private List<Vertice> adj;
    private char SIR;

    public Vertice(int id) {
        this.id = id;
        this.visitado = false;
        adj = new ArrayList<>();
        this.SIR = 'S';
    }

    //retorna a lista de adjacência
    public List<Vertice> getAdj() {
        return adj;
    }
    
    //adiciona um id na sua lista de adjacência
    public void setInListaAdj(Vertice id){
        this.adj.add(id);
    }

    public int getId() {
        return id;
    }

    public boolean getVisitado() {
        return visitado;
    }

    //marca o elemento como visitado pela componente a que ele pertence
    public void setVisitado() {
        this.visitado = true;
    }   
    
    public void desfazVisitado(){
        this.visitado = false;
    }

    public char getSIR() {
        return SIR;
    }

    public void setSIR(char SIR) {
        this.SIR = SIR;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertice other = (Vertice) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
