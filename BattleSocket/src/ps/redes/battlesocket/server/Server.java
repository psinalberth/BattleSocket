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

/**
 * Classe servidor para a aplicação 'BattleSocket', Java utilizando sockets  
 * @author Inalberth
 */
public class Server implements Runnable {
     
    public static final int PORT = 2014;
    private static int connectionCount;
    private Socket socket;
    
    /**
     * Construtor da classe Server.
     * @param socket Socket passado como parâmetro para manipulação.
     */
    public Server(Socket socket) {
        
        this.socket = socket;
    }
    
    /**
     * Retorna a porta a qual o servidor estará 'escutando' as classes cliente
     * @return Porta de conexão do servidor.
     */
    public static int getPort() {
        
        return PORT;
    }
    
     /**
     * Atualiza a quantidade de clientes conectados ao servidor.
     * @param connectionCount Número de conexões atual.
     */
    public static void setConnectionCount(int connectionCount) {
        
        Server.connectionCount = connectionCount;
    }
    
    /**
     * Retorna a quantidade de clientes conectados ao servidor.
     * @return Quantidade de conexões abertas.
     */
    public static int getConnectionCount() {
        
        return connectionCount;
    }
    
    /**
     * Sobrecarga de Runnable
     */
    public void run() {
          
        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintStream write = new PrintStream(this.socket.getOutputStream());
            
            String str = reader.readLine();
            
            System.out.println("Conectando-se a " + str);
            
            while (true) {
                
                str = reader.readLine();
                System.out.println("Mensagem > " + str);
            }
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        }
    }
      
    public static void main(String [] args) {
        
        ServerSocket server;
        int connectionCount = 0;
        
        try {
           
            server = new ServerSocket(PORT);
            
            System.out.println("Servidor online, porta " + PORT);
            
            while (true) {
                
                Socket home = server.accept();
                
//                connectionCount += 1;
//                
//                Server.setConnectionCount(connectionCount);
//                
//                System.out.println("Conexões: " + Server.getConnectionCount());
//                System.out.print("SERVER: ");
//                
//                if (getConnectionCount() < 2) {
//                    
//                    System.out.println("Não há jogadores suficientes para iniciar a partida");
//                    
//                } else {
//                    
//                    System.out.println("Iniciando partida...");
//                }
                Runnable runner = new Server(home);
                Thread thread = new Thread(runner);
                thread.start();
            }
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
            
        } finally {
            
        }
    }
}