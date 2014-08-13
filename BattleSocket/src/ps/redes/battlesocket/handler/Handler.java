/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.handler;

import java.net.SocketException;
import java.net.UnknownHostException;
import ps.redes.battlesocket.model.Jogada;
import ps.redes.battlesocket.socket.BattleSocket;

/**
 *
 * @author Inalberth
 */
public class Handler {
    
    private BattleSocket socket;
    private Jogada jogadaP1, jogadaP2;
    
    private int acertoPlayer1, acertoPlayer2;
    
    private static final int ACERTOS = 17;
    
    public Handler(Jogada jogadaP1) {
        
        this.jogadaP1 = jogadaP1;
    }
    
    public void conectar() throws UnknownHostException, SocketException {
        
        socket = new BattleSocket();
    }
    
    public void posicionarBarcos() {
        
    }
    
    public void catchJogada(Jogada j) {
        
    }
    
    public void novoJogo() {
        
        boolean isJogando = false;
        
        while (true) {
            
            if (acertoPlayer1 == ACERTOS || acertoPlayer2 == ACERTOS) {
                
            } else {
                
                if (isJogando == true) {
                    
                    socket.write(jogadaP1.getLinha(), jogadaP1.getColuna());
                    
                } else {
                    
                    //socket.write(jogadaP2.getLinha(), jogadaP2.getColuna());
                    socket.read();
                }
            }
        }
    }
}