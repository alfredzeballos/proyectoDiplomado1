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
public class TipoDato {
    
    private String tipo; 
    private String simbolo; 

    public TipoDato(String tipo, String simbolo) {
        this.tipo = tipo;
        this.simbolo = simbolo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    
}

