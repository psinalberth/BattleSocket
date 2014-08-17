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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    * Método principal da classe 'Client': utilizado para criar uma nova instância cliente
    * @param args 
    */
    public static void main(String [] args) {

       try {

           Socket socket = new Socket("192.168.6.102", Server.PORT);

           Runnable runner = new Client(socket);
           Thread th = new Thread(runner);
           th.start();
           
           BattleSocket b = new BattleSocket();
           b.setVisible(true);

       } catch (IOException ex) {

           System.err.println(ex.getMessage());
       } 
    }
}
    
/**
 * JFrame utilizado para interagir com usuário do jogo
 * 
 * @author Inalberth
 * 
 */
class BattleSocket extends JFrame {

    private Tabuleiro player1, player2;

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

        player1 = new Tabuleiro(Color.BLUE, "Você");
        player2 = new Tabuleiro(Color.RED, "Adversário");
        add(player1);
        add(player2);

    }

    class Tabuleiro extends JPanel {

        private static final int TAMANHO = 11;

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
        * @param corJogador Cor que identificará o jogador no tabuleiro
        * @param titulo Título identificador do tabuleiro
        */
       public Tabuleiro(Color corJogador, String titulo) {

           this.corJogador = corJogador;
           this.titulo = titulo;

           setLayout(new GridLayout(Tabuleiro.TAMANHO, Tabuleiro.TAMANHO));
           setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), titulo.toUpperCase()));

           initComponents();
       }

       /**
       * Método inicializador de componentes: O tabuleiro é inicializado com a quantidade de linhas de colunas
       * passado como referência na criação de uma nova instância da classe Tabuleiro.
       */
       private void initComponents() {

           int cont = 64;
           int contNum = 0;

           buttonGrid = new JButton[Tabuleiro.TAMANHO][Tabuleiro.TAMANHO];

           for (int i = 0; i < Tabuleiro.TAMANHO; i++) {
               for (int j = 0; j < Tabuleiro.TAMANHO; j++) {

                   buttonGrid[i][j] = new JButton();
                   buttonGrid[i][j].setMinimumSize(DIMENSION);
                   buttonGrid[i][j].setPreferredSize(DIMENSION);
                   buttonGrid[i][j].setMaximumSize(DIMENSION);
                   buttonGrid[i][j].addActionListener(listener);

                   if (i == 0 && j != i) {

                       cont += 1;
                       buttonGrid[i][j].setText(String.valueOf((char)cont));
                       buttonGrid[i][j].setEnabled(false);
                   }

                   if (j == 0 && i != j) {

                       contNum += 1;
                       buttonGrid[i][j].setText(String.valueOf(contNum));
                       buttonGrid[i][j].setEnabled(false);
                   }

                   add(buttonGrid[i][j]);

                   buttonGrid[0][0].setVisible(false);
               }
           }
       }

        ActionListener listener = new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ((JButton)(e.getSource())).setBackground(corJogador);
            }
        };
    }
}