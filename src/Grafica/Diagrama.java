/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Grafica;

import Estructura.CONST;
import Estructura.GeneradorCodigo;
import Estructura.Relacion;
import Presentacion.ForClase;
import Presentacion.ForRelacion;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import javax.swing.JComponent;

/**
 *
 * @author Sandrita
 */
public class Diagrama extends Figura implements MouseListener, MouseMotionListener{

    private CareTaker careTaker;
    
    private LinkedList<Figura> listaFigura ;
    private LinkedList<Figura> listaFigurasSelec ;
    
    private int tipoObjeto;
    
    private boolean creandoRelacion;
    private boolean seleccionando;       
    private boolean creandoClase=false;
    private boolean modificando=false;
    private boolean moviendo=false; 
    
    private String nombreIni="";
    
    private Point posIniSelect;
    private Point posFinSelect;
    private Point posRango;
    
    private int tipoRelacionAntiguo = CONST.R_ASOCIACION; 
    private int wRango;
    private int hRango;       
    private int indexGraficoIni;
    private int indexGraficoFin;
    
    public Diagrama() {
        super();        
        this.careTaker=new CareTaker();
        
        tipoObjeto = 0;
        creandoRelacion = false;
        seleccionando = false;
        indexGraficoIni = -1;
        indexGraficoFin = -1;
        
        this.listaFigura = new LinkedList<Figura>();
        this.listaFigurasSelec = new LinkedList<Figura>();
        
        this.addMouseListener( this );
        this.addMouseMotionListener( this );        
    }

    public Diagrama( Dimension dim ) {
        super();        
        this.careTaker=new CareTaker();
                
        this.setLocation( 0,80 );
        this.setSize( dim );
        this.addMouseListener( this );
        this.addMouseMotionListener( this );
        
        this.listaFigura = new LinkedList<Figura>();
        this.listaFigurasSelec = new LinkedList<Figura>();

        //Guarda el diagrama vacio
        this.crearMemento();
        
        creandoRelacion = false;
        seleccionando = false;
        indexGraficoIni = -1;
        indexGraficoFin = -1;
        tipoObjeto = CONST.PUNTERO;
    }    
    
//******************************************************************************
    public void paint( Graphics g ){
        super.paint(g);
        g.setColor( Color.WHITE );
        g.fill3DRect( 0, 0, this.getWidth(), this.getHeight(), true);
        if ( listaFigura!=null ){            
            for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                Figura figura = listaFigura.get(i);
                //if( figura instanceof RelacionGrafica )
                    figura.paint( g );
            }
            if ( seleccionando ){
                g.setColor( Color.black );
                g.drawRect( (int)posIniSelect.getX(),(int)posIniSelect.getY(),
                            (int)posFinSelect.getX()-(int)posIniSelect.getX(),
                            (int)posFinSelect.getY()-(int)posIniSelect.getY() );
            }
        }
    }
    
//******************************************************************************
    public void mover(int desplazamientox, int desplazamientoy) {
    
    }
    
    public void ampliar(int zoomin) {
        if ( listaFigura!=null ){
            for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                listaFigura.get(i).ampliar(zoomin);
            }    
        }
        repaint();
    }
    
    public void reducir(int zoomout) {
        if ( listaFigura!=null ){
            for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                listaFigura.get(i).reducir(zoomout);
            }    
        }
        repaint();
    }
    
    public void dibujar() {
    }
    
    public boolean estaDentro(Point p) {
        return false;
    }
    
    public boolean estaSeleccionado(Point p, Dimension d) {
        return false;
    }
    
