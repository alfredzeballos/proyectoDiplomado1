/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.LinkedList;

/**
 *
 * @author Cesar
 */
public class Atributo {
    private String field;
    private String tipo;
    private int cantidad;    
    private int elegirDatoInsertar;    
    private Key key;

    public Atributo() {        
        this.field = "";
        this.tipo = "";
        this.key = new Key(0,"");
        this.cantidad=0;
        this.elegirDatoInsertar=0;
    }
    public Atributo(String field, String tipo,int cantidad,Key k) {        
        this.field = field;
        this.tipo = tipo;
        this.key = k;
        this.cantidad=cantidad;
        this.elegirDatoInsertar=0;
    }
    
    public boolean esIgual(LinkedList<Atributo> atr){
        for (Atributo atr1 : atr) {
            if (field.equals(atr1.getField())) {
                return true;
            }
        }        
        return false;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTipo() {
        return tipo;
    }

    public void setElegirDatoInsertar(int elegirDatoInsertar) {
        this.elegirDatoInsertar = elegirDatoInsertar;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    public int getElegirDatoInsertar() {
        return elegirDatoInsertar;
    }

    @Override
    public String toString() {
        return "field= " + field + "     tipoDato= " + tipo + "     cant= "+cantidad+ "     " + key.toString();
    }
    
    
    
}
