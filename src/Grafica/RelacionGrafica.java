/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafica;

import Estructura.CONST;
import Estructura.Prototipo;
import Estructura.Relacion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Sandrita
 */
public class RelacionGrafica extends Figura implements Prototipo{

    private Relacion relacion;
            
    private Point pcA;
    private Point pcB;
    private Dimension dmA;
    private Dimension dmB;
    private int tamLetra=12;  

    public RelacionGrafica( ){
        super();
        
        this.pcA=null;
        this.pcB=null;
        
        this.dmA=null;
        this.dmB=null;
        
        this.setColorNormal( Color.BLACK );
        this.setColorSelect( Color.RED );
        
        this.relacion=new Relacion( );
    }   
    
    public RelacionGrafica( int tipoRelacion, String nombre, 
                            ClaseGrafica claseOrigen, String rolOrigen, 
                            ClaseGrafica claseDestino, String rolDestino ){
        super();

        this.pcA=claseOrigen.getLocation();
        this.pcB=claseDestino.getLocation();

        this.dmA=claseOrigen.getSize();
        this.dmB=claseDestino.getSize();
        
        this.setColorNormal( Color.BLACK );
        this.setColorSelect( Color.RED );
        
        this.relacion=new Relacion( tipoRelacion, nombre, claseOrigen.getClase(),
                                    claseDestino.getClase(), rolOrigen, rolDestino );
    }

    public void setRelacion(Relacion relacion) {
        this.relacion = relacion;
    }
    
    public Relacion getRelacion() {
        return relacion;
    }

    public void setPcA(Point pcA) {
        this.pcA = pcA;
    }

    public void setPcB(Point pcB) {
        this.pcB = pcB;
    }

    public void setDmA(Dimension dmA) {
        this.dmA = dmA;
    }

    public void setDmB(Dimension dmB) {
        this.dmB = dmB;
    }
        
    
 //******************************************************************************
    public void mover(int desplazamientox, int desplazamientoy) {
    }
    
    public void ampliar( int zoomin ) {
        int xA = (int)pcA.getX();
        int yA = (int)pcA.getY();
        int xB = (int)pcB.getX();
        int yB = (int)pcB.getY();
        xA = xA * zoomin;
        yA = yA * zoomin;
        xB = xB * zoomin;
        yB = yB * zoomin;
        int wA = (int)dmA.getWidth();
        int hA = (int)dmA.getHeight();
        int wB = (int)dmB.getWidth();
        int hB = (int)dmB.getHeight();
        wA = wA * zoomin;
        hA = hA * zoomin;
        wB = wB * zoomin;
        hB = hB * zoomin;
        pcA=new Point(xA,yA);
        dmA=new Dimension(wA,hA);
        pcB=new Point(xB,yB);
        dmB=new Dimension(wB,hB);
        tamLetra = tamLetra*zoomin;
    }
    
    public void reducir( int zoomout ) {
        int xA = (int)pcA.getX();
        int yA = (int)pcA.getY();
        int xB = (int)pcB.getX();
        int yB = (int)pcB.getY();
        xA = xA / zoomout;
        yA = yA / zoomout;
        xB = xB / zoomout;
        yB = yB / zoomout;
        int wA = (int)dmA.getWidth();
        int hA = (int)dmA.getHeight();
        int wB = (int)dmB.getWidth();
        int hB = (int)dmB.getHeight();
        wA = wA / zoomout;
        hA = hA / zoomout;
        wB = wB / zoomout;
        hB = hB / zoomout;
        pcA=new Point(xA,yA);
        dmA=new Dimension(wA,hA);
        pcB=new Point(xB,yB);
        dmB=new Dimension(wB,hB);
        tamLetra = tamLetra/zoomout;
    }
    
    public void dibujar() {
    }
    
    public boolean estaDentro( Point p ) {
        return LibreriaMatematica.estaEnSegmento(p,getPos1(),getPos2(),3);
    }
    
    public boolean estaSeleccionado( Point p, Dimension d ) {
        return false;
    }
    
    public boolean esIgual( Figura figura ) {
        if( figura instanceof RelacionGrafica ){
            RelacionGrafica r = (RelacionGrafica)figura;
            String rcA=relacion.getClaseOrigen().getNombre();
            String rcB=relacion.getClaseDestino().getNombre();

            String NA= ((RelacionGrafica)figura).getRelacion().getClaseOrigen().getNombre();
            String ND= ((RelacionGrafica)figura).getRelacion().getClaseDestino().getNombre();
            int TR= ((RelacionGrafica)figura).getRelacion().getTipo();

            return ( rcA.equals(NA )&& rcB.equals(ND)&& relacion.getTipo()==TR );
        }
        else
            return false;
                 
    }

//******************************************************************************
    public Point getPos0(){
        return new Point((int)pcA.getX()+((int)dmA.getWidth()/2),(int)pcA.getY()+((int)dmA.getHeight()/2));
    }
    
