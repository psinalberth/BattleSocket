/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.model;

import ps.redes.battlesocket.client.Client;

/**
 *
 * @author Inalberth
 */
public class Partida extends Thread {
    
    private Client player1, player2, jogando;
    
    public Partida(Client player1, Client player2) {
        
        this.player1 = player1;
        this.player2 = player2;
    }
    
    public Client getPlayerUm() {
        
        return player1;
    }
    
    public Client getPlayerDois() {
        
        return player2;
    }
    
    public void setPlayerUm(Client player1) {
        
        this.player1 = player1;
    }
    
    public void setPlayerDois(Client player2) {
        
        this.player2 = player2;
    }
    
    public synchronized boolean jogada(Client player, int coordenadas[]) {
        
        return false;
    }
}

