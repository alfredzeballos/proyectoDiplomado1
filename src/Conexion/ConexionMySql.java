/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luisBar
 */
public class ConexionMySql {

    private static Connection con;
    private static Statement stmt;
    private static String driver = "";
    private static String jdbc = "";
    private static String nameBD = "";
    private static String user = "";
    private static String password = "";
    private static String Servidor = "";
    private static String puerto = "";

    public static Connection getCon() {
        return con;
    }

    public static void InicializarBase(String nombreBase,String usuario,String contrasena,String servidor,String port) {
        driver = "com.mysql.jdbc.Driver";
        jdbc = "jdbc:mysql://";//para sqlserver
        nameBD = nombreBase;//cambiar nombre base de datos
        user = usuario;//cambiar nombre de usuario    
        password = contrasena;//cambiar contrasena        
        Servidor = servidor;
        puerto = port;
    }

    public static void InicializarBase(String nombreBase) {
        driver = "com.mysql.jdbc.Driver";
        jdbc = "jdbc:mysql://";//para sqlserver
        nameBD = nombreBase;//cambiar nombre base de datos
        user = "root";//cambiar nombre de usuario    
        password = "";//cambiar contrasena        
        Servidor = "localhost";
        puerto = "3306";
    }
    
    public static void conectar() {
        try {

            Class.forName(driver);//registrar el driver            
            String url = jdbc + Servidor + ":" + puerto + "/" + nameBD;            
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                stmt = con.createStatement();
                System.out.println("Conexion Exitosa");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }

    public static boolean existeCOnexion(){        
        try {
            Class.forName(driver);//registrar el driver            
            String url = jdbc + Servidor + ":" + puerto + "/" + nameBD;            
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                stmt = con.createStatement();
                System.out.println("Conexion Exitosa");
            }
            cerrarConexion();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return false;
    }
    
    public static void cerrarConexion() {
        try {
            if (stmt != null) {
                stmt.close();
            }

            if (con != null) {
                con.close();
            }
            System.out.println("SE CERRO LA CONEXION");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //INSERT, UPDATE, DELETE y procedimientos almacenados
    public static void consultar(String sql) {
        try {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Datos Incorrectos");
        }
    }

    //consultas SELECT * FROM
    public static ResultSet consultarRes(String sql) {
        try {
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //Devuelve una sola consulta
    public static String consultarUnaTupla(String sql) {        
        ConexionMySql.conectar();
        ResultSet result = ConexionMySql.consultarRes(sql);
        String s = "";
        try {
            while (result.next() && !result.wasNull()) {
                s = result.getString(1);
            }
        } catch (SQLException ex) {

        }
        ConexionMySql.cerrarConexion();
        return s;
    }

    public static String getNameBD() {
        return nameBD;
    }

    public static void setNameBD(String nameBD) {
        ConexionMySql.nameBD = nameBD;
    }

    public static void CambiarBase(String name) {
        nameBD = name;
    }

    public static void main(String[] agrs) {
        //ConexionMySql.InicializarBase("cristian_sw2","cristian_sw2","bolivia", "66.7.201.150", "3306");
        ConexionMySql.InicializarBase("tec_music","root","16061992j", "localhost", "3306");
        conectar();
        System.out.println();
        ResultSet result = ConexionMySql.consultarRes("select * from cuenta");
        String s = "";
        try {
            while (result.next()) {
                s = result.getString(0)+"  "+result.getString(1);
                System.out.println(s);
            }
        } catch (SQLException ex) {
                   
        }
        ConexionMySql.cerrarConexion();
//        System.out.println(s);
    }
}
