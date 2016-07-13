/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Cesar
 */
public class DatosIngresados {
    private String nombreTabla;
    private LinkedList<Atributo> llavesprimarias;
    LinkedList<String> datos;
    private Random r;

    public DatosIngresados(String nombreTabla, LinkedList<Atributo> listaLlavesPrimarias) {
        this.nombreTabla = nombreTabla;
        this.llavesprimarias = listaLlavesPrimarias;
        datos=new LinkedList<>();
        r=new Random();
    }

    public String getNombreTabla() {
        return nombreTabla;
    }

    public LinkedList<Atributo> getTipoDatoAtributo() {
        return llavesprimarias;
    }

    public LinkedList<String> getDatos() {
        return datos;
    }
    
    public void insertar(String nuevoDato){
        datos.add(nuevoDato);
    }

    public String darLLaveForanea(){
        return datos.get(r.nextInt(datos.size()));
    }
    @Override
    public String toString() {
        String s="";
        String primarykey="";
        for (int i = 0; i < datos.size(); i++) {
            s+=datos.get(i)+" | ";
        }
        
        for (int i = 0; i < llavesprimarias.size(); i++) {
            primarykey+=llavesprimarias.get(i).getField()+" , ";
        }
        
        return "Tabla---"+nombreTabla.toUpperCase()+"  Atributo---"+primarykey+"   DATOS---|"+s;
    }
    
    
    
}
