package EntreeSortie;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.SortedSet;

import Persistance.Passerelle;

import java.time.format.DateTimeParseException;



import commandLineMenus.Option;
import commandLineMenus.rendering.examples.util.InOut;
import inscriptions.Candidat;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import commandLineMenus.Action;
import commandLineMenus.List;
import commandLineMenus.ListAction;
import commandLineMenus.ListData;
import commandLineMenus.ListOption;
import commandLineMenus.Menu;



public class DialogueUtilisateur {

	private Inscriptions inscriptions;
	private Personne pers;
	private Competition compet;
	private Equipe team;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	

	public DialogueUtilisateur(Inscriptions inscriptions) {
		this.inscriptions = inscriptions;
	}
	
	public void autoSave() {
		try
		{
			inscriptions.sauvegarder();
		} 
		catch (IOException e)
		{
			System.out.println("Sauvegarde impossible." + e);
		}
	}
	
	public Menu MenuPrincipal() {
		
	Menu menu = new Menu("Menu !");
	
	menu.add(menuEquipe());
	menu.add(menuPersonne());
	menu.add(menuCompetition());
	menu.addQuit("4");
	return menu;
	
	}

	
	public void start() {
		
		MenuPrincipal().start();
	}
	
//	private Menu menuCompetition() {
//		Menu menuCompetition = new Menu("Comp�tition", "3");
//		
//		menuCompetition.add(creerCompet());
//		menuCompetition.add(listCompetOption());
//		menuCompetition.add(addTeamInCompetOption());
//		menuCompetition.add(addGuyInCompetOption());
//		menuCompetition.add(editNameCompetOption());
//	    menuCompetition.add(removeCompetOption());
//		menuCompetition.addBack("b");
//		return menuCompetition;
//	}
	
	private Menu menuCompetition() {
		Menu menuCompetition = new Menu("Menu Comp�tition", "c");
		
		menuCompetition.add(createCompetOption());
		menuCompetition.add(listCompetOption());
		menuCompetition.add(selectCompet());
		menuCompetition.add(autoSaveOption());
		menuCompetition.addBack("b");
		return menuCompetition;
	}
	public Option createCompetOption() {
		return new Option("Cr�er une comp�tition", "c", createCompetAction());
	}
	
	private Action createCompetAction() {
        return new Action() {
            public void optionSelected() {

            	
                String nomCompet = InOut.getString("Entrer le nom de la comp�tition : ");
                String teamOrNotTeam = InOut.getString("Entrer 'equipe' pour une comp�tition en �quipe ou solo pour comp�tition individuel ? (equipe/solo) ");
                Boolean enEquipe = teamOrNotTeam.equals("equipe");
                try {
                    String dateCloture = InOut.getString("Entrer la date de cl�ture (yyyy-mm-dd) : ");
                    Date localDate = formatter.parse(dateCloture);
                    inscriptions.createCompetition(nomCompet, localDate, enEquipe);
                    System.out.println("La comp�tition, " + nomCompet + " a �tait cr��e avec succ�s");
                } catch(ParseException e) {
                     System.out.println("Veuillez respecter le format de la date 'yyyy-mm-dd' ! " + e);
                }
            }
        };
    }
	
	public Option listCompetOption() {
		return new Option("Lister les comp�titions", "a",
				() -> {System.out.println(inscriptions.getCompetitions());}
		);
	}
	
	private List<Competition> selectCompet() {
		return new List<Competition>("S�lectionner une comp�tition", "e",
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> menuSelectCompet(element)
				);
	}
	
	public Menu menuSelectCompet(Competition competition) {
		Menu selectCompet = new Menu("Comp�tition : " + competition.getNom());
		selectCompet.add(menuAdd(competition));
		selectCompet.add(editNameCompetOption(competition));
		selectCompet.add(menuRemove(competition));
		selectCompet.add(detailCompet(competition));
		selectCompet.add(editDateEnd(competition));
		selectCompet.addBack("b");
		return selectCompet;
	}
	