//******************************************************************************
    public void adicionarFigura( Figura figura ){
        if( figura instanceof ClaseGrafica ){
            ClaseGrafica c=(ClaseGrafica)figura;
            if( !existeClase( c.getNombre() ) ){
                //figura.addMouseListener(this);
                //figura.addMouseMotionListener(this);
                this.adicionarGrafico( figura );
                
                //Guardar el nuevo estado
                this.crearMemento();
            }
        }
        else{
            if( figura instanceof RelacionGrafica ){
                RelacionGrafica relacion=(RelacionGrafica)figura;
                Relacion r=relacion.getRelacion();
                if( !existeRelacion( r.getClaseOrigen().getNombre(), r.getClaseDestino().getNombre(), r.getTipo() ) ){
                    //figura.addMouseListener(this);
                    //figura.addMouseMotionListener(this);                    
                    this.adicionarGrafico(figura);
                    
                    //Guardar el nuevo estado
                    this.crearMemento();                    
                }
            }
        }
    }
    
    public void adicionarGrafico( Figura figura ){
        int tamLetra=12;
        if ( figura instanceof ClaseGrafica ){
            int i=0;
            while (i<listaFigura.size()){
                Figura f = listaFigura.get(i);
                if ( f instanceof ClaseGrafica ){
                    tamLetra=( (ClaseGrafica)f ).getTamLetra();
                    i=listaFigura.size();
                }
            }
            ( (ClaseGrafica)figura ).setTamLetra( tamLetra );
        }
        
        listaFigura.add( figura );
        deseleccionarTodo();
        repaint();
    }
    
    public void eliminarGrafico( Figura figura ){
        if ( figura!=null ){
            if ( figura instanceof ClaseGrafica ){
                Figura graf ;
                if ( listaFigura!=null ){
                    LinkedList<Figura> listaEliminar = new LinkedList<Figura>();
                    for (int i=0; i<listaFigura.size() ; i++ )  {
                        graf = listaFigura.get(i);
                        if ( graf instanceof RelacionGrafica ){
                            RelacionGrafica r = (RelacionGrafica)graf;
                            
                            String nrA=r.getRelacion().getClaseOrigen().getNombre();
                            String nrB=r.getRelacion().getClaseDestino().getNombre();                            
                            String ncg=( (ClaseGrafica)figura ).getNombre();
                                                        
                            if ( nrA.equals( ncg )|| nrB.equals( ncg ) )
                                listaEliminar.add(r);
                        }
                    }
                    for ( int i=0 ; i<listaEliminar.size() ; i++ )  {
                        listaEliminar.get(i).borrar();
                        listaFigura.remove(listaEliminar.get(i));
                    }                    
                }
            }
            figura.borrar();
            listaFigura.remove( figura );
            repaint();
        }
    }
        
    public void eliminarGraficosSeleccionados(){
        if ( listaFigurasSelec!=null ){
            for ( int i = 0 ; i<listaFigurasSelec.size() ;  i++ )  {
                Figura figura = listaFigurasSelec.get(i);
                figura.borrar();
                eliminarGrafico( figura );
            }
            repaint();
        }
    }
    
    public void moverGraficosSeleccionados( Point p ){
        if ( listaFigurasSelec.size()>0 ){
            //System.out.println("moviendo los graficos ***************************");
            //System.out.println(posRango);
            for ( int i=0 ; i<listaFigurasSelec.size() ; i++ )  {
                int w = (int)p.getX()-(int)posRango.getX();
                int h = (int)p.getY()-(int)posRango.getY();
                //System.out.println("width="+w+", height="+h);
                moverGrafico( listaFigurasSelec.get(i),w,h );
            }
            posRango = p;
            //System.out.println("moviendo los graficos Fin ***************************");
            repaint();
        }
    }
    
    private void moverGrafico( Figura figura, int w, int h){
        if ( figura instanceof ClaseGrafica ){
            int x = (int)figura.getLocation().getX();
            int y = (int)figura.getLocation().getY();
            
            figura.setLocation( new Point(x+w,y+h) );
            actualizarPosicionesRelaciones( (ClaseGrafica)figura );
            repaint();
        }
    }
    
    private void actualizarPosicionesRelaciones( ClaseGrafica clase ){
        if ( listaFigura!=null ){
            for ( int i=0 ; i<listaFigura.size(); i++ )  {
                Figura figura = listaFigura.get(i);
                if ( figura instanceof RelacionGrafica ){
                    RelacionGrafica r = (RelacionGrafica)figura;
                    if ( r.getRelacion().getClaseOrigen().getNombre().equals(clase.getNombre()) )
                        r.setPcA( clase.getLocation() );
                    if ( r.getRelacion().getClaseDestino().getNombre().equals(clase.getNombre()) )
                        r.setPcB( clase.getLocation() );
                }
            }
            
        }
    }
    
    private void actualizarRelaciones( String nombreAntiguo, ClaseGrafica clase ){        
        for ( int i=0 ; i<listaFigura.size() ; i++ )  {
            Figura figura = listaFigura.get(i);
            if ( figura instanceof RelacionGrafica ){
                RelacionGrafica r = (RelacionGrafica)figura;
                if ( r.getRelacion().getClaseOrigen().getNombre().equals(nombreAntiguo) ){
                    r.setPcA( clase.getLocation() );
                    r.setDmA( clase.getSize() );
                    r.repaint();
                }
                if ( r.getRelacion().getClaseDestino().getNombre().equals(nombreAntiguo) ){
                    r.setPcB( clase.getLocation() );
                    r.setDmB( clase.getSize() );
                    r.repaint();
                }
            }
        }
        
    }
    
    private void deseleccionarTodo(){
        for ( int i=0 ; i<listaFigurasSelec.size() ; i++ )  {
            listaFigurasSelec.get(i).setSelect(false);
            listaFigurasSelec.get(i).repaint();
        }
        listaFigurasSelec=new LinkedList<Figura>();
        posRango=new Point(-1,-1);
    }
    
