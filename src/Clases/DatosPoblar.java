/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class DatosPoblar {
    private int elegirDatoInsertar;
    private String nombre;
    private String ruta;
    LinkedList<String> lista;
    
    public DatosPoblar(int elegir,String nombres,String rut){
        elegirDatoInsertar=elegir;
        nombre=nombres;
        ruta=rut;
        lista=new LinkedList<>();
        cargarDatos();
    }

    public String getNombre() {
        return nombre;
    }

    public LinkedList<String> getLista() {
        return lista;
    }

    public int getElegirDatoInsertar() {
        return elegirDatoInsertar;
    }
    public int cantidadDatos(){
        return lista.size();
    }
    public void cargarDatos(){
        try{           
            FileInputStream fstream = new FileInputStream(ruta);            
            DataInputStream entrada = new DataInputStream(fstream);            
            BufferedReader buffer = new BufferedReader(new InputStreamReader(entrada));
            String strLinea;            
            String stracu="";            
            while ((strLinea = buffer.readLine()) != null)   {            
                stracu+=strLinea;
            }            
            entrada.close();
            strLinea="";
            System.out.println("SE CAPTURIO  "+stracu);
            for (int i = 0; i < stracu.length(); i++) {                
                if(stracu.charAt(i)==';'){
                    System.out.println("SOLO ENTRO UNA");
                    lista.add(strLinea);
                    strLinea="";
                }else{
                    strLinea+=stracu.charAt(i);
                }
            }
            
        }catch (Exception e){ //Catch de excepciones
            System.err.println("Ocurrio un error: " + e.getMessage());
        }
    }
    public String mostrarDatos(){
        String s="";
        for (int i = 0; i < lista.size(); i++) {
            System.out.println("Longitus "+lista.size());
            s+=lista.get(i);
        }
        return s;
    }
    public static void main(String[] args) {
        DatosPoblar d=new DatosPoblar(1, "Personas","src/Archivos/personas.txt");
        System.out.println(d.mostrarDatos());
        System.out.println(d.cantidadDatos());
        
    }
}
