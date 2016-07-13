/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Conexion.ConexionMySql;
import Datos.GeneradorDatos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class DB {

    private LinkedList<Tabla> listaTablas;
    private LinkedList<DatosIngresados> tablasInsert;
    private GeneradorDatos generar;

    public DB(String nombreDB) {
        listaTablas = new LinkedList<>();
        tablasInsert = new LinkedList<>();
        generar=new GeneradorDatos();
        CargarDB(nombreDB);
    }

    public LinkedList<Tabla> getListaTablas() {
        return listaTablas;
    }

    private void CargarDB(String nombreDB) {
        LinkedList<String> aux = new LinkedList<>();
//        ConexionMySql.InicializarBase(nombreDB);
        ConexionMySql.conectar();
//        ResultSet result = ConexionMySql.consultarRes("show tables from ");
        ResultSet result = ConexionMySql.consultarRes("show full tables from " + ConexionMySql.getNameBD());
        try {
            while (!result.wasNull() && result.next() ) {
                aux.add(result.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println("No cargo los Tablas");
        }
        ConexionMySql.cerrarConexion();
        boolean[] estados = new boolean[aux.size()];
        int i = 0;
        
        //Cargas las tablas obtenidas a Partir de la Base de Datos
        for (String aux1 : aux) {
            listaTablas.add(new Tabla(aux1));
            estados[i] = false;
            i++;
        }
        Ordenar(estados);
    }

    private void Ordenar(boolean[] estados) {
        LinkedList<Tabla> aux = new LinkedList<>();
        for (int i = 0; i < listaTablas.size(); i++) {
            if (!listaTablas.get(i).existeForanea()) {
                aux.add(listaTablas.get(i));
                estados[i] = true;
            }
        }
        for (int i = 0; i < listaTablas.size(); i++) {
            if (estados[i] == false) {
                aux.add(listaTablas.get(i));
            }
        }
        listaTablas = aux;
    }

    public void insertarTablaInsert(String dato, String nombreTabla) {
        for (DatosIngresados tablasInsert1 : tablasInsert) {
            if (tablasInsert1.getNombreTabla().equals(nombreTabla)) {
                tablasInsert1.insertar(dato);
                return;
            }
        }
    }
    
    public String obtenerLLaveForaneaPK(String nombreTabla,int val){
        String s="";
        for (int i = 0; i < tablasInsert.size(); i++) {
            if(tablasInsert.get(i).getNombreTabla().equals(nombreTabla)){
                return tablasInsert.get(i).datos.get(val);
            }
        }
        return s;
    }
    
    public String obtenerLLaveForanea(String nombreTabla){
        String s="";
        for (int i = 0; i < tablasInsert.size(); i++) {
            if(tablasInsert.get(i).getNombreTabla().equals(nombreTabla)){
                return tablasInsert.get(i).darLLaveForanea();
            }
        }
        return s;
    }
    
    public void insertarDatos(int cantidad,Tabla tab) { 
        int pKfK=1;
        tablasInsert.add(new DatosIngresados(tab.getNombreTabla(), tab.getPrimaryKey()));
        String consulta;
        String cadAtributos = "";
        Atributo atributos = new Atributo();
        int pk=Integer.valueOf(ConexionMySql.consultarUnaTupla("select count(*) from "+tab.getNombreTabla()))+1;        
        ConexionMySql.conectar();
        LinkedList<DatoHilo> listaCon=new LinkedList<>();
        for (int i = 0; i < cantidad; i++) {
            int j = 0;
            consulta = "insert into " + tab.getNombreTabla() + "  values("; 
            while (j < tab.getlAtributos().getListaAtributos().size()) {
                atributos = tab.getlAtributos().getListaAtributos().get(j);                
                if(atributos.getKey().getLlave()==2){
                    cadAtributos=obtenerLLaveForanea(atributos.getKey().getTabla());
                }else{
                    cadAtributos = generar.obtenerValores(atributos.getCantidad(), atributos.getTipo(),atributos.getElegirDatoInsertar()); 
                }
                
                if (atributos.esIgual(tab.getPrimaryKey())) {
                    if(atributos.getKey().getTabla().length()>0){                          
//                        cadAtributos=obtenerLLaveForaneaPK(atributos.getKey().getTabla(),pKfK);
//                        System.out.println("FFFFFFFFFFFfff "+cadAtributos );
//                        cadAtributos=""+pKfK;                        
                        cadAtributos=obtenerLLaveForanea(atributos.getKey().getTabla());
                    }else{
                        if(atributos.getTipo().equals("int")){
                            cadAtributos=String.valueOf(pk);
                            pk++;
                        }
                        insertarTablaInsert(cadAtributos, tab.getNombreTabla());
                    }                    
                }
                consulta = consulta + cadAtributos + ",";
                j++;
            }      
            pKfK++;
            consulta=consulta.substring(0,consulta.length()-1);
            consulta += ")";
//            System.out.println(consulta);
            listaCon.add(new DatoHilo(consulta));
//            ConexionMySql.consultar(consulta);
        }
//        for (DatoHilo listaCon1 : listaCon) {
//            ConexionMySql.consultar(listaCon1.dato);
//        }
        
        
        int counH=10;
        HiloInsertador[] h=new HiloInsertador[counH];
        int cantHilos=listaCon.size()/counH;
        int ini=0;
        int fin=cantHilos;
        for (int i = 0; i < counH; i++) {
            h[i]=new HiloInsertador(listaCon, ini, fin);
            h[i].start();
            ini=fin;
            fin+=cantHilos;
        }
        while(!termine(listaCon)){
            ;
        }
//        
        ConexionMySql.cerrarConexion();
    }
    
    public void insertarTablasSinFK(int cantidadDatos,boolean[] aux){
        for (int i = 0; i < listaTablas.size(); i++) {
            if(!listaTablas.get(i).existeForanea()){
                insertarDatos(cantidadDatos,listaTablas.get(i));
                aux[i]=true;
            }
        }
    }
    
    public boolean sePuedeInsertar(Tabla t){
        for (int i = 0; i < tablasInsert.size(); i++) {
//            System.out.println("SE PUEDE INSERT "+t.getlAtributos().esForanea(tablasInsert.get(i).getNombreTabla()));            
            if(t.getlAtributos().esForanea(tablasInsert.get(i).getNombreTabla())){
                return true;
            }
        }
        return false;
    } 
    
    public void insertarTablasConFK(int cantidadDatos,boolean[] aux){
        for (int i = 0; i < listaTablas.size(); i++) {
            if(aux[i]==false && sePuedeInsertar(listaTablas.get(i))){
                insertarDatos(cantidadDatos,listaTablas.get(i));
                aux[i]=true;
            }
        }
    }
    
    public boolean InsertaronTodasTablas(boolean[] aux){
        for (int i = 0; i < aux.length; i++) {
            if(aux[i]==false) return false;
        }
        return true;
    }
    
    public void insertar(int cantidadDatos){
        boolean[] aux=new boolean[listaTablas.size()];
        insertarTablasSinFK(cantidadDatos,aux);        
//        for (int i = 0; i < aux.length; i++) {
//            System.out.print(aux[i]+"   ");
//        }
        System.out.println(toStringDatosIngresados());
        while(!InsertaronTodasTablas(aux)){            
            insertarTablasConFK(cantidadDatos, aux);
        }
    }
    public String toStringDatosIngresados(){
        String s="";
        for (DatosIngresados tablasInsert1 : tablasInsert) {
            s += tablasInsert1.toString() + "\n";
        }
        return s;
    }
    
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < listaTablas.size(); i++) {
            s = s + listaTablas.get(i).toString();
        }
        return s;
    }

    public static void main(String[] args) {       
//        System.out.println(Math.pow(2,11));    
//        ConexionMySql.InicializarBase("cristian_sw2", "cristian_sw2", "bolivia", "66.7.201.150", "3306");
        ConexionMySql.InicializarBase("appMusic", "root", "", "localhost", "3306");
        DB aux = new DB("appMusic");
        aux.insertar(10);
        System.out.println("Se termino de Insertar");
//        System.out.println(aux.toStringDatosIngresados());
    }

    private boolean termine(LinkedList<DatoHilo> listaCon) {
        for (DatoHilo listaCon1 : listaCon) {
            if (listaCon1.estado == false) {
                return false;
            }
        }
        return true;
    }

}
