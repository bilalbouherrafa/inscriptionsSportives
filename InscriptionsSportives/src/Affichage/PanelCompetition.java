package Affichage;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Persistance.Passerelle;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;

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
	private JComboBox list;
	private JComboBox list2;
	private JButton selectcompet;
	private static Competition compCourante;
	private JButton editcomp;
	private JButton supprcomp;
	private JButton listerequips;
	private JButton ajouteracomp;
	private JTextField textediter;
	private JButton editercompfinie;
	private JButton selectcandidat;
	private int x;
	private int xx;
	private JFrame frame;
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
	
	public void selectionnerCompet() {
		setNumPage(4);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    list = new JComboBox();
	    selectcompet = new JButton("Valider");
		ArrayList<Competition> comp = new ArrayList<Competition>();
		comp = (ArrayList) Passerelle.getData("Competition");
		System.out.println(comp);
		for (Competition c : comp) {
			list.addItem(c);
		}
		
		test.add(new JLabel("Selectionner une Compétition"));
		test.add(list);
		test.add(selectcompet);
		selectcompet.addActionListener(this);
		this.add(test);
		this.revalidate();
	}
	
	public void listerCompet() {
		setNumPage(4);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    ArrayList<Competition> comp = new ArrayList<Competition>();
	    comp = (ArrayList) Passerelle.getData("Competition");
		for(Competition c : comp) {
			JLabel area = new JLabel();
			area.setText(area.getText() + "\n" + c.getNom());
			gbc.gridy = x;
			test.add(area, gbc);
			x += 1;
		}
		if(x == 0) {
			JLabel area = new JLabel("Aucune competition n'est créee");
			test.add(area);
			System.out.println("NULL");
		}
		
		this.add(test);
		this.revalidate();
	}
	
	public void menucompetitionbis() {
		setNumPage(4);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    if(list != null) {
			compCourante = (Competition) list.getSelectedItem();
	    }
	    JLabel titr = new JLabel(compCourante.getNom());
		editcomp = new JButton("Editer la compétition");
		supprcomp = new JButton("Supprimer la compétition");
		listerequips = new JButton("Lister les candidats de la compétition");
		ajouteracomp = new JButton("Ajouter un candidat");
		editcomp.addActionListener(this);
		supprcomp.addActionListener(this);
		listerequips.addActionListener(this);
		ajouteracomp.addActionListener(this);
		
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(titr, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(editcomp, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 2;
		test.add(supprcomp, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 3;
		test.add(listerequips, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 4;
		test.add(ajouteracomp, gbc);
		
		this.add(test);
		this.revalidate();
		
	}
	
	public  void editerComp() {
		setNumPage(6);
		this.removeAll();
		
	    if(list != null) {
			compCourante = (Competition) list.getSelectedItem();
	    }
	    
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    JLabel label = new JLabel("Nouveau nom de l'équipe");
	    textediter = new JTextField(15);
		JLabel labeldate = new JLabel("Nouvelle date de cloture de la compétition :(YYYY - MM - DD)");
		datecompet = new JTextField(15);
		
	    editercompfinie = new JButton("Editer");
	    editercompfinie.addActionListener(this);
	    
		gbc.gridx = 0;
	    gbc.gridy = 0;
		test.add(label, gbc);	
		
		gbc.gridx = 1;
	    gbc.gridy = 0;
		test.add(textediter, gbc);
		
		gbc.gridx = 0;
	    gbc.gridy = 1;
		test.add(labeldate, gbc);	
		
		gbc.gridx = 1;
	    gbc.gridy = 1;
		test.add(datecompet, gbc);
		
		gbc.gridx = 2;
	    gbc.gridy = 2;
		test.add(editercompfinie, gbc);
		
		this.add(test);
		this.revalidate();
	}
	
	public void listerEquipes() {
		setNumPage(6);
		this.removeAll();
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    
	    if(list != null) {
			compCourante = (Competition) list.getSelectedItem();
	    }
	    
		for(Candidat c : compCourante.getCandidats()) {
			JLabel area = new JLabel();
			area.setText(c.getNom());
			gbc.gridy = xx;
			test.add(area, gbc);
			xx += 1;
		}
		if(xx == 0) {
			JLabel area = new JLabel("Aucune équipe n'est insrite dans cette compétition");
			test.add(area);
			System.out.println("NULL");
		}
		
		this.add(test);
		this.revalidate();
	}
	
	public void ajouterAComp() {
		setNumPage(6);
		this.removeAll();
		
		JPanel test = new JPanel();
		test.setLayout(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    list2 = new JComboBox();
	    selectcandidat = new JButton("Valider");
		ArrayList<Candidat> candid = new ArrayList<Candidat>();
		candid = (ArrayList) Passerelle.getData("Candidat");
		System.out.println(candid);
		if(compCourante.estEnEquipe()) {
			for(Candidat c : candid) {
				if(c instanceof Equipe)
					list2.addItem(c);
			}
		}
		else {
			for(Candidat c : candid) {
				if(c instanceof Personne)
					list2.addItem(c);
			}
		}
		test.add(new JLabel("Selectionner le Candidat a ajouté"));
		test.add(list2);
		test.add(selectcandidat);
		selectcandidat.addActionListener(this);
		this.add(test);
		this.revalidate();
	}
	
	public void supprComp() {
		int s = JOptionPane.showConfirmDialog(this,"Etes-vous sûr de vouloir supprimer : " + compCourante.getNom() + " ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
        if(s == 0) {
            compCourante.delete();
            JOptionPane.showMessageDialog(this,
                    "La compétition a bien été supprimée !");
        }
        this.removeAll();
        PanelCompetition test = new PanelCompetition();
        this.add(test);
		this.revalidate();
		
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
		if(e.getSource() == selectionner) {
			this.selectionnerCompet();
		}
		if(e.getSource() == selectcompet) {
			this.menucompetitionbis();
		}
		if(e.getSource() == editcomp) {
			this.editerComp();
		}
		if(e.getSource() == editercompfinie) {
			//compCourante = (Competition) list.getSelectedItem();
			
			String dateCloture = datecompet.getText();
			try {
				Date localDate = formatter.parse(dateCloture);
				compCourante.setDateCloture(localDate);
				compCourante.setNom(textediter.getText());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Passerelle.save(compCourante);
			//System.out.println(compCourante.getNom());
		}
		if(e.getSource() == listerequips) {
			this.listerEquipes();
		}
		if(e.getSource() == ajouteracomp) {
			this.ajouterAComp();
		}
		if(e.getSource() == selectcandidat) {
			if(list2.getSelectedItem() instanceof Personne) {
				Personne laPersonne = (Personne) list2.getSelectedItem();
				compCourante.add(laPersonne);
			}
			else if(list2.getSelectedItem() instanceof Equipe) {
				Equipe lEquipe = (Equipe) list2.getSelectedItem();
				compCourante.add(lEquipe);
			}
		}
		if(e.getSource() == supprcomp) {
			this.supprComp();
		}
		
		
	}
	public Competition getCompetitionCourante(){
		return compCourante;
	}
}