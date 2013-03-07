package Presentacion;

import Estructura.CONST;
import Grafica.Diagrama;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;

public class ToolBar extends JToolBar implements Serializable{

    private Diagrama diagrama;
        
    public ToolBar( ) {
        super();         
        this.setBorderPainted( true );
        ButtonGroup grupoB=new ButtonGroup();        
        
        JToggleButton b; 
        String nom;
        String[] opc={ "Puntero","Clase","Asociacion","Agregacion","Composicion",
                       "Herencia","Deshacer","Rehacer","Eliminar","Guardar" };
        int[] obj={ CONST.PUNTERO, CONST.CLASE, CONST.R_ASOCIACION, 
                    CONST.R_AGREGACION, CONST.R_COMPOSICION, CONST.R_HERENCIA,
                    CONST.DESHACER, CONST.REHACER, CONST.ELIMINAR, CONST.GUARDAR };
        
        for ( int i = 0; i < opc.length; i++ ) {
            nom = opc[i];
            /*URL url=this.getClass().getResource("/Recursos/Img/"+nom+".png"); 
            String dir=url.getPath().replaceAll("%20"," ");*/
            b=new JToggleButton( new ImageIcon("Imagenes/"+nom +".gif") ){
                     public float getAlignmentY() { return 0.5f; }
            };
            b.setMargin( new Insets(3,3,3,3) );
            b.setToolTipText( nom ); 
            //b.setText( nom );
            b.setActionCommand( ""+obj[i] );
            b.addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    int tipo=Integer.parseInt( e.getActionCommand() );
                    if( tipo<CONST.DESHACER )
                        ponerObjeto(e);
                    else
                        ejecutarAccion(e);
                }
            });
            this.add(b);
            grupoB.add( b );              
        }  
        
   
        /*JSlider zoom = new JSlider( 1, 100, 50 );
        //zoom.setLabelTable( "" );
        zoom.setPaintLabels(true);
        zoom.setPaintTicks(true);
        zoom.setMajorTickSpacing( 5 );
        zoom.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged( ChangeEvent evt ) {
                JSlider zoom=(JSlider)evt.getSource();
                diagrama.zoom( zoom.getValue() );
            }
        });                
        zoom.setBorder( BorderFactory.createTitledBorder("Scale") );
        this.add( zoom );*/
        
        this.add( Box.createHorizontalStrut(5) );                   
        this.add( Box.createHorizontalGlue() );  
        
        this.habilitarBoton( true );
    }

    public void setDiagrama(Diagrama diagrama) {
        this.diagrama = diagrama;
    }        
    
    public void ponerObjeto( ActionEvent e ){
        int tipo=Integer.parseInt( e.getActionCommand() );
        diagrama.setTipoObjeto( tipo );
    }
    
    private void ejecutarAccion(ActionEvent e) {
        int tipo=Integer.parseInt( e.getActionCommand() );
        switch( tipo ){
            case CONST.DESHACER: this.diagrama.deshacer();
            break;
            case CONST.REHACER: this.diagrama.rehacer();
            break;
            case CONST.ELIMINAR: this.diagrama.eliminarGraficosSeleccionados();
            break;
            case CONST.GUARDAR: this.diagrama.generarCodigo();
            break;            
        }
    }    
                
    public void habilitarBoton( boolean hab ){
        Component[] botones=this.getComponents();
        for( int i=1; i<botones.length; i++ ){
            botones[i].setEnabled( hab );
        }
    }
       
    
}
