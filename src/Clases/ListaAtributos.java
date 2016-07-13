/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.ConexionMySql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class ListaAtributos {

    private LinkedList<Atributo> listaAtributos;
    private ListaTipoDato listaTD;

    public ListaAtributos() {
        listaAtributos = new LinkedList<>();
        listaTD = new ListaTipoDato();
    }

    public ListaAtributos(String nombreTabla) {
        listaAtributos = new LinkedList<>();
        listaTD = new ListaTipoDato();
        CargarAtributos(nombreTabla);
    }

    public LinkedList<Atributo> getListaAtributos() {
        return listaAtributos;
    }

    public LinkedList<Atributo> getPrimaryKey(){
        LinkedList<Atributo> m=new LinkedList<>();
        for (Atributo listaAtributo : listaAtributos) {
            if(listaAtributo.getKey().getLlave()==1){
                m.add(listaAtributo);
            }
        }
        return m;
    }
    
    public void AdicionarAtributo(String field, String type, String key, String nombreClase) {
//        System.out.println("Field: "+field+" Type: "+type+" Key: "+key+" NombreClase: "+nombreClase);
        listaAtributos.add(new Atributo(field, listaTD.buscarTipo(type),listaTD.sacarCantidad(type), listaTD.devolverkey(key, nombreClase, field)));
    }
    
    public boolean esForanea(String nombreTabla){
        for (int i = 0; i < listaAtributos.size(); i++) {
            if(listaAtributos.get(i).getKey().getTabla().equals(nombreTabla)){
                return true;
            }
        }
        return false;
    }

    private void CargarAtributos(String nombreTabla) {
        ConexionMySql.conectar();
        ResultSet result = ConexionMySql.consultarRes("SHOW COLUMNS FROM " + ConexionMySql.getNameBD() + "." + nombreTabla);
        LinkedList<String[]> aux=new LinkedList<>();
        String field;
        String type;
        String key;
        try {
            while (result.next() && !result.wasNull()) {
                aux.add(new String[]{result.getString(1),result.getString(2),result.getString(4)});
            }
        } catch (SQLException ex) {
            System.out.println("No cargo los Atributos de las Tablas");
        }
        ConexionMySql.cerrarConexion();
        
        for (String[] aux1 : aux) {
            field = aux1[0];
            type = aux1[1];
            key = aux1[2];
            AdicionarAtributo(field, type, key, nombreTabla);
        }
    }

    @Override
    public String toString() {
        String s="";
        for (int i = 0; i < listaAtributos.size(); i++) {
            s=s+listaAtributos.get(i).toString()+"\n";
        }
        return s;
    }
    
//    public static void main(String[] args) {
////        ConexionMySql.InicializarBase("appMusic");
////        ListaAtributos l=new ListaAtributos("albun");
//        
//        LinkedList<String[]> m=new LinkedList<>();
//        m.add(new String[]{"Julio","Cesar"});
//        m.add(new String[]{"Pinto","Rojas"});
//        System.out.println(m.get(0)[0]);
//    }
}
