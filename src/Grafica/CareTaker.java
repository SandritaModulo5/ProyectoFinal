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
public class CareTaker {

    private LinkedList<Memento> lista;
    private int index;
    
    public CareTaker() {
        this.lista=new LinkedList<Memento>();
        this.index=0;
    }
    
    public void adicionar( Memento memento ){
        if( lista.size()<5 ){
            this.lista.add(memento);
            index=lista.size()-1;
        }else{
            this.lista.remove( 0 );
            this.lista.add( memento );
            index=lista.size()-1;
        }        
    }
    
    public Memento rehacer(){
        Memento memento=null;
        if( index<lista.size()-1 ){
            this.index++;
            memento=this.lista.get( index );            
        }
        return memento;
    }
    
    public Memento deshacer(){
        Memento memento=null;
        if( index>=0 && index<lista.size() ){            
            memento=this.lista.get( index ); 
            if( index>0 )
                this.index--;           
        }
        return memento;
    }    
            
}
