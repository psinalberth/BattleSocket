/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inalberth
 */
public class BattleSocket {
    
    private static final int PORTA = 9199;
    private static final String IP = "192.168.6.106";
    
    private DatagramSocket socketP1, server;
    private InetAddress socketP2;
    private byte[] mensagem;
    
    public BattleSocket() throws SocketException, UnknownHostException {
        
        init();
    }
    
    private void init() throws SocketException, UnknownHostException {
        
        String str = "Hello, ";
        
        socketP1 = new DatagramSocket();
        socketP2 = InetAddress.getByName(IP);
        
        server = new DatagramSocket(PORTA);
        server.setReceiveBufferSize(32);
        
        DatagramPacket pacote = new DatagramPacket(str.getBytes(), str.getBytes().length, socketP2, PORTA);
        
        try {
            
            socketP1.send(pacote);
            
        } catch (IOException ex) {
            Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mensagem = new byte[str.length()];
        
        DatagramPacket pa = new DatagramPacket(mensagem, mensagem.length);
        
        ponn:
        while (true) {
            
            try {
                
                server.receive(pa);
                
            } catch (IOException ex) {
                Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String hash = new String(pa.getData());
            
            if (hash.equals(str)) {
                
                System.out.println("AA: " + hash);
                
                try {
                    
                    socketP1.send(pacote);
                    
                } catch (IOException ex) {
                    Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                pacote = pa = null;
                break ponn;
            }
        }
    }
    
    public void write(int linha, int coluna) {
        
        String str = String.valueOf(linha) + "  " + String.valueOf(coluna);
        
        DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length, socketP2, PORTA);
        
        try {
            
            socketP1.send(packet);
            
            read();
            
        } catch (IOException ex) {
            Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int[] read() {
        
        byte[] r = new byte[4];
        
        DatagramPacket packet = new DatagramPacket(r, r.length);
        
        while (true) {
            
            try {
                
                server.receive(packet);
                String str = new String(packet.getData(), 0, packet.getLength());
                
                System.out.println("Estou recebendo:" + str);
                
                int move[] = new int[2];
                move[0] = Integer.parseInt(str.substring(0, 1));
                move[1] = Integer.parseInt(str.substring(1, 2));
                
                System.out.println("Adversário jogou: " + move[0] + " e " + move[1]);
                
                return move;
                
            } catch (IOException ex) {
                Logger.getLogger(BattleSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
}