package ep5_aed_ii;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import services.BuscaEmProfundidade;
import entities.Vertice;
import services.ModeloDeContagioSIR;

public class EP5_AED_II {
    
    public static void main(String[] args) {
        List<Vertice> IDList = new ArrayList<>(); //lista de IDs
        List<List<Vertice>> componentes = new ArrayList<>();    //lista de componentes conexas
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Informe o caminho de entrada do arquivo a ser lido: ");
        //Lendo o arquivo .csv contendo as informações dos encontros
        try (BufferedReader bf = new BufferedReader (new FileReader(sc.nextLine()))){
            String aux = bf.readLine(); // linha com número de ids 
            aux = bf.readLine();        // linha com numero de encontros
            aux = bf.readLine();

            while(aux != null){
                String[] line = aux.split(" ");
                
                //pegando os dois IDs que se encontraram
                Vertice id1 = new Vertice(Integer.parseInt(line[0]));
                Vertice id2 = new Vertice(Integer.parseInt(line[1]));
                
                //adicionando-os na lista de ajdacência um do outro
                if (!IDList.contains(id1)){ //id não existe na lista de IDs
                    IDList.add(id1);
                    id1.setInListaAdj(id2);
                } else{//id existe, preciso localizá-lo p/ adicionar o outro id na lista de adjacência
                    for (Vertice auxId : IDList){
                        if(auxId.equals(id1)){
                            auxId.setInListaAdj(id2);
                            break;
                        }
                    }
                }
                
                if (!IDList.contains(id2)){ //id não existe na lista de IDs
                    IDList.add(id2);
                    id2.setInListaAdj(id1);
                } else{//id existe, preciso localizá-lo p/ adicionar o outro id na lista de adjacência
                    for (Vertice auxId : IDList){
                        if(auxId.equals(id2)){
                            auxId.setInListaAdj(id1);
                            break;
                        }
                    }
                }
                aux = bf.readLine();
            }   
            bf.close();
            
            //componente conexa começando do meu 1° elemento 
            BuscaEmProfundidade compo = new BuscaEmProfundidade(IDList, IDList.get(0));
            List<Vertice> auxCompo = new ArrayList<>();
            auxCompo.addAll(compo.getComponente());
            
            componentes.add(auxCompo);
            compo.closeComponente();
            
            //adicionando todas as componentes conexas na lista de componentes
            for(Vertice ids : IDList){
                if(!ids.getVisitado()){
                    compo = new BuscaEmProfundidade(IDList, ids);
                        List<Vertice> auxCompo1 = new ArrayList<>();
                    auxCompo1.addAll(compo.getComponente());
                    componentes.add(auxCompo1);
                    compo.closeComponente();
                }
            }
            
            //procurando qual o tamanho da componente gigante
            int biggerList = 0;
            for(List ids : componentes) if(ids.size() > biggerList) biggerList = ids.size();

            //encontrando a componente gigante
            List<Vertice> componenteGigante = new ArrayList<>(); 
            
            for(List ids : componentes){
                if(ids.size() == biggerList){
                    componenteGigante = ids;
                    break;
                }
            }
            
            ModeloDeContagioSIR m = new ModeloDeContagioSIR(componenteGigante);            
        }catch (IOException e){
            e.getMessage();
            e.printStackTrace();
        }
        finally{
            sc.close();
        }
    }
    //C:\\Users\\marco\\Desktop\\out\\summary7.cvs 
    //C:\\Users\\marco\\Desktop\\gigante3.txt
}
