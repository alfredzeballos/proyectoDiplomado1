/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import Conexion.ConexionMySql;
import com.mysql.jdbc.MySQLConnection;
import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class HiloInsertador extends Thread{
    LinkedList<DatoHilo> consultas;
    int ini;
    int fin;

    public HiloInsertador(LinkedList<DatoHilo> consultas, int ini, int fin) {
        this.consultas = consultas;
        this.ini = ini;
        this.fin = fin;
    }

    @Override
    public void run() {
        System.out.println("HILO " + ini+" - "+fin);
        for (int i = ini; i < fin; i++) {
//            System.out.println(ini+"-"+fin+"  "+consultas.get(i).dato);
            ConexionMySql.consultar(consultas.get(i).dato);
            consultas.get(i).estado=true;
        }
//        System.out.println("----------------------------------YA MORI ---------------------------------");
    }
    
    
}
