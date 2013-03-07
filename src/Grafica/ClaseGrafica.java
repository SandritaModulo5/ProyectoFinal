/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafica;

import Estructura.Atributo;
import Estructura.CONST;
import Estructura.Clase;
import Estructura.Metodo;
import Estructura.Prototipo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;
        
/**
 *
 * @author Sandrita
 */
public class ClaseGrafica extends Figura implements Prototipo{

    private Clase clase;

    private int tamLetra=12;
    private int yn= tamLetra/3;
    private Dimension minDimension;

    public ClaseGrafica() {
        super();   
        
        this.clase=new Clase( "", CONST.C_NORMAL );
        this.tamLetra=12;
        this.setLocation( new Point(0,0) );
        
        minDimension = new Dimension( tamLetra*4,tamLetra*3 );
        
        this.setSize( minDimension );
        this.setColorNormal( Color.red );
        this.setColorSelect( Color.black ); 
        
    }
    
    public ClaseGrafica( String nombre, Point pos ){
        super();
        
        this.clase=new Clase( nombre, CONST.C_NORMAL );
        this.tamLetra=12;
        this.setLocation( pos );
        
        if( nombre.length()*tamLetra <= 30 )
            minDimension = new Dimension( tamLetra*4,tamLetra*3 );
        else 
            minDimension = new Dimension( nombre.length()*tamLetra,tamLetra*3 );
        
        this.setSize( minDimension );
        this.setColorNormal( Color.red );
        this.setColorSelect( Color.black );
        
    }
        
    
    public void setNombre( String nombre ) {
        this.clase.setNombre(nombre);
    }
    
    public String getNombre() {
        return this.clase.getNombre();
    }
    
    public LinkedList<Atributo> obtenerLAtributos(){
        return this.clase.getListaAtributos();
    }
    