//******************************************************************************
    public void mouseDragged( MouseEvent e ){
        if(seleccionando==true){
            posFinSelect = e.getPoint();
            repaint();
        }else{
            moverGraficosSeleccionados( e.getPoint() );
        }
    }
    
    public void mouseMoved(MouseEvent e){
    }
    
//******************************************************************************
    public void mouseClicked( MouseEvent e ){   
         if ( tipoObjeto == CONST.R_ASOCIACION || tipoObjeto == CONST.R_AGREGACION ||
              tipoObjeto==CONST.R_COMPOSICION  || tipoObjeto==CONST.R_HERENCIA ){
             Figura figura = getFigura(e.getPoint());
             if ( (figura != null) && (figura instanceof ClaseGrafica) ){
                 ClaseGrafica CD = (ClaseGrafica)figura;
                 if ( creandoRelacion==true ){
                     ClaseGrafica cA = (ClaseGrafica)getFigura(indexGraficoIni); 
                     
                     RelacionGrafica r=new RelacionGrafica( tipoObjeto, "", cA, "", CD, "" );

                     /*ventanaRelacion = new frmRelacion(this);
                     ventanaRelacion.setLocation(e.getPoint());
                     ventanaRelacion.setRelacion(r);
                     ventanaRelacion.setAlwaysOnTop(true);
                     ventanaRelacion.setVisible(true);*/
                     this.verForm( r, e.getPoint() );
                     
                     //this.listaFigura.add( r );
                     //this.add( r );  
                                                  
                     creandoRelacion = false;
                     indexGraficoIni = -1;
                 }else{
                     creandoRelacion = true;
                     indexGraficoIni = getIndexFigura( CD );
                     indexGraficoFin = -1;
                 }
                 repaint();
             }
         }
         else {
             if ( tipoObjeto==CONST.PUNTERO ){//modificar
                 if ( e.getClickCount()>=2 ){
                     Figura figura = getFigura(e.getPoint());
                     if ( figura != null && figura instanceof ClaseGrafica){
                         ClaseGrafica c = (ClaseGrafica)figura;
                         /*ventanaClase = new frmClase(this);
                         ventanaClase.setLocation(e.getPoint());
                         ventanaClase.setClase(c);*/
                         nombreIni = c.getNombre();
                         modificando=true;
                         //ventanaClase.setVisible(true);  
                         this.verForm( c, e.getPoint() );
                     }else{
                         if (figura != null && figura instanceof RelacionGrafica){
                             RelacionGrafica r = (RelacionGrafica)figura;
                             tipoRelacionAntiguo = r.getRelacion().getTipo();
                             /*ventanaRelacion = new frmRelacion(this);
                             ventanaRelacion.setLocation(e.getPoint());
                             ventanaRelacion.setRelacion(r);*/
                             nombreIni="hola";
                             modificando = true;
                             //ventanaRelacion.setVisible(true);   
                             this.verForm( r, e.getPoint() );
                             
                             //this.listaFigura.add( r );
                            //this.add( r );   
                         }
                     }
                 }
             }
         }
    }
    
    public void mousePressed(MouseEvent e){
        if (tipoObjeto==CONST.PUNTERO){
            Figura figura ;
            boolean b=false;
            if (listaFigura != null){
                for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                    figura = listaFigura.get(i);
                    if ( figura.estaDentro(e.getPoint()) ){
                        b=true;
                        if ( figura.isSelect() ){
                            posRango=e.getPoint();
                        }else{
                            deseleccionarTodo();
                            figura.setSelect(true);
                            listaFigurasSelec.add(figura);
                            posRango=e.getPoint();
                            repaint();
                        }                        
                    }
                }
                if (b==false){
                    deseleccionarTodo();
                    seleccionando=true;
                    moviendo=false;
                    posIniSelect=e.getPoint();
                    repaint();
                    return;
                }else{
                    seleccionando=false;
                    moviendo=true;
                }
            }
        }
        else 
            if (tipoObjeto==CONST.CLASE){
                deseleccionarTodo();
                creandoClase = true;
                /*ventanaClase = new frmClase(this);
                ventanaClase.setLocation(e.getPoint());
                ventanaClase.setClase(new Clase("nueva Clase", e.getPoint()));
                ventanaClase.setVisible(true);*/
                //this.verForm( c, e.getPoint() );
                ClaseGrafica clase=new ClaseGrafica( "", e.getPoint() );
                this.verForm( clase, e.getPoint() );

                //this.listaFigura.add(clase);
                //this.add( clase );            
            }
        repaint();
    }
    
    public void mouseReleased(MouseEvent e){
         if ( seleccionando ){
             double x,y,x1,y1;
             x = posIniSelect.getX();
             y = posIniSelect.getY();
             x1 = e.getPoint().getX();
             y1 = e.getPoint().getY();
             if (x<x1){
                 if (y<y1){
                     posFinSelect = new Point((int)x1,(int)y1);
                 }else{
                     posIniSelect = new Point((int)x,(int)y1);
                     posFinSelect = new Point((int)x1,(int)y);
                 }
             }else{
                 if (y<y1){
                     posIniSelect = new Point((int)x1,(int)y);
                     posFinSelect = new Point((int)x,(int)y1);
                 }else{
                     posIniSelect = new Point((int)x1,(int)y1);
                     posFinSelect = new Point((int)x,(int)y);
                 }
             }
             if (listaFigura!=null){
                 deseleccionarTodo();
                 for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                     Figura f = listaFigura.get(i);
                     if ( f.estaSeleccionado(posIniSelect,new Dimension((int)posFinSelect.getX()-(int)posIniSelect.getX(),(int)posFinSelect.getY()-(int)posIniSelect.getY()))){
                         f.setSelect(true);
                         listaFigurasSelec.add( f );
                     }
                     else
                         f.setSelect( false );
                 }
                 repaint();
             }
             seleccionando = false;
             repaint();
         }
         else{
             if ( moviendo ){
                 if ( listaFigurasSelec!=null ){
                    /* for ( int i=0 ; i<listaFigurasSelec.size() ; i++ )  {
                         Figura f = listaFigurasSelec.get(i);
                         if ( f instanceof ClaseGrafica ){
                             ClaseGrafica c = (ClaseGrafica)f;
                             try {
                                 //conector.enviarPaqueteClase(c.getNombre(),c.getPosicion(), (LinkedList)c.getAtributos(), (LinkedList)c.getMetodos(),
                                //     ACCION_MODIFICACION);
                             } catch (RemoteException f) {
                                 // TODO
                             }
                         }
                     }*/                     
                 }
                 moviendo=false;   
             }
         }
         posIniSelect=new Point(0,0);
         posFinSelect=new Point(0,0);        
    }
    
    public void mouseEntered(MouseEvent e){
    }
    
    public void mouseExited(MouseEvent e){
    }
    
