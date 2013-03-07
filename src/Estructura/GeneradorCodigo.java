/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructura;

import Dato.Archivo;
import java.util.LinkedList;

/**
 *
 * @author Sandrita
 */
public class GeneradorCodigo {

    private LinkedList<Clase> listaClase;
    private LinkedList<Relacion> listaRelacion;
    
    private Archivo archivo;
    
    public GeneradorCodigo() {
        this.listaClase=new LinkedList<Clase>();
        this.listaRelacion=new LinkedList<Relacion>();
        
        this.archivo=new Archivo();
    }

    public void addClase( Clase clase ){
        this.listaClase.add( clase );
    }
    
    public void addRelacion( Relacion relacion ){
        this.listaRelacion.add( relacion );
    }
    
    
    public void bajarCodigo(){
        this.archivo.guardar();
        this.generarCodigo();
    }
    
    private void generarCodigo(){
        for (int i = 0; i < listaClase.size(); i++) {
            Clase clase = listaClase.get(i);
            LinkedList<String> codigoFuente=this.generarCodigo(clase);
            this.guardarClase( clase.getNombre(), codigoFuente);
        }        
    }
    
    private LinkedList<String> generarCodigo( Clase clase ){
        LinkedList<Relacion> listaRelacion=this.obtenerRelaciones(clase);
        
        LinkedList<String> codigoFuente=new LinkedList<String>();
        
        String linea=clase.generarSegmentoNombre();
        codigoFuente.add(linea);
        
        this.concatenarListas( codigoFuente, clase.generarSegmentoAtributos() );
        
        LinkedList<String> listaHerencia;               
        LinkedList<String> listaAgregacion;        
        LinkedList<String> listaComposicion;
        
        boolean sw=false;
        
        LinkedList<String> listaConstructor=new LinkedList<String>();  
        linea="\n\tpublic "+clase.getNombre()+"(){\n";
        listaConstructor.add( linea );
        
        for (int i = 0; i < listaRelacion.size(); i++) {
            Relacion relacion = listaRelacion.get(i);
            switch( relacion.getTipo() ){
                case CONST.R_HERENCIA:{
                    listaHerencia=this.generarHerencia(relacion);
                    
                    linea=codigoFuente.get(0);
                    linea=linea+listaHerencia.get(0);
                            
                    codigoFuente.set( 0,linea );
                    
                    linea=listaHerencia.get(1);
                    listaConstructor.add(linea);
                    
                    sw=true;
                    break;
                }
                case CONST.R_ASOCIACION:{
                    linea=this.generarAsociacion(relacion);                    
                    codigoFuente.add(linea);
                    break;
                }
                case CONST.R_AGREGACION:{
                    listaAgregacion=this.generarAgregacion(relacion);
                    
                    linea=listaAgregacion.get(0);
                    codigoFuente.add( linea );
                    
                    linea=listaAgregacion.get(1);
                    listaConstructor.add(linea);
                    break;
                }
                case CONST.R_COMPOSICION:{
                    listaComposicion=this.generarComposicion(relacion);
                    
                    linea=listaComposicion.get(0);
                    codigoFuente.add( linea );
                    
                    linea=listaComposicion.get(1);
                    listaConstructor.add(linea);
                    linea=listaComposicion.get(2);
                    listaConstructor.add(linea);                    
                    break;
                }                
            }
        }
        if( !sw ){
            linea=codigoFuente.get(0);
            linea=linea+"{\n\n";  
            codigoFuente.set( 0,linea );
        }
        
        linea="\t}\n\n";
        listaConstructor.add(linea);
        
        this.concatenarListas(codigoFuente, listaConstructor);
        this.concatenarListas(codigoFuente, clase.generarSegmentoMetodos());

        linea="\n}\n";
        codigoFuente.add(linea);
        return codigoFuente;
    }
    
    private void concatenarListas( LinkedList<String> L1, LinkedList<String> L2 ){
        for (int i = 0; i < L2.size(); i++) {
            String linea = L2.get(i);
            L1.add( linea );
        }
    }
    
    
    private LinkedList<Relacion> obtenerRelaciones( Clase clase ){
        LinkedList<Relacion> lista=new LinkedList<Relacion>();
        for (int i = 0; i < listaRelacion.size(); i++) {
            Relacion relacion = listaRelacion.get(i);
            Clase c=null;
            if( relacion.getTipo()==CONST.R_COMPOSICION ||
                relacion.getTipo()==CONST.R_AGREGACION )
                c=relacion.getClaseDestino();
            else
                c=relacion.getClaseOrigen();
            if( c.equals(clase) )
                lista.add(relacion);
        }
        return lista;
    }
        
    
    private LinkedList<String> generarHerencia( Relacion relacion ){
        LinkedList<String> lista=new LinkedList<String>();
        
        String codigo=" extends "+relacion.getClaseDestino().getNombre()+" {\n\n";
        lista.add(codigo);
        codigo="\t\tsuper();\n";
        lista.add(codigo);
                
        return lista;
    }
    
    private String generarAsociacion( Relacion relacion ){       
        String nombreClase=relacion.getClaseDestino().getNombre();
        
        String codigo="\tprivate "+nombreClase+" "+nombreClase.toLowerCase()+";\n";
                
        return codigo;
    }

    private LinkedList<String> generarAgregacion( Relacion relacion ){
        LinkedList<String> lista=new LinkedList<String>();
        
        String nombreClase=relacion.getClaseOrigen().getNombre();
        
        String codigo="\tprivate LinkedList<"+nombreClase+"> lista"+nombreClase+";\n";
        lista.add(codigo);
        
        codigo="\t\tlista"+nombreClase+"=new LinkedList<"+nombreClase+">();\n";
        lista.add(codigo);
                
        return lista;
    }
    
    private LinkedList<String> generarComposicion( Relacion relacion ){
        LinkedList<String> lista=new LinkedList<String>();
        
        String nombreClase=relacion.getClaseOrigen().getNombre();
        
        String codigo="\tprivate LinkedList<"+nombreClase+"> lista"+nombreClase+";\n";
        lista.add(codigo);
        
        codigo="\t\tlista"+nombreClase+"=new LinkedList<"+nombreClase+">();\n";
        lista.add(codigo);
        
        codigo="\t\tlista"+nombreClase+".add( new "+nombreClase+"() );\n";
        lista.add(codigo);
        
        return lista;
    }    
    
    
    private void guardarClase( String nombreClase, LinkedList<String> codigoFuente ){
        this.archivo.guardarCodigoFuente( nombreClase, codigoFuente);
    }
    
}
