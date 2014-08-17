/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import ps.redes.battlesocket.server.Server;

/**
 * Classe cliente que da aplicação BattleSocket, utilizada para conectar 
 * e enviar requisições ao Servidor da aplicação
 * 
 * @author Inalberth
 */
public class Client implements Runnable {
    
    private Socket socket;
    
    public Client(Socket socket) {
        
        this.socket = socket;
    }
    
    /**
     * Sobrecaraga de Runnable
     */
    public void run() {
        
        try {
            
            PrintStream writer = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            String str = reader.readLine();
            
            writer.println(str);
            
            while (true) {
                
                str = reader.readLine();
                writer.println("MESSAGE > " + str);
            }
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * JFrame utilizado para interagir com usuário do jogo
     * 
     * @author Inalberth
     * 
     */
    class BattleSocket extends JFrame {
        
        /**
         * Construtor da classe
         */
        public BattleSocket() {
            
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            initComponents();
        }
        
        /**
         * Método inicializador de componentes: 
         */
        private void initComponents() {
            
        }
    }
    
    class Tabuleiro extends JPanel {
        
        private int linhas;
        private int colunas;
    
        private static final int BUTTON_SIZE = 50;
        private final Dimension DIMENSION = new Dimension(BUTTON_SIZE, BUTTON_SIZE);
    
        private JButton[][] buttonGrid;
        private Color corJogador;
        private String titulo;
        
        public Tabuleiro() {
            
        }
        
        /**
        * Construtor da classe
        * 
        * @param linhas Quantidade de linhas do tabuleiro
        * @param colunas Quantidade de colunas do tabuleiro
        * @param corJogador Cor que identificará o jogador no tabuleiro
        * @param titulo Título identificador do tabuleiro
        */
       public Tabuleiro(int linhas, int colunas, Color corJogador, String titulo) {

           this.linhas = linhas;
           this.colunas = colunas;
           this.corJogador = corJogador;
           this.titulo = titulo;

           setLayout(new GridLayout(linhas, colunas));
           setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), titulo.toUpperCase()));

           initComponents();
       }
       
       private void initComponents() {
           
       }
    }
    
    /**
     * Método principal da classe 'Client': utilizado para criar uma nova instância cliente
     * @param args 
     */
    public static void main(String [] args) {
        
        try {
            
            Socket socket = new Socket("192.168.6.102", Server.PORT);
            
            Runnable runner = new Client(socket);
            Thread th = new Thread(runner);
            th.start();            
            
        } catch (IOException ex) {
            
            System.err.println(ex.getMessage());
        } 
    }
}