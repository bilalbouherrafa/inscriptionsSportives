package Affichage;
import java.awt.BorderLayout;
import java.awt.Color; 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Persistance.Passerelle;
 
public class FenetreMere extends JFrame {
  public FenetreMere(){             
    this.setTitle("Ma première fenêtre Java");
    this.setSize(600, 600);
    this.setLocationRelativeTo(null);     
    
    JPanel background = new JPanel();
    
	Passerelle p = new Passerelle();
	p.open();
    
    Panel pan = new Panel();
    background.add(pan, BorderLayout.CENTER);

    this.setContentPane(background); 
    this.setVisible(true);
  }       

  public static void main(String[] args){
	  FenetreMere fenetre = new FenetreMere();
  }
}