//******************************************************************************

    public Figura getFigura(Point p){
        if (listaFigura!=null){
            for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                if ( listaFigura.get(i).estaDentro(p) )
                    return  listaFigura.get(i);
            }    
        }
        return null;
    }
    
    public Figura getFigura(int index){
        if (listaFigura!=null && index>=0 && index<listaFigura.size())
            return listaFigura.get(index);
        else
            return null;
    }
    
    private int getIndexFigura( Figura figura){
        if (listaFigura !=null){
            for ( int i=0 ; i<listaFigura.size() ; i++ )  {
                if ( listaFigura.get(i).esIgual(figura) ) 
                    return i;
            }
        }
        return -1;
    }
    
//******************************************************************************
    public void setTipoObjeto(int tipo){
        this.tipoObjeto = tipo;
        restarAll();
    }
    
    public int getTipoObjeto(){
        return tipoObjeto;
    }
    
//******************************************************************************
    private void restarAll(){
        indexGraficoIni = -1;
        indexGraficoFin = -1;
        creandoRelacion = false;
        seleccionando = false;
        deseleccionarTodo();
        repaint();
    }
    
    public void modificarGrafico( String nombre, int tipoRelacion, Figura figura ){        
         int i=0;
         while (i<listaFigura.size()){
             boolean  b = false;
             if ( listaFigura.get(i) instanceof ClaseGrafica){                 
                 ClaseGrafica c = (ClaseGrafica)listaFigura.get(i);
                 if ( figura instanceof ClaseGrafica ){
                     if ( c.getNombre().equals(nombre) ){
                         ClaseGrafica g = (ClaseGrafica)listaFigura.get(i);
                         ClaseGrafica aux = (ClaseGrafica)figura;
                         //g.setCopy(aux);
                         g.redimensionar();
                         actualizarRelaciones(nombre,g);
                         i=listaFigura.size();
                         i++;
                         return;
                     }
                 }                
             }else{                 
                 RelacionGrafica r = (RelacionGrafica)listaFigura.get(i);
                 if ( figura instanceof RelacionGrafica ){
                     
                    String nrA=r.getRelacion().getClaseOrigen().getNombre();
                    String nrB=r.getRelacion().getClaseDestino().getNombre();

                    String nfA=((RelacionGrafica)figura).getRelacion().getClaseOrigen().getNombre();
                    String nfB=((RelacionGrafica)figura).getRelacion().getClaseDestino().getNombre();

                    if ( nrA.equals(nfA) && nrB.equals(nfB) && 
                         r.getRelacion().getTipo()==tipoRelacion ){ 

                         RelacionGrafica g = (RelacionGrafica)listaFigura.get(i);
                         RelacionGrafica aux = (RelacionGrafica)figura;
                         //g.setCopy(aux);
                         i=listaFigura.size();
                         i++;
                         return;
                     }                    
                 }
             }
             i++;
         }
         repaint();
    }
    
