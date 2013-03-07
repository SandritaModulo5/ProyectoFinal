/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructura;

/**
 *
 * @author Sandrita
 */
public class Metodo implements Prototipo{
    
    private String nombre;
    private String retorno;
    private int acceso;

    public Metodo() {
        this.nombre = "";
        this.retorno = "";
        this.acceso = CONST.V_PUBLIC; 
    }

    public Metodo(String nombre, String retorno, int acceso) {
        this.nombre = nombre;
        this.retorno = retorno;
        this.acceso = acceso;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRetorno(String retorno) {
        this.retorno = retorno;
    }

    public void setAcceso(int acceso) {
        this.acceso = acceso;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getRetorno() {
        return retorno;
    }

    public int getAcceso() {
        return acceso;
    }

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
        s=s+ " " + this.retorno + " " + this.nombre + "( ) {\n\t}" ;
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
        s = s + this.nombre + "( ): " + this.retorno ;
        return s;
    }       

    public boolean equals(Metodo metodo) {
        return ( this.nombre.equals( metodo.getNombre()) );
    }

    public Metodo clonar() {
        return new Metodo(nombre, retorno, acceso);
    }

}
