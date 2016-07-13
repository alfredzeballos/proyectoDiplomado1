/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

/**
 *
 * @author Cesar
 */
public class Key {
    private int llave;
    private String tabla;

    public Key(int llave, String tabla) {
        this.llave = llave;
        this.tabla = tabla;
    }

    public int getLlave() {
        return llave;
    }

    public void setLlave(int llave) {
        this.llave = llave;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    @Override
    public String toString() {
        return "TipoLlave= " + llave + "  Foranea=" + tabla;
    }
    
    
        
}
