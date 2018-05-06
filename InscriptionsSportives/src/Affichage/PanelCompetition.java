package Affichage;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import inscriptions.Inscriptions;
import inscriptions.Competition;

public class PanelCompetition extends PanelAffichage implements ActionListener{
	private JButton creer;
	private JButton lister;
	private JButton selectionner;
	private JLabel titre;
	private JTextField nomcompet;
	private JButton valider;
	private JTextField datecompet;
	private JButton validercreation;
	private JRadioButton equipe;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");


	
	public PanelCompetition() {
		super(1);
		this.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    titre = new JLabel("Menu Compétition");
	    
	    creer = new JButton("Créer une Compétition");
	    creer.addActionListener(this);
	    lister = new JButton("Lister les Compétitions");
	    lister.addActionListener(this);
	    selectionner = new JButton("Selectionner une Compétition");
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

	public void creerCompet() {
		setNumPage(4);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
		JLabel labelnom = new JLabel("Nom de la compétition : ");
		nomcompet = new JTextField(15);
		
		JLabel labeldate = new JLabel("Date de cloture de la compétition :(YYYY - MM - DD)");
		datecompet = new JTextField(15);
		
		validercreation = new JButton("Valider");
		validercreation.addActionListener(this);
		
		equipe = new JRadioButton("Equipe");
	    equipe.setMnemonic(KeyEvent.VK_E);


	    JRadioButton solo = new JRadioButton("Seul");
	    solo.setMnemonic(KeyEvent.VK_S);

	    ButtonGroup group = new ButtonGroup();
	    group.add(equipe);
	    group.add(solo);
	    this.add(equipe);
	    this.add(solo);

	    equipe.addActionListener(this);
	    solo.addActionListener(this);
	    
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(labelnom, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(nomcompet, gbc);
		
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(equipe, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		test.add(solo, gbc);

		gbc.gridx = 0;
	    gbc.gridy = 2;
		test.add(labeldate, gbc);
		
		gbc.gridx = 1;
	    gbc.gridy = 2;
		test.add(datecompet, gbc);

		gbc.gridx = 4;
	    gbc.gridy = 5;
	    gbc.gridheight = 2;
	    gbc.gridwidth = 2;
		test.add(validercreation, gbc);
		
		this.add(test);
		this.revalidate();
		
	}
	
	public void listerCompet() {
		for (Competition c : Inscriptions.getInscriptions().getCompetitions()) {
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == creer) {
			this.creerCompet();
		}
		if(e.getSource() == validercreation) {
			Boolean enEquipe = null;
			String nomCompet = nomcompet.getText();
			String dateCloture = datecompet.getText();
			//Date localDate = null;
			try {
				Date localDate = formatter.parse(dateCloture);
				if(equipe.isSelected() == true) 
					enEquipe = true;
				else
					enEquipe = false;
				Inscriptions.getInscriptions().createCompetition(nomCompet, localDate, enEquipe);
	            System.out.println("La compétition, " + nomCompet + " a était créée avec succés");
			} catch (ParseException e1) {
				System.out.println("Veuillez respecter le format de la date 'yyyy-mm-dd' ! " + e1);
			}
		}
		if(e.getSource() == lister) {
			this.listerCompet();
		}
		
	}
}