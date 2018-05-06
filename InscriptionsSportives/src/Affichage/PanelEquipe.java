package Affichage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Persistance.Passerelle;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class PanelEquipe extends PanelAffichage implements ActionListener{
	private JButton creer;
	private JButton lister;
	private JButton selectionner;
	private JLabel titre;
	private JButton valider;
	private JLabel label;
	private int x = 0;
	private JComboBox list;
	private JComboBox list2;
	private JButton selectequipe;
	private JButton selectjoueur;
	private JTextField text;
	private JButton editeq;
	private JButton suppreq;
	private JButton listereq;
	private JButton ajoutereq;
	private JButton editerequipe;
	private JTextField textediter;
	private JButton supprimerequipe;
	private static Equipe equipeCourante;
	private static Personne personneCourante;
	
	public PanelEquipe() {
		super(1);
		this.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    titre = new JLabel("Menu Equipe");
	    
	    creer = new JButton("Créer une Equipe");
	    creer.addActionListener(this);
	    lister = new JButton("Lister les Equipes");
	    lister.addActionListener(this);
	    selectionner = new JButton("Selectionner une Equipe");
	    selectionner.addActionListener(this);

	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
	    this.add(titre, gbc);
	    gbc.gridx = 0;
	    gbc.gridy = 2;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		this.add(creer, gbc);	
		gbc.gridx = 0;
	    gbc.gridy = 4;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		this.add(lister, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 6;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		this.add(selectionner, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 8;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;

	}

	public void creerEquipe() {
		setNumPage(2);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
		JLabel label = new JLabel("Entrer le nom de l'équipe : ");
		text = new JTextField(15);
		valider = new JButton("Valider");
		valider.addActionListener(this);
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		test.add(label, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 5;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		test.add(text, gbc);
		gbc.gridx = 4;
	    gbc.gridy = 5;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		test.add(valider, gbc);
		
		this.add(test);
		this.revalidate();
		
	}
	
	public void listerEquipe() {
		setNumPage(2);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
		for(Equipe e : Inscriptions.getInscriptions().getEquipes()) {
			JLabel area = new JLabel();
			area.setText(area.getText() + "\n" + e.getNom());
			gbc.gridy = x;
			test.add(area, gbc);
			x += 1;
		}
		if(x == 0) {
			JLabel area = new JLabel("Aucune équipe n'est inscrite");
			test.add(area);
			System.out.println("NULL");
		}
		
		this.add(test);
		this.revalidate();
	}
	public void selectionnerEquipe() {
		setNumPage(2);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    list = new JComboBox();
	    selectequipe = new JButton("Valider");
		ArrayList<Equipe> teams = new ArrayList<Equipe>();
		teams = (ArrayList) Passerelle.getData("Equipe");
		System.out.println(teams);
		for(Equipe e : teams) {
			list.addItem(e);
		}
		test.add(new JLabel("Selectionner une Equipe"));
		test.add(list);
		test.add(selectequipe);
		selectequipe.addActionListener(this);
		this.add(test);
		this.revalidate();
	}
	@Override
	public void menuequipebis() {
		setNumPage(2);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    if(list != null) {
			equipeCourante = (Equipe) list.getSelectedItem();
	    }
	    JLabel titr = new JLabel(equipeCourante.getNom());
		editeq = new JButton("Editer une équipe");
		suppreq = new JButton("Supprimer une équipe");
		listereq = new JButton("Lister les membres");
		ajoutereq = new JButton("Ajouter un sportif");
		editeq.addActionListener(this);
		suppreq.addActionListener(this);
		listereq.addActionListener(this);
		ajoutereq.addActionListener(this);
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(titr, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(editeq, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 2;
		test.add(suppreq, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 3;
		test.add(listereq, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 4;
		test.add(ajoutereq, gbc);
		
		this.add(test);
		this.revalidate();
		
	}
	
	public void editerEquipe() {
		setNumPage(5);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    JLabel label = new JLabel("Nouveau nom de l'équipe");
	    textediter = new JTextField(15);
	    editerequipe = new JButton("Editer");
	    editerequipe.addActionListener(this);
	    
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(label, gbc);	
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(textediter, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 0;
		test.add(editerequipe, gbc);
		
		this.add(test);
		this.revalidate();
	}
	public void supprimerEquipe() {
		setNumPage(2);
		this.removeAll();
		
		//equipeCourante = (Equipe) list.getSelectedItem();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    JLabel label = new JLabel("Voulez vous supprimer l'équipe " + equipeCourante.getNom());
	    supprimerequipe = new JButton("Supprimer");
	    supprimerequipe.addActionListener(this);
	    
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(label, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 1;
		test.add(supprimerequipe, gbc);
		
		this.add(test);
		this.revalidate();
	}
	
	public void ajouterEquipe() {
		setNumPage(5);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    list2 = new JComboBox();
	    selectjoueur = new JButton("Valider");
		ArrayList<Personne> boug = new ArrayList<Personne>();
		boug = (ArrayList) Passerelle.getData("Personne");
		System.out.println(boug);
		for(Personne p : boug) {
			list2.addItem(p);
		}
		test.add(new JLabel("Selectionner le Joueur a ajouté"));
		test.add(list2);
		test.add(selectjoueur);
		selectjoueur.addActionListener(this);
		this.add(test);
		this.revalidate();
	}
	
	public void listerMembres() {
		setNumPage(5);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    if(list != null) {
	    equipeCourante = (Equipe) list.getSelectedItem();
	    }
		for(Personne p : equipeCourante.getMembres()) {
			JLabel area = new JLabel();
			area.setText(p.getPrenom() + " " + p.getNom());
			gbc.gridy = x;
			test.add(area, gbc);
			x += 1;
		}
		if(x == 0) {
			JLabel area = new JLabel("Aucune joueurs n'est inscrit dans l'équipe");
			test.add(area);
			System.out.println("NULL");
		}
		
		this.add(test);
		this.revalidate();
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == creer){
			this.creerEquipe();
			System.out.println("creer");
		}
		if(e.getSource() == lister) {
			this.listerEquipe();
			System.out.println("lister");
		}
		if(e.getSource() == selectionner) {
			this.selectionnerEquipe();
			System.out.println("select");
		}
		if(e.getSource() == valider) {
			String nomEquipe = text.getText();
			Equipe team = Inscriptions.getInscriptions().createEquipe (nomEquipe);
			System.out.println("L'équipe, " + nomEquipe + " a était créée avec succés");
			text.setText("");
		}
		if(e.getSource() == selectequipe) {
			this.menuequipebis();
		}
		if(e.getSource() == editeq) {
			this.editerEquipe();
		}
		if(e.getSource() == editerequipe) {
			equipeCourante = (Equipe) list.getSelectedItem();
			equipeCourante.setNom(textediter.getText());
			System.out.println(equipeCourante.getNom());
			}
		
		if(e.getSource() == suppreq) {
			equipeCourante = (Equipe) list.getSelectedItem();
			this.supprimerEquipe();
		}
		if(e.getSource() == supprimerequipe) {
			equipeCourante = (Equipe) list.getSelectedItem();  
			System.out.println(equipeCourante.getNom());
			equipeCourante.delete();
		}
		if(e.getSource() == ajoutereq) {
			this.ajouterEquipe();
		}
		if(e.getSource() == selectjoueur) {
			equipeCourante = (Equipe) list.getSelectedItem();
			personneCourante = (Personne) list2.getSelectedItem();
			equipeCourante.add(personneCourante);
		}
		if(e.getSource() == listereq) {
			
			this.listerMembres();
		}

	}
	
	public Equipe getEquipeCourante(){
		return equipeCourante;
	}
}


