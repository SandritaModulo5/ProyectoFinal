/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructura;

/**
 *
 * @author Sandrita
 */
public class Atributo implements Prototipo{
    
    private String nombre;
    private String tipoDato;
    private int acceso;

    public Atributo() {
        this.nombre = "";
        this.tipoDato = "";
        this.acceso = CONST.V_PUBLIC;        
    }

    public Atributo(String nombre, String tipoDato, int acceso) {
        this.nombre = nombre;
        this.tipoDato = tipoDato;
        this.acceso = acceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public void setAcceso(int acceso) {
        this.acceso = acceso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public int getAcceso() {
        return acceso;
    }

    /**
     * Sirve para convertir a String pero para generar el Codigo Java
     * @return
     */
    @Override
    public String toString() {
        String s="";
        switch( this.acceso ){
            case CONST.V_PUBLIC : s=s+"public";
            break;
            case CONST.V_PRIVATE : s=s+"private";
            break;
            case CONST.V_PROTECTED : s=s+"protected";
            break;            
        }
        s=s+ " " + this.tipoDato + " " + this.nombre + ";" ;
        return s;
    }

        
    /**
     * Sirve para convertir a String pero para mostrar en la Clase Grafica
     * @return
     */        
    public String toStringGrafico() {
        String s="";
        switch( this.acceso ){
            case CONST.V_PUBLIC : s=s+"+";
            break;
            case CONST.V_PRIVATE : s=s+"-";
            break;
            case CONST.V_PROTECTED : s=s+"#";
            break;            
        }
        s=s+ this.nombre + ": " + this.tipoDato ;
        return s;
    }    

    public boolean equals(Atributo atributo) {
        return ( this.nombre.equals( atributo.getNombre()) );
    }

    public Atributo clonar() {
        return new Atributo( nombre, tipoDato, acceso );
    }

}