	private Menu menuAdd(Competition competition) {
		Menu menuAdd = new Menu("Menu Inscription", "i");
		if(competition.estEnEquipe()) {
			menuAdd.add(addTeamInCompetOption(competition));
		}
		else {
			menuAdd.add(addGuyInCompetOption(competition));
		}
		menuAdd.addBack("b");
		return menuAdd;
	}
	
	public Option addTeamInCompetOption(Competition competition) {
		ArrayList clone = new ArrayList<>(inscriptions.getEquipes());
		for (Candidat c : competition.getCandidats()){
			if (clone.contains(c)){
				clone.remove(c);
			}
		}
		return new List <Equipe>("S�lection de l'�quipe", "e",
				() -> clone,
				(element) -> addTeamInCompet(competition, element)
			);
	}
	
	public Option addTeamInCompet(Competition competition, Equipe equipe) {
		return new Option ("Inscrire " + equipe.getNom() + " dans la comp�tition : " + competition.getNom(), "a",
				() -> {competition.add(equipe); System.out.println(equipe.getNom() + " a �tait inscrit dans la comp�tition : " + competition.getNom());}
				);
	}
	
	public Option addGuyInCompetOption(Competition competition) {
		return new List <Personne>("S�lection du sportif", "s",
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> addGuyInCompet(competition, element) 
			);
	}
	
	public Option addGuyInCompet(Competition competition, Personne personne) {
		return new Option ("Inscrire " + personne.getNom() + " " + personne.getPrenom() + " dans la comp�tition : " + competition.getNom(), "a",
				() -> {competition.add(personne); System.out.println(personne.getNom() + " " + personne.getPrenom() + " a �tait inscrit dans la comp�tition : " + competition.getNom());}
				);
	}
	
	public Option editNameCompetOption(Competition competition) {
		return new Option("Renommer une comp�tition", "r", 
				() -> {String newName = InOut.getString("Entrer le nouveau nom de la comp�tition : "); competition.setNom(newName); System.out.println("La comp�tition � bien �tait renomm�");}
				);
	}
	
	private Menu menuRemove(Competition competition) {
		Menu menuRemove = new Menu("Menu Suppression", "s");
		menuRemove.add(removeCompetOption(competition));
		menuRemove.add(removeGuyOrTeamOfCompetOption(competition));
		menuRemove.addBack("b");
		return menuRemove;
	}
	
	public Option removeCompetOption(Competition competition) {
		return new Option("Supprimer la comp�tition : " + competition.getNom(), "r",
				() -> {
					if(competition.getCandidats().isEmpty()){
						competition.delete();
						System.out.println("La competition a bien �tait supprim�e");
					}
					else {
						System.out.println("La comp�tition n'est pas vide ! Veuillez supprimer les candidats incrits dans la comp�tition !");
					}
				}
				);
	}

	public Option removeGuyOrTeamOfCompetOption(Competition competition) {
		return new List<Candidat>("Selectionner l'�quipe ou le sportif a supprimer de la comp�tition : " + competition.getNom(), "d",
				() -> new ArrayList<>(competition.getCandidats()),
				(element) -> removeGuyOrTeamOfCompetAction(competition, element)
				);
	}

	private Option removeGuyOrTeamOfCompetAction(Competition competition, Candidat candidat) {
		return new Option("Supprimer " + candidat.getNom() + " de " + competition.getNom(), "s",
				() -> {competition.remove(candidat); System.out.println(candidat.getNom() + " a bien �tait supprim�(e) de la comp�tition : " + competition.getNom());}
				);
	}
	
	public Option detailCompet(Competition competition) {
		return new Option("Information d�taill�es sur la comp�tition : " + competition.getNom(), "t",
				() -> {
					if(competition.estEnEquipe()) {
						System.out.println("\nComp�tition : " + competition.getNom() + "\n Date de cl�ture : " + competition.getDateCloture() + "\n Type : Equipe \n ");
					}
					else {
						System.out.println("\nComp�tition : " + competition.getNom() + "\n Date de cl�ture : " + competition.getDateCloture() + "\n Type : Individuel \n ");
					}
				}
				);
	}
	