    public Point getPos1(){
        Point a1,a2,a3,a4;
        a1 = pcA;
        a2 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY());
        a3 = new Point((int)pcA.getX(),(int)pcA.getY()+(int)dmA.getHeight());
        a4 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY()+(int)dmA.getHeight());
        return LibreriaMatematica.interseccionRectanguloSegmento(a1,a2,a3,a4,getPos0(),getPos3());
    }
    
    public Point getPos2(){
        Point b1,b2,b3,b4;
        b1 = pcB;
        b2 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY());
        b3 = new Point((int)pcB.getX(),(int)pcB.getY()+(int)dmB.getHeight());
        b4 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY()+(int)dmB.getHeight());
        return LibreriaMatematica.interseccionRectanguloSegmento(b1,b2,b3,b4,getPos0(),getPos3());
    }
    
    public Point getPos3(){
        return new Point((int)pcB.getX()+((int)dmB.getWidth()/2),(int)pcB.getY()+((int)dmB.getHeight()/2));
    }
    
//******************************************************************************    
    public void paint( Graphics g ){
        super.paint(g);
        if (isSelect()) g.setColor(getColorSelect());
        else g.setColor(getColorNormal());
        
        switch (relacion.getTipo()){
            case CONST.R_ASOCIACION : pintarAsociacion(g); break; 
            case CONST.R_AGREGACION : pintarAgregacion(g);break;
            case CONST.R_HERENCIA : pintarGeneralizacion(g);break;
            case CONST.R_COMPOSICION : pintarComposicion(g);break;
            default : pintarAsociacion(g);
        }
    }
    
    private void pintarAsociacion( Graphics g ){
        Point a1,a2,a3,a4;
        a1 = pcA;
        a2 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY());
        a3 = new Point((int)pcA.getX(),(int)pcA.getY()+(int)dmA.getHeight());
        a4 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY()+(int)dmA.getHeight());
        Point b1,b2,b3,b4;
        b1 = pcB;
        b2 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY());
        b3 = new Point((int)pcB.getX(),(int)pcB.getY()+(int)dmB.getHeight());
        b4 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY()+(int)dmB.getHeight());
        Point p1,p2,p3,p4;
        p1 = getPos0();
        p4 = getPos3();
        p2 = LibreriaMatematica.interseccionRectanguloSegmento(a1,a2,a3,a4,p1,p4);
        p3 = LibreriaMatematica.interseccionRectanguloSegmento(b1,b2,b3,b4,p1,p4);
        
        g.drawLine((int)p2.getX(),(int)p2.getY(),(int)p3.getX(),(int)p3.getY());
        
        if (isSelect())
            g.setColor(getColorSelect());
        else g.setColor(getColorNormal());
        int x1 = (int)p2.getX();
        int x2 = (int)p3.getX();
        int y1 = (int)p2.getY();
        int y2 = (int)p3.getY();
        int x = 0;
        int y = 0;
        g.drawString(relacion.getNombre(),(x1+x2)/2,(y1+y2)/2);
        if (x1<x2)  x2 = x2 - (relacion.getRolDestino().length()*(tamLetra/4));
        else        x1 = x1 + (relacion.getRolOrigen().length()*(tamLetra/4));
        if (y1<y2)  y1 = y1+tamLetra;
        else        y2 = y2 + tamLetra;
    
        g.drawString(relacion.getRolOrigen(),x1,y1);
        g.drawString(relacion.getRolDestino(),x2,y2);
    }
    
    private void pintarGeneralizacion( Graphics g ){
        Point a1,a2,a3,a4;
        a1 = pcA;
        a2 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY());
        a3 = new Point((int)pcA.getX(),(int)pcA.getY()+(int)dmA.getHeight());
        a4 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY()+(int)dmA.getHeight());
        Point b1,b2,b3,b4;
        b1 = pcB;
        b2 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY());
        b3 = new Point((int)pcB.getX(),(int)pcB.getY()+(int)dmB.getHeight());
        b4 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY()+(int)dmB.getHeight());
        Point p1,p2,p3,p4;
        p1 = getPos0();
        p4 = getPos3();
        p2 = LibreriaMatematica.interseccionRectanguloSegmento(a1,a2,a3,a4,p1,p4);
        p3 = LibreriaMatematica.interseccionRectanguloSegmento(b1,b2,b3,b4,p1,p4);
        
        
        Point p = LibreriaMatematica.getP(p2,p3,tamLetra);
        g.drawLine((int)p2.getX(),(int)p2.getY(),(int)p.getX(),(int)p.getY());
        Point[] vp = LibreriaMatematica.getPuntosTriangulo(p,p3);
        g.drawLine((int)vp[0].getX(),(int)vp[0].getY(),(int)p3.getX(),(int)p3.getY());
        g.drawLine((int)vp[1].getX(),(int)vp[1].getY(),(int)p3.getX(),(int)p3.getY());
        g.drawLine((int)vp[0].getX(),(int)vp[0].getY(),(int)vp[1].getX(),(int)vp[1].getY());
    }
    
    private void pintarAgregacion( Graphics g ){
        Point a1,a2,a3,a4;
        a1 = pcA;
        a2 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY());
        a3 = new Point((int)pcA.getX(),(int)pcA.getY()+(int)dmA.getHeight());
        a4 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY()+(int)dmA.getHeight());
        Point b1,b2,b3,b4;
        b1 = pcB;
        b2 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY());
        b3 = new Point((int)pcB.getX(),(int)pcB.getY()+(int)dmB.getHeight());
        b4 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY()+(int)dmB.getHeight());
        Point p1,p2,p3,p4;
        p1 = getPos0();
        p4 = getPos3();
        p2 = LibreriaMatematica.interseccionRectanguloSegmento(a1,a2,a3,a4,p1,p4);
        p3 = LibreriaMatematica.interseccionRectanguloSegmento(b1,b2,b3,b4,p1,p4);
        
        
        Point p = LibreriaMatematica.getP(p2,p3,tamLetra);
        g.drawLine((int)p2.getX(),(int)p2.getY(),(int)p.getX(),(int)p.getY());
        Point q =LibreriaMatematica.getP(p2,p3,tamLetra/2);
        Point[] vp = LibreriaMatematica.getPuntosTriangulo(q,p3);
        /*
        g.drawLine((int)vp[0].getX(),(int)vp[0].getY(),(int)p.getX(),(int)p.getY());
        g.drawLine((int)vp[0].getX(),(int)vp[0].getY(),(int)p3.getX(),(int)p3.getY());
        g.drawLine((int)vp[1].getX(),(int)vp[1].getY(),(int)p.getX(),(int)p.getY());
        g.drawLine((int)vp[1].getX(),(int)vp[1].getY(),(int)p3.getX(),(int)p3.getY());
        */
        Polygon poligono=new Polygon();
        poligono.addPoint((int)vp[0].getX(), (int)vp[0].getY());
        poligono.addPoint((int)p3.getX(), (int)p3.getY());
        poligono.addPoint((int)vp[1].getX(), (int)vp[1].getY());
        poligono.addPoint((int)p.getX(), (int)p.getY());
        
        g.drawPolygon(poligono);
    }
    
    private void pintarComposicion( Graphics g ){
        Point a1,a2,a3,a4;
        a1 = pcA;
        a2 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY());
        a3 = new Point((int)pcA.getX(),(int)pcA.getY()+(int)dmA.getHeight());
        a4 = new Point((int)pcA.getX()+(int)dmA.getWidth(),(int)pcA.getY()+(int)dmA.getHeight());
        Point b1,b2,b3,b4;
        b1 = pcB;
        b2 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY());
        b3 = new Point((int)pcB.getX(),(int)pcB.getY()+(int)dmB.getHeight());
        b4 = new Point((int)pcB.getX()+(int)dmB.getWidth(),(int)pcB.getY()+(int)dmB.getHeight());
        Point p1,p2,p3,p4;
        p1 = getPos0();
        p4 = getPos3();
        p2 = LibreriaMatematica.interseccionRectanguloSegmento(a1,a2,a3,a4,p1,p4);
        p3 = LibreriaMatematica.interseccionRectanguloSegmento(b1,b2,b3,b4,p1,p4);
        
        
        Point p = LibreriaMatematica.getP(p2,p3,tamLetra);
        g.drawLine((int)p2.getX(),(int)p2.getY(),(int)p.getX(),(int)p.getY());
        Point q =LibreriaMatematica.getP(p2,p3,tamLetra/2);
        Point[] vp = LibreriaMatematica.getPuntosTriangulo(q,p3);
        Polygon poligono=new Polygon();
        poligono.addPoint((int)vp[0].getX(), (int)vp[0].getY());
        poligono.addPoint((int)p3.getX(), (int)p3.getY());
        poligono.addPoint((int)vp[1].getX(), (int)vp[1].getY());
        poligono.addPoint((int)p.getX(), (int)p.getY());
        g.fillPolygon(poligono);
        g.drawPolygon(poligono);
    }
    
//******************************************************************************
    public void setTamLetra(int tamLetra) {
        this.tamLetra = tamLetra;
    }
    
    public int getTamLetra() {
        return tamLetra;
    }

    public RelacionGrafica clonar() {
        RelacionGrafica r=new RelacionGrafica( );
        r.setRelacion( relacion.clonar() );
        r.setTamLetra(tamLetra);
        r.setPcA( (Point)pcA.clone() );
        r.setPcB( (Point)pcB.clone() );
        r.setDmA( (Dimension)dmA.clone() );
        r.setDmB( (Dimension)dmB.clone() );
        r.setLocation( this.getLocation() );
        r.setSize( this.getSize() );
        r.setColorNormal( this.getColorNormal() );
        r.setColorSelect( this.getColorSelect() );
        r.setSelect( this.isSelect() );
        
        return r;
    }

}
