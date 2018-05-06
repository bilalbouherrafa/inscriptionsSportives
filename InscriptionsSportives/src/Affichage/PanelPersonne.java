package Affichage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Persistance.Passerelle;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class PanelPersonne extends PanelAffichage implements ActionListener{
	private JButton creer;
	private JButton lister;
	private JButton selectionner;
	private JButton supprimer;
	private JButton editer;
	private JButton editerr;
	private JLabel titre;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JTextField maill;
	private JTextField prenom;
	private JTextField nom;
	private JTextField prenomedit;
	private JTextField nomedit;
	private JTextField mailedit;
	private JTextField mail;
	private JButton valider;
	private JButton suppr;
	private JTextField mailsupp;
	private JButton validerr;
	private int x;

	
	
	public PanelPersonne() {
		super(1);
		this.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    titre = new JLabel("Menu Sportif");
	    
	    creer = new JButton("Créer un Sportif");
	    creer.addActionListener(this);
	    lister = new JButton("Lister les Sportifs");
	    lister.addActionListener(this);
	    supprimer = new JButton("Supprimer un Sportif");
	    supprimer.addActionListener(this);
	    editer = new JButton("Editer un Sportif");
	    editer.addActionListener(this);

	    
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
		this.add(supprimer, gbc);
		gbc.gridx = 0;
	    gbc.gridy = 8;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		this.add(editer, gbc);
	}

	public void creerSportif() {
		setNumPage(3);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
		JLabel label1 = new JLabel("Prénom du sportif : ");
		prenom = new JTextField(15);
		JLabel label2 = new JLabel("Nom du sportif : ");
		nom = new JTextField(15);
		JLabel label3 = new JLabel("Mail du sportif : ");
		mail = new JTextField(15);
		valider = new JButton("Valider");
		valider.addActionListener(this);
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(label1, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(prenom, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(label2, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		test.add(nom, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 2;
		test.add(label3, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 2;
		test.add(mail, gbc);
		
		gbc.gridx = 4;
	    gbc.gridy = 5;
		test.add(valider, gbc);
		
		this.add(test);
		this.revalidate();
	}
	
	public void listerSportif() {
		setNumPage(3);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
		ArrayList<Personne> boug = new ArrayList<Personne>();
		boug = (ArrayList) Passerelle.getData("Personne");
		for(Personne p : boug) {
			JLabel area = new JLabel();
			area.setText(p.getPrenom() + " " + p.getNom() + " - " + p.getMail());
			gbc.gridy = x;
			test.add(area, gbc);
			x += 1;
		}
		if(x == 0) {
			JLabel area = new JLabel("Aucune sportif n'est inscrit");
			test.add(area);
			System.out.println("NULL");
		}
		
		this.add(test);
		this.revalidate();
	}
	
	public void supprimerSportif() {
		setNumPage(3);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    JLabel label1 = new JLabel("Mail du sportif à supprimer : ");
		mailsupp = new JTextField(15);
		
		suppr = new JButton("Supprimer");
		suppr.addActionListener(this);
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(label1, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(mailsupp, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 2;
		test.add(suppr, gbc);
		
		this.add(test);
		this.revalidate();
	}
	
	public void editerSportif() {
		setNumPage(3);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    

		JLabel label1 = new JLabel("Mail du sportif à éditer : ");
		maill = new JTextField(15);
		
		label2 = new JLabel("Prénom du sportif à éditer: ");
		prenomedit = new JTextField(15);
		label3 = new JLabel("Nom du sportif à éditer: ");
		nomedit = new JTextField(15);
		label4 = new JLabel("Nouveau mail du sportif à éditer : ");
		mailedit = new JTextField(15);
		
		validerr = new JButton("Valider");
		validerr.addActionListener(this);
		
		editerr = new JButton("Editer");
		editerr.addActionListener(this);
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(label1, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(maill, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 0;
		test.add(validerr, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(label2, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		test.add(prenomedit, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 2;
		test.add(label3, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 2;
		test.add(nomedit, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 3;
		test.add(label4, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 3;
		test.add(mailedit, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 3;
		test.add(editerr, gbc);
		label2.setVisible(false);
		prenomedit.setVisible(false);
		label3.setVisible(false);
		nomedit.setVisible(false);
		label4.setVisible(false);
		mailedit.setVisible(false);
		editerr.setVisible(false);
		this.add(test);
		this.revalidate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == creer) {
			this.creerSportif();
			
		}
		if(e.getSource() == lister) {
			this.listerSportif();
			
		}
		if(e.getSource() == supprimer) {
			this.supprimerSportif();
			
		}
		if(e.getSource() == editer) {
			this.editerSportif();
		}
		if(e.getSource() == valider) {
			String snom = nom.getText();
			String sprenom = prenom.getText();
			String smail = mail.getText();
			Personne pers = Inscriptions.getInscriptions().createPersonne(snom, sprenom, smail);
			System.out.println(pers.getNom() + " " + pers.getPrenom() + ", a était créé(e) avec succés" + " son mail est : " + pers.getMail());
		}
		
		if(e.getSource() == suppr) {
			System.out.println("On supprim");
			ArrayList<Personne> boug = new ArrayList<Personne>();
			boug = (ArrayList) Passerelle.getData("Personne");
			for(Personne p : Inscriptions.getInscriptions().getPersonnes()) {
				if(p.getMail().equals(mailsupp.getText())) {
					p.delete();
					String nom = p.getNom();
					String prenom = p.getPrenom();
					System.out.println(prenom + " " + nom + ", a bien été supprimé(e)");
					
				}
			}
		}
		if(e.getSource() == validerr) {
			for(Personne p : Inscriptions.getInscriptions().getPersonnes()) {
				if(p.getMail().equals(maill.getText())) {
					nomedit.setText(p.getNom());
					prenomedit.setText(p.getPrenom());
					label2.setVisible(true);
					prenomedit.setVisible(true);
					label3.setVisible(true);
					nomedit.setVisible(true);
					label4.setVisible(true);
					mailedit.setVisible(true);
					editerr.setVisible(true);
				}
			}
		}
		if(e.getSource() == editerr) {
			for(Personne p : Inscriptions.getInscriptions().getPersonnes()) {
				if(p.getMail().equals(maill.getText())) {
					p.setNom(nomedit.getText());
					p.setPrenom(prenomedit.getText());
					p.setMail(mailedit.getText());
				}
			}
			System.out.println("Le sportif a bien été édité.");
		}
	}
}