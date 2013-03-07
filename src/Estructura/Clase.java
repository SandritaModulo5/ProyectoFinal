/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Estructura;

import java.util.LinkedList;

/**
 *
 * @author Sandrita
 */
public class Clase implements Prototipo{

    private String nombre;
    private String tipo;
    
    private LinkedList<Atributo> listaAtributos;
    private LinkedList<Metodo> listaMetodos;
    
    public Clase() {
        this.nombre = "Clase";
        this.tipo = CONST.C_NORMAL; 
        this.listaAtributos = new LinkedList<Atributo>();
        this.listaMetodos = new LinkedList<Metodo>();
    }

    public Clase(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.listaAtributos = new LinkedList<Atributo>();
        this.listaMetodos = new LinkedList<Metodo>();        
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public LinkedList<Atributo> getListaAtributos() {
        return listaAtributos;
    }

    public LinkedList<Metodo> getListaMetodos() {
        return listaMetodos;
    }  
    
    /** 
     * Metodos para controlar a los listaAtributos 
     */
    public void adicionarAtributo( String nombre, String tipoDato, int acceso ){
        Atributo atributo=new Atributo(nombre, tipoDato, acceso);
        if( !contiene( atributo ) )
            listaAtributos.add(atributo);
    }  
    
    public void adicionarAtributo( Atributo atributo ){
        if( !contiene( atributo ) )
            listaAtributos.add(atributo);
    }
    
    public void modificarAtributo( String nombre, Atributo atributo ){
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo a = listaAtributos.get(i);
            if( a.getNombre().equals(nombre) )
                listaAtributos.set(i, atributo);
        }
    }
    
    public void eliminarAtributo( String nombre ){
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            if(atributo.getNombre().equals(nombre))
                listaAtributos.remove(i);
        }
    }
    
    public void eliminarAtributo( Atributo atributo ){
        listaAtributos.remove(atributo);
    }    
    
    public void eliminarAtributo( int index){
        listaAtributos.remove(index);
    }    
    
    public Atributo obtenerAtributo( String nombre ){
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            if( atributo.getNombre().equals(nombre) )
                return atributo;
        }
        return null;
    }     
    
    public Atributo obtenerAtributo( int pos ){
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            if(pos==i){
                return atributo;
            }
        }
        return null;
    }  
    
    /** 
     * Metodos para controlar a los listaMetodos 
     */
    public void adicionarMetodo( String nombre, String retorno, int acceso ){
        Metodo metodo=new Metodo(nombre, retorno, acceso);
        if( !contiene( metodo ) )
            listaMetodos.add(metodo);
    }    
    
    public void adicionarMetodo( Metodo metodo ){
         if( !contiene( metodo ) )
            listaMetodos.add(metodo);
    }
    
    public void modificarMetodo( String nombre, Metodo metodo ){
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo m = listaMetodos.get(i);
            if( m.getNombre().equals(nombre) )
                listaMetodos.set(i, metodo);
        }
    }
    
    public void eliminarMetodo( String nombre ){
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo m = listaMetodos.get(i);
            if(m.getNombre().equals(nombre))
                listaMetodos.remove(i);
        }
    }
    
    public void eliminarMetodo( Metodo metodo ){
        listaMetodos.remove(metodo);
    }  
    
    public void eliminarMetodo( int index){
        listaMetodos.remove(index);
    }     
    
    public Metodo obtenerMetodo( String nombre ){
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo m = listaMetodos.get(i);
            if( m.getNombre().equals(nombre) )
                return m;
        }
        return null;
    }         
    
    public Metodo obtenerMetodo( int pos ){
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo metodo = listaMetodos.get(i);
            if(pos==i){
                return metodo;
            }
        }
        return null;
    }  
    
    @Override
    public String toString() {
        String s="public "+tipo+"class "+nombre+"{\n\n";
        
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            s=s+"\t"+atributo.toString()+"\n";
        }
        
        s=s+"\n";
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo metodo = listaMetodos.get(i);
            s=s+"\t"+metodo.toString()+"\n\n";
        }
        s=s+"}";
        
        return s;
    }

    
    public String generarSegmentoNombre(){
        return ( "public "+tipo+"class "+nombre );
    }
    
    public LinkedList<String> generarSegmentoAtributos(){
        LinkedList<String> lista=new LinkedList<String>();
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            lista.add( "\t"+atributo.toString()+"\n" );
        }
        return lista;
    }
    
    public LinkedList<String> generarSegmentoMetodos(){
        LinkedList<String> lista=new LinkedList<String>();
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo metodo = listaMetodos.get(i);
            lista.add( "\t"+metodo.toString()+"\n\n" );
        }
        return lista;
    }
    
    
    public boolean equals(Clase clase) {
        return ( this.nombre.equals( clase.getNombre()) );
    }    
    
    private boolean contiene( Atributo atributo ){
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo a = listaAtributos.get(i);
            if( a.equals(atributo) )
                return true;
        }
        return false;
    }
    
    private boolean contiene( Metodo metodo ){
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo m = listaMetodos.get(i);
            if( m.equals(metodo) )
                return true;
        }
        return false;
    }

    public Clase clonar() {
        Clase clase=new Clase(nombre, tipo);
        
        for (int i = 0; i < listaAtributos.size(); i++) {
            Atributo atributo = listaAtributos.get(i);
            clase.adicionarAtributo( atributo.clonar() );
        }
        for (int i = 0; i < listaMetodos.size(); i++) {
            Metodo metodo = listaMetodos.get(i);
            clase.adicionarMetodo( metodo.clonar() );
        }
        return clase;
    }
        
    
}
