/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

import Clases.DatosPoblar;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author Cesar
 */
public class GeneradorDatos {

    public static String abecedario = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ abcdefghijklmnñopqrstuvwxyw";
    public static String numeros = "0123456789";
    public static Random r = new Random();
    public LinkedList<DatosPoblar> listaDatosPoblar;
    public GeneradorDatos() {
        listaDatosPoblar=new LinkedList<>();
        
        listaDatosPoblar.add(new DatosPoblar(1,"Nombres","src/Archivos/nombres.txt"));
        listaDatosPoblar.add(new DatosPoblar(2,"Apellidos","src/Archivos/apellidos.txt"));
        listaDatosPoblar.add(new DatosPoblar(3,"Titulos","src/Archivos/titulos.txt"));
        listaDatosPoblar.add(new DatosPoblar(4,"Telefonos","src/Archivos/telefono.txt"));        
        listaDatosPoblar.add(new DatosPoblar(5,"Imagenes Portada","src/Archivos/portada.txt"));
        listaDatosPoblar.add(new DatosPoblar(6,"Imagenes Perfil","src/Archivos/perfil.txt"));
        listaDatosPoblar.add(new DatosPoblar(7,"Fechas","src/Archivos/fechas.txt"));
        listaDatosPoblar.add(new DatosPoblar(8,"Sexo","src/Archivos/sexo.txt"));
        listaDatosPoblar.add(new DatosPoblar(9,"Estados","src/Archivos/estado.txt"));        
        listaDatosPoblar.add(new DatosPoblar(10,"Correos","src/Archivos/correo.txt"));        
    }
    public int devolverElegirDatoInsertar(String itemSeleccionado){
        for (DatosPoblar listaDatosPoblar1 : listaDatosPoblar) {
            if (listaDatosPoblar1.getNombre().equals(itemSeleccionado)) {
                System.out.println("SE ELigio "+itemSeleccionado+"  ID= "+listaDatosPoblar1.getElegirDatoInsertar());
                return listaDatosPoblar1.getElegirDatoInsertar();
            }
        }
        return 0;
    }
    public String devolverNombreDatoInsertar(int itemSeleccionado){
        for (DatosPoblar listaDatosPoblar1 : listaDatosPoblar) {
            if (listaDatosPoblar1.getElegirDatoInsertar()==itemSeleccionado) {
                return listaDatosPoblar1.getNombre();
            }
        }
        return "";
    }
    public String obtenerValorCeherente(int elegirDatoInsertar){
        String s="";
        int cant=0;
        for (int i = 0; i < listaDatosPoblar.size(); i++) {
            if(listaDatosPoblar.get(i).getElegirDatoInsertar()==elegirDatoInsertar){
                cant=listaDatosPoblar.get(i).getLista().size();
//                return "COHERENTE";
                return listaDatosPoblar.get(i).getLista().get(r.nextInt(cant));
            }
        }
        return s;
    }
    
    public  String obtenerValores(int cantidad, String tipoDato, int elegirDatoInsertar) {
        String s = "";
        if (elegirDatoInsertar == 0 ) {            
            if (tipoDato.contains("varchar")) {
                cantidad = r.nextInt(cantidad) + 1;
                for (int i = 0; i < cantidad; i++) {
                    s = s + abecedario.charAt(r.nextInt(abecedario.length()));
                }
                return "'" + s + "'";
            }
            if (tipoDato.contains("int")) {
                cantidad = r.nextInt(8) + 1;
                for (int i = 0; i < cantidad; i++) {
                    s = s + numeros.charAt(r.nextInt(numeros.length()));
                }
                return s;
            }

            if (tipoDato.contains("date")) {
                return "'2015-05-15'";
            }
        }else{
            return "'"+obtenerValorCeherente(elegirDatoInsertar)+"'";
        }
        return s;
    }

    public LinkedList<DatosPoblar> getListaDatosPoblar() {
        return listaDatosPoblar;
    }
    
}
