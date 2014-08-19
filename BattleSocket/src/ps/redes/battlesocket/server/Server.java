/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.server;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import ps.redes.battlesocket.client.Client;

/**
 * Classe servidor para a aplicação 'BattleSocket', Java utilizando sockets  
 * @author Inalberth
 */
public class Server implements Runnable {
     
    public static final int PORT = 2014;
    private static int connectionCount;
    private Socket socket;
    private int r[];
    
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
     * Recebe uma String do cliente na forma x-y e retorna as coordenadas em que o cliente jogou no tabuleiro
     * 
     * @param reader Mensagem enviada pelo cliente
     * @return Coordenadas X e Y do tabuleiro
     */
    public int[] converteEmCoordenadas(String reader) {
        
        String[] temp = reader.split("-");
        
        int coordenadas[] = new int[2];
        
        coordenadas[0] = Integer.parseInt(temp[0]);
        coordenadas[1] = Integer.parseInt(temp[1]);
        
        return coordenadas;
    }
    
    public String converteEmString(Point coordenadas) {
        
        String string = String.valueOf(coordenadas.x + "-" + coordenadas.y);
        
        return string;
    }
    
    /**
     * Sobrecarga de Runnable
     */
    public void run() {
        
        String str = null;
        
        try {
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            PrintStream write = new PrintStream(this.socket.getOutputStream());
            
            while (true) {
                
                str = reader.readLine();
                
                if (str != null) {
                
                r = converteEmCoordenadas(str);
                write.println(str);
                System.out.println("Mensagem > " + String.valueOf(r[0]) + " e " + String.valueOf(r[1]));
                
                }
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
                
                Client player1, player2;
                Runnable runner1, runner2;
                
                Partida partida = new Partida();
                
                player1 = new Client(home);
                player2 = new Client(home);
                
                runner1 = player1;
                runner2 = player2;
                
                Thread p1 = new Thread(runner1);
                Thread p2 = new Thread(runner2);
                
                partida.setPlayerUm(player1);
                partida.setPlayerDois(player2);
                partida.setAtual(player1);
                
                p1.start();
                p2.start();

                
                
                
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

class Partida implements Runnable {
    
    private Client player1, player2, jogando;
    private static final int ACERTOS = 17;
    
    public Partida() {
        
    }

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
    
    public void setAtual(Client jogando) {
        
        this.jogando = jogando;
    }
    
    public void enviarJogada(Client player) throws IOException {
        
        player.receberMovimento();
    }

    /**
     * Sobrecarga de Runnable
     */
    public void run() {
        
        if (player1.getAcertos() == ACERTOS ) {
            
        } else if (player1.getAcertos() == ACERTOS) {
            
        } else {
            
            if (jogando == player1) {
                
                try {
                    enviarJogada(player2);
                } catch (IOException ex) {
                    Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                
                try {
                    enviarJogada(player1);
                } catch (IOException ex) {
                    Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
}