	public Option editDateEnd(Competition competition) {
		return new Option("Repousser la date de cl�ture", "d",
				() -> {		
					
						try {
							String dateCloture = InOut.getString("Entrer la nouvelle date de cl�ture (dd-mm-yyyy) : ");
							Date localDate = formatter.parse(dateCloture);
							competition.setDateCloture(localDate);
						} catch(ParseException e) {
							System.out.println("Veuillez respecter le format de la date 'dd-mm-yyyy' ! " + e);
						}
					
					System.out.println("La date a bien �tait repouss�e");
				}
				);
	}
	//TEST
	
	
	private Menu menuEquipe() {
		Menu menuEquipe = new Menu("Equipe", "e");
		menuEquipe.add(createTeamOption());
		menuEquipe.add(listTeamOption());
		menuEquipe.add(selectTeam());
		menuEquipe.addBack("b");
		return menuEquipe;
	}

	
	public Option createTeamOption() {
		return new Option("Cr�er une �quipe", "1", createTeamAction());
	}
	
	
	private Action createTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nomEquipe = InOut.getString("Entrer le nom de l'�quipe : ");
				Equipe team = inscriptions.createEquipe (nomEquipe);
				System.out.println("L'�quipe, " + nomEquipe + " a �tait cr��e avec succ�s");
				autoSave();
				Passerelle.save(team);
			}
		};
	}
	
	public Option listTeamOption() {
		return new Option("Lister les �quipes", "2", listTeamAction());
	}
	
	private Action listTeamAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getEquipes());
			}
		};
	}

	

	
//	private Menu sousMenuEquipe() {
//		List<Equipe> sousMenuEquipe = new List<Equipe>("Selectionne une �quipe","3",
//			new ListData <Equipe>()
//			{
//				public java.util.List<Equipe> getList()
//				{
//					return new ArrayList<Equipe>(inscriptions.getEquipes());
//				}
//			},
//			new ListOption <Equipe>()
//			{
//
//				@Override
//				public Option getOption(Equipe item) {
//					// TODO Auto-generated method stub
//					return null;
//				}	
//				
//			});
	
	private List<Equipe> selectTeam() {
		return new List<Equipe>("Selectionner une �quipe", "e",
				() -> new ArrayList<>(inscriptions.getEquipes()),
				(element) -> menuSelectTeam(element)
		);
	}
	
	private Menu menuSelectTeam(Equipe e) {
		Menu selectTeam = new Menu(e.getNom());
		selectTeam.add(listMemberTeamOption(e));
		selectTeam.add(ajoutPersonneEquipe(e));
		selectTeam.addBack("b");
		return selectTeam;
	}
						
		
		
