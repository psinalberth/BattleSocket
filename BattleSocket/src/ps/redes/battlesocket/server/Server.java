/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Inalberth
 */
public class Server extends Thread {
    
    private Socket socket;
    
    private static final int PORT = 9998;
    
    public Server(Socket socket) {
        
        this.socket = socket;
    }
    
    @Override
    public void run() {
          
        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintStream write = new PrintStream(this.socket.getOutputStream());
            
            String str = reader.readLine();
            
            System.out.println("Conectando-se a " + str);
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int getPort() {
        
        return PORT;
    }
    
    public static void main(String [] args) {
        
        try {
            
            ServerSocket server = new ServerSocket(PORT);
            
            System.out.println("Servidor online, porta " + PORT);
            
            while (true) {
                
                Socket socket = server.accept();
                
                Thread th = new Server(socket);
                th.start();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
