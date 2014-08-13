/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.model;

import java.awt.Point;

/**
 *
 * @author Inalberth
 */
public class Jogada {
    
    private Point coordenada;
    
    public Jogada() {
        
    }
    
    public void setCoordenada(Point coordenada) {
        
        this.coordenada = coordenada;
    }
    
    public Point getCoordenada() {
        
        return coordenada;
    }
    
    public int getLinha() {
        
        return (int)getCoordenada().getLocation().getX();
    }
    
    public int getColuna() {
        
        return (int)getCoordenada().getLocation().getY();
    }
}