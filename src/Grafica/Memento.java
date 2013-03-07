/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafica;

import java.util.LinkedList;

/**
 *
 * @author Sandrita
 */
public class Memento {

    private LinkedList<Figura> estado;

    public Memento() {
        this.estado=new LinkedList<Figura>();
    }
    
    public void setEstado( LinkedList<Figura> lista ){
        for ( int i = 0; i < lista.size(); i++ ) {
            Figura figura = lista.get(i);
            Figura newFigura = null;
            if( figura instanceof ClaseGrafica ){
                ClaseGrafica c=(ClaseGrafica)figura;
                newFigura=c.clonar();
            }
            if( figura instanceof RelacionGrafica ){
                RelacionGrafica r=(RelacionGrafica)figura;
                newFigura=r.clonar();
            }
            this.estado.add( figura );
        }
    }
    
    public LinkedList<Figura> getEstado(){
        return this.estado;
    }
    
}
