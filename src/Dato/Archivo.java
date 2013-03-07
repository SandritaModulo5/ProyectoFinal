
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Dato;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import javax.swing.JFileChooser;

/**
 *
 * @author Sandrita
 */
public class Archivo {
    
    /**
     * Modo Lectura
     */
    private BufferedReader bufferOut;
    private FileReader fileOut;
    
    /**
     * Modo Escritura
     */
    private BufferedWriter bufferIn;
    private FileWriter fileIn;
    private PrintWriter printIn;
    
    private String dir;
    private String nombre;
            
    public Archivo() {   
        this.dir="Archivos BD/";
        this.nombre="";
        
        this.fileIn=null;
        this.fileOut=null;
        this.bufferOut=null;
        this.bufferIn=null;
        this.printIn=null;          
    }
    
    public Archivo( String nombre ) {   
        this.dir="Archivos BD/";
        this.nombre=nombre;
        
        this.fileIn=null;
        this.fileOut=null;
        this.bufferOut=null;
        this.bufferIn=null;
        this.printIn=null;       
    }   
    

    public void setDir(String dir) {
        this.dir = dir;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDir() {
        return dir;
    }

    public String getNombre() {
        return nombre;
    }
    

    public void abrirLectura( ){
        try{  
            File file = new File ( dir+nombre+".txt" );   
            if( !file.exists() ){  
                file.createNewFile();
            }
            this.fileOut = new FileReader ( file );
            this.bufferOut = new BufferedReader ( fileOut );                     
        }
        catch( Exception e){
            e.printStackTrace(); 
        } 
    }
       
    public void abrirEscritura( ){
        try{  
            File file = new File ( dir+nombre+".java" );              
            this.fileIn = new FileWriter( file, true );
            this.bufferIn = new BufferedWriter( fileIn );
            this.printIn = new PrintWriter( bufferIn );
        }
        catch( Exception e){
            e.printStackTrace(); 
        } 
    }    
    
    public void cerrarArchivo(){
        try{            
            if( bufferOut!=null )   this.bufferOut.close();
            if( fileOut!=null )     this.fileOut.close();            
                        
            if( printIn!=null )     this.printIn.close();
            if( bufferIn!=null )    this.bufferIn.close();
            if( fileIn!=null )      this.fileIn.close();
        }
        catch(Exception e){
            e.printStackTrace();
        } 
    }
      
    
    public void guardarCodigoFuente( String nombreClase, LinkedList<String> codigo ){
        try {
            this.nombre=nombreClase;
            this.abrirEscritura();
            
            int cl=codigo.size();
            String linea;
            for (int i = 0; i < cl; i++) {
                linea = codigo.get( i );
                this.printIn.print( linea );    
            }
            this.cerrarArchivo();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
        
    public void guardar( ){
        try{
              JFileChooser FA = new JFileChooser ();
              FA.setDialogTitle ("Guardar: Codigo Java");
              FA.setFileSelectionMode ( JFileChooser.DIRECTORIES_ONLY );
              //FA.setAcceptAllFileFilterUsed( true ); 
              //FA.addChoosableFileFilter( new Filtro() ); 
              int opc=FA.showSaveDialog (null);
              if( opc==JFileChooser.APPROVE_OPTION ){            
                  this.dir=FA.getSelectedFile().getPath()+"\\";                                     
              }
        }
        catch(Exception e){   
            e.printStackTrace(); 
        } 
    }

    
}