    public LinkedList<Metodo> obtenerLMetodos(){
        return this.clase.getListaMetodos();
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public int getYn() {
        return yn;
    }

    public void setMinDimension(Dimension minDimension) {
        this.minDimension = minDimension;
    }

    public Dimension getMinDimension() {
        return minDimension;
    }   
    
    public void setTamLetra(int tamLetra) {
        this.tamLetra = tamLetra;
        yn = tamLetra/3;
        redimensionar();
    }
    
    public int getTamLetra() {
        return tamLetra;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
    
    public Clase getClase() {
        return clase;
    }
        
  //******************************************************************************  
    
    public void redimensionar(){
        int ancho = getDimenMasLargo()*(tamLetra*3/4);
        if ( ancho<=30 )
            ancho = tamLetra * 4;
        
        int alto = (2*yn) + (3*tamLetra);
        int size = getSizeAtributos();
        if ( size>0 )
            alto = alto + ((size-1)*tamLetra) + (size*yn) + yn;
        size = getSizeMetodos();
        if ( size>0 )
            alto = alto + ((size-1)*tamLetra) + (size*yn) + yn;
        
        minDimension = new Dimension( ancho,alto );        
        this.setSize( minDimension );
    }
    
    private int getDimenMasLargo(){
        int dim=0;
        LinkedList<Atributo> la=clase.getListaAtributos();
        if ( la!=null ){
            for ( int i=0 ; i<la.size() ; i++ )  {
                Atributo a = la.get(i);
                if ( a.toStringGrafico().length()>dim ) 
                    dim = a.toStringGrafico().length();
            }            
        }
        LinkedList<Metodo> lm=clase.getListaMetodos();
        if ( lm!=null ){
            for ( int i=0 ; i<lm.size() ; i++ )  {
                Metodo m = lm.get(i);
                if ( m.toStringGrafico().length()>dim ) 
                    dim = m.toStringGrafico().length();
            }            
        }
        if ( clase.getNombre().length()>dim ) 
            dim = clase.getNombre().length();
        return dim;
    }
    
    private int getSizeAtributos(){
        LinkedList la=clase.getListaAtributos();
        if ( la!=null)
            return la.size();
        else
            return 0;
    }
    
    private int getSizeMetodos(){
        LinkedList lm=clase.getListaMetodos();
        if ( lm!=null)
            return lm.size();
        else
            return 0;
    }
    
//******************************************************************************
    public void mover( int dx, int dy ) {
        Point pc=this.getLocation();
        pc.x= pc.x + dx ;
        pc.y= pc.y + dy ;
        
        this.setLocation( pc );
    }
    
    public void ampliar( int zoomin ) {
        Point pc=this.getLocation();        
        this.setLocation( new Point( pc.x*zoomin, pc.y*zoomin ) );
                
        Dimension dim=this.getSize();
        dim.width = dim.width*zoomin;
        dim.height = dim.height*zoomin;
       
        this.minDimension = dim;
        this.setSize( dim );  
        
        this.tamLetra = tamLetra*zoomin;
    }
    
    public void reducir( int zoomout ) {
        Point pc=this.getLocation(); 
        this.setLocation( new Point( pc.x/zoomout, pc.y/zoomout) );
        
        Dimension dim=this.getSize();
        dim.width = dim.width/zoomout;
        dim.height = dim.height/zoomout;
       
        this.minDimension = dim;
        this.setSize( dim );
        
        this.tamLetra = tamLetra/zoomout;        
    }
    
    
    public void dibujar() {
    }
    
    public boolean estaDentro( Point p ) {
        int dw, dh;
        
        Point pc=this.getLocation();
        dw = pc.x+this.getWidth();
        dh = pc.y+this.getHeight();
        
        return ( (( (pc.x<=p.x)&&(p.x<=dw) ) &&(((dh<=p.y)&&(p.y<=pc.y))||((pc.y<=p.y)&&(p.y<=dh))))
                || (((dw<=p.x)&&(p.x<=pc.x))&&(((dh<=p.y)&&(p.y<=pc.y))||((pc.y<=p.y)&&(p.y<=dh)))));
    }
    
    public boolean esIgual( Figura figura ) {
        return( ((ClaseGrafica)figura).getNombre().equals( clase.getNombre() ));
    }
    
    public boolean estaSeleccionado( Point p, Dimension dim ) {
        return LibreriaMatematica.estaDentroRectangulo( p,dim,this.getLocation(),this.getSize() );
    }

    public void redimimensionar( int ancho, int alto ){
        int w=this.getWidth();
        int h=this.getHeight();
        this.setSize( w+ancho, h+alto );
    }
    
    public Point getCentro(){
        Point pc=this.getLocation();
        int w=this.getWidth()/2;
        int h=this.getHeight()/2;
        
        return new Point( pc.x+w, pc.y+h );
    }
   
//*****************************************************************************
    public Point getPos0(){
        return this.getLocation();
    }
    
    public Point getPos1(){
        Point pc=this.getLocation();;
        int w=this.getWidth();
        
        return new Point( pc.x+w, pc.y );
    }
    
    public Point getPos2(){        
        Point pc=this.getLocation();;
        int h=this.getHeight();
        
        return new Point( pc.x, pc.y+h );
    }
    
    public Point getPos3(){
        Point pc=this.getLocation();;
        int w=this.getWidth();
        int h=this.getHeight();
        
        return new Point( pc.x+w, pc.y+h );
    }
    
    
    public void paint( Graphics g ){
    
        super.paint(g);
        
        // actualizamos dimensiones
        int cantAtr = getSizeAtributos();
        int cantMet = getSizeMetodos();
        int px=this.getX();
        int py=this.getY();
        int w=this.getWidth();
        int h=this.getHeight();
        
        // pintamos el relleno
        g.setColor(new Color(81, 237, 255));
        g.fill3DRect( px, py, w, h, true );
        
        // luego pintamos los contornos
        g.setColor(Color.black);
        g.draw3DRect( px, py, w, tamLetra+yn, true );
        
        int y = (2*tamLetra) + (2*yn);
        int size = getSizeAtributos();
        if (size>0)
            y = y + ((size-1)*tamLetra) + (size*yn) + yn;
        g.draw3DRect( px, py, w, y, true );
        
        // nos preguntamos antes si esta seleccionado esta clase
        if ( this.isSelect() )
            g.setColor( this.getColorSelect() );        
        else 
            g.setColor( this.getColorNormal() );
        
        g.draw3DRect( px, py, w, h, true );
        
        g.setColor(Color.black);
        g.setFont( new Font("Times New Rooman",Font.BOLD, tamLetra) );
        g.drawString( clase.getNombre(), px+(w/7), py+tamLetra );
        
        y = (2*yn) + tamLetra;
        y = y + py;
        LinkedList<Atributo> la=clase.getListaAtributos();
        for ( int i = 0 ; i<cantAtr ; i++ )  {
            Atributo a = la.get(i);
            y = y + yn + tamLetra;
            g.drawString( a.toStringGrafico(), px+2, y );
        }
        if ( cantAtr>0 ){
            y = ((2*yn)+ (tamLetra*2))+((cantAtr-1)*tamLetra)+(cantAtr*yn)+yn;
        }else{
            y = (2*yn)+ (tamLetra*2);
        }
        y = y + py;
        LinkedList<Metodo> lm=clase.getListaMetodos();
        for ( int i = 0 ; i<cantMet ; i++ )  {
            Metodo m = lm.get(i);
            y = y + yn + tamLetra;
            g.drawString( m.toStringGrafico(), px+2, y );
        }
    }
   
//******************************************************************************    
    public void adicionarAtributo( String nombre, String tipoDato, int acceso ){
        clase.adicionarAtributo(nombre, tipoDato, acceso);
        this.redimensionar();
        this.repaint();
    } 
    
    public void adicionarAtributo( Atributo atributo ){
        this.clase.adicionarAtributo( atributo );
        this.redimensionar();
        this.repaint();
    } 
    
    public void eliminarAtributo( String nombre ){
        clase.eliminarAtributo(nombre);
        this.redimensionar();
        this.repaint();
    }
    
    public void eliminarAtributo( Atributo atributo ){
        clase.eliminarAtributo(atributo);
        this.redimensionar();
        this.repaint();        
    }  
    
    public void eliminarAtributo( int index ){
        if( index>getSizeAtributos() ){
            clase.eliminarAtributo( index );
            this.redimensionar();
            this.repaint();          
        }       
    } 
    
    
    public void adicionarMetodo( String nombre, String retorno, int acceso ){
        clase.adicionarMetodo(nombre, retorno, acceso);
        this.redimensionar();
        this.repaint();
    }   
        
    public void adicionarMetodo( Metodo metodo ){
        clase.adicionarMetodo( metodo );
        this.redimensionar();
        this.repaint();
    } 
    
    public void eliminarMetodo( String nombre ){
        clase.eliminarMetodo(nombre);
        this.redimensionar();
        this.repaint();
    }
    
    public void eliminarMetodo( Metodo metodo ){
        clase.eliminarMetodo(metodo);
        this.redimensionar();
        this.repaint();        
    }  
    
    public void eliminarMetodo( int index ){
        if( index>getSizeMetodos() ){
            clase.eliminarMetodo( index );
            this.redimensionar();
            this.repaint();  
        }
    }

    public ClaseGrafica clonar() {
        ClaseGrafica c=new ClaseGrafica();
        c.setClase( clase.clonar() );
        c.setTamLetra(tamLetra);
        c.setMinDimension( (Dimension)minDimension.clone() );
        c.setYn(yn);
        c.setLocation( this.getLocation() );
        c.setSize( this.getSize() );
        c.setColorNormal( this.getColorNormal() );
        c.setColorSelect( this.getColorSelect() );
        c.setSelect( this.isSelect() );        
        
        return c;
    }
 
}

