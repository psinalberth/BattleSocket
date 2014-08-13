/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ps.redes.battlesocket.server.Server;

/**
 *
 * @author Inalberth
 */
public class Client extends Thread {
    
    private Socket socket;
    
    public Client(Socket socket) {
        
        this.socket = socket;
    }
    
    @Override
    public void run() {
        
    }
    
    public static void main(String [] args) {
        
        try {
            
            Socket socket = new Socket("192.168.6.101", Server.getPort());
            
            PrintStream writer = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            String str = reader.readLine();
            
            writer.println(str);
            
            Thread th = new Client(socket);
            th.start();
            
            while (true) {
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}