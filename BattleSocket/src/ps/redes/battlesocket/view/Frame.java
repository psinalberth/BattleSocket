/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import ps.redes.battlesocket.socket.BattleSocket;

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
    
    public void go() {
        
    }
    
    public static void main(String [] args) {
        
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                
                new Frame().setVisible(true);
            }
        });
    }
}