//		
//		//sousMenuEquipe.add(listMemberTeamOption());
//		//sousMenuEquipe.add(removeTeamOption());
//		sousMenuEquipe.addBack("b");
//		//String nameTeam = InOut.getString("Nom de l'�quipe : ");
//		//sousMenuEquipe.start();
//		return sousMenuEquipe;
//	}
//	
	public Option listMemberTeamOption(Equipe e) {
		return new Option("Afficher les joueurs de l'�quipe", "a",
				() -> {System.out.println(e.getMembres());}
		);
	}
	
	private List<Personne> ajoutPersonneEquipe(Equipe e) {
		return new List<Personne>("Selectionner une personne", "p",
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> addGuyInTeam(e, element)
		);
	}
	
	public Option addGuyInTeam(Equipe e, Personne p) {
		return new Option("Ajouter " + p.getNom() + " " + p.getPrenom() + " � " + e.getNom(), "a",
				() -> {e.add(p);}
				);
	}
	
	public Option removeTeamOption() {
		return new Option("Supprimer l'�quipe", "s", removeTeamAction());
	}
	
	private Action removeTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'�quipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						c.delete();
						System.out.println(c.getNom() + ", a bien �tait supprim�e");
						autoSave();
						deleteSuccess = true;
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a �chou�, car l'�quipe n'est pas r�pertori�e");
					
				}
			}
		};
	}
	
	public Option editNameTeamOption() {
		return new Option("Editer le nom d'une �quipe", "5", editNameTeamAction());
	}
	
	private Action editNameTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'�quipe : ");
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						String newName = InOut.getString("Nouveau de l'�quipe : ");
						c.setNom(newName);
						autoSave();
						System.out.println("Le nouveau nom de l'�quipe : " + nameTeam + "est : " + newName);
						break;
					}
				}
			}
		};
	}
	
	public Option removeGuyOfTeamOption() {
		return new Option("Supprimer un sportif d'une �quipe", "6", removeGuyOfTeamAction());
	}
	
	private Action removeGuyOfTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'�quipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				SortedSet<Equipe> listTeam = inscriptions.getEquipes();
				String nomPersonne = null;
				
				do {
					for(Candidat c : listTeams) {
					
						if(c.getNom().equals(nameTeam)) {
							nomPersonne = InOut.getString("Entrer le nom du sportif � supprimer : ");
							for(Personne p : listGuys) {
								if(p.getNom().equals(nomPersonne)) {
									for(Equipe t : listTeam) {
										if(t.toString().equals(c.toString())) {
											t.remove(p);
											System.out.println(p.getNom() + " " + p.getPrenom() + ", a bien �tait supprim�e de : " + c.getNom());
											autoSave();
											deleteSuccess = true;
											break;
										}
									}
								}
							}
						}
					}
				
					if(!deleteSuccess) {
						System.out.println("La suppression a �chou�, car le sportif n'appartient pas  n'est pas r�pertori�e");
					}
				}while(!nomPersonne.equals("stop"));
			}
		};
	}
	
	private Menu menuPersonne() {
		Menu menuPersonne = new Menu("Personne", "p");
		menuPersonne.add(addAGuyOption());
		menuPersonne.add(listGuysOption());
		menuPersonne.add(removeGuyOption());
		menuPersonne.add(menuEditGuy());
//		menuPersonne.add(addAGuyInTeamOption());
		menuPersonne.addBack("b");
		return menuPersonne;
	}
	
	
	public Option addAGuyOption() {
		
		return new Option("Ajouter un sportif", "1", addAGuyAction());
	}
	
	private Action addAGuyAction() {
		
		return new Action ()
		{
			public void optionSelected()
			{
				String nomPersonne = InOut.getString("Nom : ");
				String prenomPersonne = InOut.getString("Prenom : ");
				String mailPersonne = InOut.getString("Mail : ");
				Personne pers = inscriptions.createPersonne(nomPersonne, prenomPersonne, mailPersonne);
				System.out.println(pers.getNom() + " " + pers.getPrenom() + ", a �tait cr��(e) avec succ�s" + " son mail est : " + pers.getMail());
				autoSave();
				Passerelle.save(pers);
			}
		};
	}

	public Option listGuysOption() {
		
		return new Option("Lister les sportifs", "2", listGuysAction());
	}

	//Listes les personnes
	private Action listGuysAction() {
		
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getPersonnes());
			}
		};
	}
	
	public Option removeGuyOption() {
		return new Option("Supprimer un sportif", "3", removeGuyAction());
	}
	
	private Action removeGuyAction() {
		return new Action() {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String nomPersonne = null;
				String prenomPersonne = null;
				boolean deleteSuccess = false;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						p.delete();
						nomPersonne = p.getNom();
						prenomPersonne = p.getPrenom();
						System.out.println(nomPersonne + " " + prenomPersonne + ", a bien �tait supprim�(e)");
						autoSave();
						deleteSuccess = true;
						Passerelle.delete(p);
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a �chou�, car le sportif n'est pas inscrit");
					
				}
					
			}
			
		};
	}
	
	public Menu menuEditGuy() {
		Menu menuEditGuy = new Menu ("Editer un sportif", "4");
		menuEditGuy.add(editNameOption());
		menuEditGuy.add(editLastNameOption());
		menuEditGuy.add(editMailOption());
		menuEditGuy.addBack("b");
		return menuEditGuy;
	}
	
	public Option editNameOption() {
		return new Option("Editer le nom", "1", editNameAction());
	}
	
	public Action editNameAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String nomPersonne = null;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						nomPersonne = p.getNom();
						String prenomPersonne = p.getPrenom();
						System.out.println("Vous vous appr�tez � modifier : " + nomPersonne + " " + prenomPersonne);
						nomPersonne  = InOut.getString("Entrer le nouveau nom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setNom(nomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le nom a bien �tait modifi�");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option editLastNameOption() {
		return new Option("Editer le prenom", "2", editLastNameAction());
	}
	
	public Action editLastNameAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				String prenomPersonne = null;
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						prenomPersonne = p.getPrenom();
						String nomPersonne = p.getNom();
						System.out.println("Vous vous appr�tez � modifier : " + nomPersonne + " " + prenomPersonne);
						prenomPersonne  = InOut.getString("Entrer le nouveau prenom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setPrenom(prenomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le prenom a bien �tait modifi�");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option editMailOption() {
		return new Option("Editer le mail", "3", editMailAction());
	}
	
	public Action editMailAction() {
		return new Action () {
			public void optionSelected() {
				String mailPersonne = InOut.getString("Mail : ");
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				
				for(Personne p : listGuys) {
					
					if(p.getMail().equals(mailPersonne)) {
						String nomPersonne = p.getNom();
						String prenomPersonne = p.getPrenom();
						System.out.println("Vous vous appr�tez le mail de " + nomPersonne + " " + prenomPersonne);
						mailPersonne  = InOut.getString("Entrer le nouveau mail : ");
						String checkEdit = InOut.getString(mailPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setMail(mailPersonne);
							System.out.println("Le mail de : " + nomPersonne + " " + prenomPersonne + "a bien �t� modifi�");
							autoSave();
							break;
						}
						else {
							break;
						}
					}
				}
			}
		};
	}
	
	public Option autoSaveOption() {
		return new Option("Sauvegarder", "x", autoSaveAction());
	}
	private Action autoSaveAction() {
		return new Action() {
			public void optionSelected() {
				try
				{
					inscriptions.sauvegarder();
				} 
				catch (IOException e)
				{
					System.out.println("Sauvegarde impossible." + e);
				}
			}
		};
	}
