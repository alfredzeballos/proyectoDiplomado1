/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import Conexion.ConexionMySql;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class ListaTipoDato {
    
    private LinkedList<TipoDato> listaTD;

    public ListaTipoDato() {
        this.listaTD = new LinkedList<>();
        listaTD.add(new TipoDato("int",""));
        listaTD.add(new TipoDato("varchar","'"));
        listaTD.add(new TipoDato("date","'"));
        listaTD.add(new TipoDato("binary","'"));
        listaTD.add(new TipoDato("geometry","'"));
    }
    
    public String buscarTipo(String t){        
        for (TipoDato listaTD1 : listaTD) {
            if (t.contains(listaTD1.getTipo())) {
                return listaTD1.getTipo();
            }
        }
        return "";
    }
    public int sacarCantidad(String t){        
        String s="";
        String aux="";
        for (int i = 0; i < t.length(); i++) {
            aux=""+t.charAt(i);
            if("1234567890".contains(aux)){
                s=s+aux;
            }
        }
        if(s.length()==0){
            return 0;
        }else{
            return Integer.parseInt(s);
        }
    }
    public Key devolverkey(String key,String nombreClase,String field){      
        Key k=new Key(0,"");
        if(key.length()>0){
            k.setTabla(devTabla(nombreClase, field));
            if("PRI".equals(key)){
                k.setLlave(1);                
            }else{
                k.setLlave(2);
            }
        }
        return k;
    }
    private String devTabla(String clase,String field){
        String s=ConexionMySql.consultarUnaTupla("SELECT REFERENCED_TABLE_NAME FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE WHERE table_name = '"+clase+"' AND REFERENCED_TABLE_SCHEMA = '"+ConexionMySql.getNameBD()+"' AND column_name LIKE '"+field+"'");        
//        System.out.println("DEVTABLA"+s);
        return s;
    } 
    
//    public static void main(String[] args) {
////        System.out.println("int(11)".contains("int"));
//        ListaTipoDato d=new ListaTipoDato();
//        System.out.println(d.sacarCantidad("geometry"));
//    }
    
}
