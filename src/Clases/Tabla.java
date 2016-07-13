/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import Datos.GeneradorDatos;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class Tabla {
       private String nombreTabla;
       private ListaAtributos lAtributos;

    public Tabla(String nombreTabla) {
        this.nombreTabla = nombreTabla;
        lAtributos=new ListaAtributos(nombreTabla);
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public ListaAtributos getlAtributos() {
        return lAtributos;
    }
    
    public boolean existeForanea(){        
           for (Atributo listaAtributo : lAtributos.getListaAtributos()) {
               if(listaAtributo.getKey().getTabla().length()>0){
                   return true;
               }
           }
        return false;
    }
    
    public LinkedList<Atributo> getPrimaryKey(){
        return lAtributos.getPrimaryKey();
    }
   
    
    @Override
    public String toString() {
        String s="------------------------------------------"+nombreTabla+"------------------------------------------\n";
        s=s+lAtributos.toString();
        return s+"---------------------------------------------------------------------------------------------------\n";
    }
    
    
       
}
