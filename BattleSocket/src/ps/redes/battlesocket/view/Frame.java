/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import ps.redes.battlesocket.client.Client;
import ps.redes.battlesocket.server.Server;

/**
 *
 * @author Inalberth
 */
public class Frame extends JFrame {
    
    private Tabuleiro tabuleiroP1, tabuleiroP2;
    
    public Frame() {
        
        setLayout(new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        initComponents();
    }
    
    private void initComponents() {
        
        tabuleiroP1 = new Tabuleiro(11, 11, Color.BLUE, "Você");
        tabuleiroP2 = new Tabuleiro(11, 11, Color.RED, "Adversário");
        add(tabuleiroP1);
        add(tabuleiroP2);
        
    }
    
    public static void main(String [] args) {
        
        try {
            
            Socket socket = new Socket("192.168.6.102", Server.PORT);
            
            PrintStream writer = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            String str = reader.readLine();
            
            writer.println(str);
            
            Thread th = new Client(socket);
            th.start();
            
            new Frame().setVisible(true);
            
            while (true) {
                
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
