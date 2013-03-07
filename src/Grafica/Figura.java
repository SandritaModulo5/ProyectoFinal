/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Point;

/**
 *
 * @author Sandrita
 */
public abstract class Figura extends JComponent{
    
    private Color colorNormal;
    private Color colorSelect;
    private boolean select;
    
    public Figura() {
        super();
        this.select=false;
        this.colorNormal=this.getBackground();
        this.colorSelect=this.getBackground();
    }

    public void setColorNormal( Color colorNormal ) {
        this.colorNormal = colorNormal;
    }

    public void setColorSelect( Color colorSelect ) {
        this.colorSelect = colorSelect;
    }

    public void setSelect( boolean select ) {
        this.select = select;
    }

    public Color getColorNormal() {
        return colorNormal;
    }

    public Color getColorSelect() {
        return colorSelect;
    }

    public boolean isSelect() {
        return select;
    }
    
    public void borrar(){      
        //Pintarme del color del fondo de la ventana 
        colorNormal = this.getBackground();
        colorSelect = this.getBackground();
        repaint();
    }    
      
    @Override
    public void paint( Graphics g ){
        super.paint(g);    
    }
    
    public abstract void mover ( int desplazamientox, int desplazamientoy );
    
    public abstract void ampliar ( int zoomin );
    
    public abstract void reducir ( int zoomout );
    
    public abstract void dibujar();
    
    public abstract boolean estaDentro( Point p );
    
    public abstract boolean esIgual( Figura f );    
    
    public abstract boolean estaSeleccionado( Point p, Dimension d  ); 

}