//******************************************************************************

    public boolean existeClase( String nombreClase ){
        for ( int i=0 ; i<listaFigura.size() ; i++ )  {
            if ( listaFigura.get(i) instanceof ClaseGrafica ){
                if ( ((ClaseGrafica)listaFigura.get(i)) .getNombre().equals(nombreClase) ) 
                    return true;
            }
        }
        return false;
    }
    
    public boolean existeRelacion( String nombreClaseA, String nombreClaseB, int tipoRelacion ){
        for ( int i=0 ; i<listaFigura.size() ; i++ )  {
            if ( listaFigura.get(i) instanceof RelacionGrafica ){
                RelacionGrafica r = (RelacionGrafica)listaFigura.get(i);
                
               String nrA=r.getRelacion().getClaseOrigen().getNombre();
               String nrB=r.getRelacion().getClaseDestino().getNombre();
                            
                if ( nrA.equals(nombreClaseA) && nrB.equals(nombreClaseB) && 
                     r.getRelacion().getTipo()==tipoRelacion ) 
                    return true;
            }
        }
        return false;
    }

    public void eliminarClase( String nombre ){
        Figura figura = getFigura(nombre);
        if (figura!=null){
            figura.borrar();
            eliminarGrafico(figura);
        }
    }
    
    private Figura getFigura( String nombre ){
        for ( int i=0 ; i<listaFigura.size() ; i++ )  {
            if ( listaFigura.get(i) instanceof ClaseGrafica){
                if ( ((ClaseGrafica)listaFigura.get(i)).getNombre().equals(nombre) ) 
                    return listaFigura.get(i);
            }
        }
        return null;        
    }

    public boolean esIgual( Figura figura ) {
        return false;
    }
    
//******************************************************************************
    private void verForm( Figura figura, Point punto ){
        if( figura instanceof ClaseGrafica ){
            ForClase f=new ForClase( null, true, (ClaseGrafica)figura, this );
            f.setLocation( punto );
            f.show();
        }
        else
            if( figura instanceof RelacionGrafica ){
                ForRelacion f=new ForRelacion( null, true, (RelacionGrafica)figura, this );
                f.setLocation( punto );
                f.show();                
            }
    }    
    
/**
 * Metodos del Memento-Originador
 */    
    
    public void crearMemento(){
        Memento memento=new Memento();
        memento.setEstado(listaFigura );
        
        this.careTaker.adicionar( memento );
    }
    
    public void setMemento( Memento memento ){
        this.listaFigura=memento.getEstado();
        repaint();
    }
    
    public void rehacer(){
        this.setMemento( careTaker.rehacer() );
    }
    
    public void deshacer(){
        this.setMemento( careTaker.deshacer() );
    }   
    
    public void generarCodigo(){
        GeneradorCodigo generador=new GeneradorCodigo();
        
        for (int i = 0; i < listaFigura.size(); i++) {
            Figura figura = listaFigura.get(i);
            if( figura instanceof ClaseGrafica ){
                ClaseGrafica clase=(ClaseGrafica)figura;
                generador.addClase(  clase.getClase() );
            }
            if( figura instanceof RelacionGrafica ){
                RelacionGrafica relacion=(RelacionGrafica)figura;
                generador.addRelacion(  relacion.getRelacion() );
            }
        }
        generador.bajarCodigo();                
    }
    
//******************************************************************************
    public void zoom( int zoom ){
        /*AffineTransform at = new AffineTransform();
        double shear = zoom / 10.0;
        at.shear(shear, 0);
        
        JXTransformer t = new JXTransformer( this );
        t.setTransform(at);*/
    }
    
}

