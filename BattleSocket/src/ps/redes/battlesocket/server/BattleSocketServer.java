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
import java.util.HashMap;
import java.util.Map;
import ps.redes.battlesocket.client.BattleSocketClient;

/**
 *
 * @author Inalberth
 */
public class BattleSocketServer implements Runnable {
    
    private PrintStream writer;
    private BufferedReader reader;
    private static Map<String, PrintStream> clients;
    
    private static int connectionCount;
    
    public static final int PORT = 2014;
    
    private Socket socket;
    
    public BattleSocketServer(Socket socket) {
        
        this.socket = socket;
    }
    
    public void sendToClient(PrintStream write, String message) {
        //out:
        for (Map.Entry<String, PrintStream> client : clients.entrySet()) {
            
            PrintStream temp = client.getValue();
            
            if (temp != write) {
                System.out.println(client.getKey() + client.getValue());
                temp.println(message);
                //break out;
            }
        }
    }
    
    public String receiveFromClient() throws IOException {
        
        String string = this.reader.readLine();
        
        return string;
    }
    
    public static int getConnectionCount() {
        
        return connectionCount;
    }
    
    public static void main(String[] args) {
        
        try {
            
            int cont = 0;
            
            ServerSocket server = new ServerSocket(2014);
            
            System.out.println("Servidor online na porta " + PORT);
            
            clients = new HashMap<>();

            while (true) {
                
                Socket home = server.accept();
                
                if (home.isConnected()) {
                    
                    cont += 1;
                    BattleSocketServer.connectionCount = cont;
                    
                    System.out.println("conectou-se");                    
                }
                
                Runnable run = new BattleSocketServer(home);
                Thread thread = new Thread(run);
                thread.start();
                
            }   
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());   
        }
    }

    @Override
    public void run() {
        
        try {
            
            this.writer = new PrintStream(this.socket.getOutputStream());
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
            String strx = this.reader.readLine();
            
            clients.put(strx, this.writer);
            
            while (true) {
                
                String str = receiveFromClient();
                
                sendToClient(this.writer, str);
            }
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        }
    }
}

class Handler {
    
    BattleSocketClient player1, player2;
    
    public Handler() {
        
        
    }
}