//	public Option addAGuyInTeamOption() {
//		return new Option("Ajouter un sportif dans une �quipe", "5", addAGuyInTeamAction());
//	}
//	
//	private Action addAGuyInTeamAction(Equipe e) {
//		return new Action() {
//			public void optionSelected() {
//				SortedSet <Candidat> listTeams = inscriptions.getCandidats();
//				SortedSet <Equipe> listTeam = inscriptions.getEquipes();
//				//System.out.println(inscriptions.getEquipes());
//				//String selectTeam = InOut.getString("Selectionner l'�quipe : ");
//				selectTeam = e.getNom();
//				String nomPersonne = null;
//				SortedSet <Personne> listGuys = inscriptions.getPersonnes();
//				System.out.println(inscriptions.getPersonnes());
//				
//				do {
//					nomPersonne = InOut.getString("Nom du sportif : ");
//					for(Candidat c : listTeams) {
//						if(c.getNom().equals(selectTeam)) {
//							for(Equipe t : listTeam) {
//								if(t.toString().equals(c.toString())) {
//									
//									for(Personne p : listGuys) {
//										if(p.getNom().equals(nomPersonne)) {
//											t.add(p);
//											autoSave();
//											System.out.println("Le sportif : " + p.getNom() + " " + p.getPrenom() + " a rejoint l'�quipe " + c.getNom());
//										}
//									}
//								}
//								else {
//									System.out.println("Il y a eu une erreur lors de l'ajout du sportif dans l'�quipe :" + c.getNom());
//								}
//							}
//							
//						}
//					}	
//				}while(!nomPersonne.equals("stop"));
//			}
//		};
//	}
}