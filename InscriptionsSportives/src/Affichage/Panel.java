package Affichage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import inscriptions.Equipe;
import inscriptions.Inscriptions;

import javax.swing.JButton;
import javax.swing.JPanel;



public class Panel extends JPanel implements ActionListener {
	
	private JButton equipe;
	private JButton competition;
	private JButton personne;
	private JButton exit;
	private JLabel label;
	private JLabel titre;
	private PanelAffichage page;
	private JButton retour;
	//private Inscriptions inscriptions;
	

	public Panel() {
		
		Inscriptions.reinitialiser();
		page = new PanelAffichage(0);
		BorderLayout bd = new BorderLayout();
		this.setLayout(bd);
		page.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    titre = new JLabel("Menu Inscription Sportives");
	    
	    equipe = new JButton("Equipe");
	    equipe.addActionListener(this);
	    personne = new JButton("Personne");
	    personne.addActionListener(this);
	    competition = new JButton("Compétition");
	    competition.addActionListener(this);
	    exit = new JButton("Exit");
	    exit.addActionListener(this);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(titre, gbc);
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(equipe, gbc);	
		gbc.gridx = 0;
	    gbc.gridy = 4;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(personne, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(competition, gbc);
//		gbc.gridx = 0;
//	    gbc.gridy = 8;
//	    gbc.gridheight = 2;
//	    gbc.gridwidth = 2;
//	    page.add(exit, gbc);
		
		
		label = new JLabel();
		page.add(label);
		this.add(page, BorderLayout.CENTER);	
		retour = new JButton("Retour");
		retour.addActionListener(this);
		this.add(retour,BorderLayout.SOUTH);
		retour.setVisible(false);
	}
	
	public void menu() {
		this.remove(page);
		retour.setVisible(false);
		page = new PanelAffichage(0);
		BorderLayout bd = new BorderLayout();
		this.setLayout(bd);
		page.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    titre = new JLabel("Menu Inscription Sportives");
	    
	    equipe = new JButton("Equipe");
	    equipe.addActionListener(this);
	    personne = new JButton("Personne");
	    personne.addActionListener(this);
	    competition = new JButton("Compétition");
	    competition.addActionListener(this);
//	    exit = new JButton("Exit");
//	    exit.addActionListener(this);
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(titre, gbc);
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(equipe, gbc);	
		gbc.gridx = 0;
	    gbc.gridy = 4;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(personne, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    page.add(competition, gbc);
//		gbc.gridx = 0;
//	    gbc.gridy = 8;
//	    gbc.gridheight = 2;
//	    gbc.gridwidth = 2;
//	    page.add(exit, gbc);
		

//		label = new JLabel();
//		page.add(label);
		this.add(page, BorderLayout.CENTER);	
//		retour = new JButton("Retour");
//		retour.addActionListener(this);
		this.add(retour,BorderLayout.SOUTH);
//		retour.setVisible(false);
		this.revalidate();
		}
	
	public void afficheMenuEquipe() {
		this.remove(page);
		retour.setVisible(true);
		page = new PanelEquipe();
		this.add(page, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void afficheMenuPersonne() {
		this.remove(page);
		retour.setVisible(true);
		page = new PanelPersonne();
		this.add(page, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void afficheMenuCompetition() {
		this.remove(page);
		retour.setVisible(true);
		page = new PanelCompetition();
		this.add(page, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void affichemenubis() {
		this.remove(page);
		retour.setVisible(true);
		page = new PanelEquipe();
		page.menuequipebis();
		this.add(page, BorderLayout.CENTER);
		this.revalidate();
	}
	
	public void afficheRetour() {
		switch (page.getNumPage())
		{
		case 1: this.menu();
		break;
		
		case 2: this.afficheMenuEquipe();
		break;
		
		case 3: this.afficheMenuPersonne();
		break;
		
		case 4: this.afficheMenuCompetition();
		break;
		
		case 5: this.affichemenubis();
		break;
		}
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == equipe) {
			this.afficheMenuEquipe();
			System.out.println("ekip");
		}
		if(arg0.getSource() == personne) {
			this.afficheMenuPersonne();
			System.out.println("pers");
		}
		if(arg0.getSource() == competition) {
			this.afficheMenuCompetition();
			System.out.println("compet");
		}
		if(arg0.getSource() == retour) {
			System.out.println("ok");
			this.afficheRetour();
		}
		    
		
	}

}