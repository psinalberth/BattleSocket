/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ps.redes.battlesocket.view;

import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JFrame;

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
        
        new Frame().setVisible(true);
    }
}
