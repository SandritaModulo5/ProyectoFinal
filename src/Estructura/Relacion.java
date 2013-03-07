/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructura;

/**
 *
 * @author Sandrita
 */
public class Relacion implements Prototipo{

    private int tipo;
    private String nombre;
    private Clase claseOrigen;
    private Clase claseDestino;
    private String rolOrigen;
    private String rolDestino;
    
    public Relacion() {
        this.tipo = 0;
        this.nombre = "";
        this.claseOrigen = null;
        this.claseDestino = null;        
        this.rolOrigen="";
        this.rolDestino="";
    }

    public Relacion(int tipo, String nombre, Clase claseOrigen, Clase claseDestino, String rolOrigen, String rolDestino) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.claseOrigen = claseOrigen;
        this.claseDestino = claseDestino;
        this.rolOrigen = rolOrigen;
        this.rolDestino = rolDestino;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setClaseOrigen(Clase origen) {
        this.claseOrigen = origen;
    }

    public void setClaseDestino(Clase destino) {
        this.claseDestino = destino;
    }

    public void setRolOrigen(String rolOrigen) {
        this.rolOrigen = rolOrigen;
    }

    public void setRolDestino(String rolDestino) {
        this.rolDestino = rolDestino;
    }

    public int getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public Clase getClaseOrigen() {
        return claseOrigen;
    }

    public Clase getClaseDestino() {
        return claseDestino;
    }

    public String getRolOrigen() {
        return rolOrigen;
    }

    public String getRolDestino() {
        return rolDestino;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Relacion clonar() {
        return new Relacion( tipo, nombre, claseOrigen.clonar(), 
                             claseDestino.clonar(), rolOrigen, rolDestino );
    }
    
}
