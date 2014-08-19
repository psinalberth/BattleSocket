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
import ps.redes.battlesocket.server.BattleSocketServer;

/**
 *
 * @author Inalberth
 */
public class BattleSocketClient implements Runnable {
    
    private static PrintStream writer;
    private static BufferedReader console;
    private BufferedReader reader;
    
    private Socket socket;
    
    public BattleSocketClient(Socket socket) {
        
        this.socket = socket;
    }
    
    public static void sendRequest(String message) {
        
        writer.println(message);
    }
    
    public String getRequest() throws IOException {
        
        String string = reader.readLine();
        
        return string;
    }
    
    public static void main(String[] args) {
        
        try {
            
            Socket client = new Socket("localhost", BattleSocketServer.PORT);
            
            writer = new PrintStream(client.getOutputStream());
            console = new BufferedReader(new InputStreamReader(System.in));
            
            String str = console.readLine();
            
            sendRequest(str);

            Runnable run = new BattleSocketClient(client);
            Thread thread = new Thread(run);
            thread.start();
            
            while (true) {
                
                str = console.readLine();
                
                sendRequest(str);
            }
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        
        try {
            
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            
            while (true) {
                
                try {
                    
                    String message = reader.readLine();
                    
                    System.out.println(message);
                    
                } catch (IOException ex) {
                    
                    System.err.println(ex.getMessage());
                }
            }
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        }
    }
}