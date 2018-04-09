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
//		Menu menuCompetition = new Menu("Compétition", "3");
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
		Menu menuCompetition = new Menu("Menu Compétition", "c");
		
		menuCompetition.add(createCompetOption());
		menuCompetition.add(listCompetOption());
		menuCompetition.add(selectCompet());
		menuCompetition.add(autoSaveOption());
		menuCompetition.addBack("b");
		return menuCompetition;
	}
	public Option createCompetOption() {
		return new Option("Créer une compétition", "c", createCompetAction());
	}
	
	private Action createCompetAction() {
        return new Action() {
            public void optionSelected() {

            	
                String nomCompet = InOut.getString("Entrer le nom de la compétition : ");
                String teamOrNotTeam = InOut.getString("Entrer 'equipe' pour une compétition en équipe ou solo pour compétition individuel ? (equipe/solo) ");
                Boolean enEquipe = teamOrNotTeam.equals("equipe");
                try {
                    String dateCloture = InOut.getString("Entrer la date de clôture (yyyy-mm-dd) : ");
                    Date localDate = formatter.parse(dateCloture);
                    inscriptions.createCompetition(nomCompet, localDate, enEquipe);
                    System.out.println("La compétition, " + nomCompet + " a était créée avec succés");
                } catch(ParseException e) {
                     System.out.println("Veuillez respecter le format de la date 'yyyy-mm-dd' ! " + e);
                }
            }
        };
    }
	
	public Option listCompetOption() {
		return new Option("Lister les compétitions", "a",
				() -> {System.out.println(inscriptions.getCompetitions());}
		);
	}
	
	private List<Competition> selectCompet() {
		return new List<Competition>("Sélectionner une compétition", "e",
				() -> new ArrayList<>(inscriptions.getCompetitions()),
				(element) -> menuSelectCompet(element)
				);
	}
	
	public Menu menuSelectCompet(Competition competition) {
		Menu selectCompet = new Menu("Compétition : " + competition.getNom());
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
		return new List <Equipe>("Sélection de l'équipe", "e",
				() -> clone,
				(element) -> addTeamInCompet(competition, element)
			);
	}
	
	public Option addTeamInCompet(Competition competition, Equipe equipe) {
		return new Option ("Inscrire " + equipe.getNom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {competition.add(equipe); System.out.println(equipe.getNom() + " a était inscrit dans la compétition : " + competition.getNom());}
				);
	}
	
	public Option addGuyInCompetOption(Competition competition) {
		return new List <Personne>("Sélection du sportif", "s",
				() -> new ArrayList<>(inscriptions.getPersonnes()),
				(element) -> addGuyInCompet(competition, element) 
			);
	}
	
	public Option addGuyInCompet(Competition competition, Personne personne) {
		return new Option ("Inscrire " + personne.getNom() + " " + personne.getPrenom() + " dans la compétition : " + competition.getNom(), "a",
				() -> {competition.add(personne); System.out.println(personne.getNom() + " " + personne.getPrenom() + " a était inscrit dans la compétition : " + competition.getNom());}
				);
	}
	
	public Option editNameCompetOption(Competition competition) {
		return new Option("Renommer une compétition", "r", 
				() -> {String newName = InOut.getString("Entrer le nouveau nom de la compétition : "); competition.setNom(newName); System.out.println("La compétition à bien était renommé");}
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
		return new Option("Supprimer la compétition : " + competition.getNom(), "r",
				() -> {
					if(competition.getCandidats().isEmpty()){
						competition.delete();
						System.out.println("La competition a bien était supprimée");
					}
					else {
						System.out.println("La compétition n'est pas vide ! Veuillez supprimer les candidats incrits dans la compétition !");
					}
				}
				);
	}

	public Option removeGuyOrTeamOfCompetOption(Competition competition) {
		return new List<Candidat>("Selectionner l'équipe ou le sportif a supprimer de la compétition : " + competition.getNom(), "d",
				() -> new ArrayList<>(competition.getCandidats()),
				(element) -> removeGuyOrTeamOfCompetAction(competition, element)
				);
	}

	private Option removeGuyOrTeamOfCompetAction(Competition competition, Candidat candidat) {
		return new Option("Supprimer " + candidat.getNom() + " de " + competition.getNom(), "s",
				() -> {competition.remove(candidat); System.out.println(candidat.getNom() + " a bien était supprimé(e) de la compétition : " + competition.getNom());}
				);
	}
	
	public Option detailCompet(Competition competition) {
		return new Option("Information détaillées sur la compétition : " + competition.getNom(), "t",
				() -> {
					if(competition.estEnEquipe()) {
						System.out.println("\nCompétition : " + competition.getNom() + "\n Date de clôture : " + competition.getDateCloture() + "\n Type : Equipe \n ");
					}
					else {
						System.out.println("\nCompétition : " + competition.getNom() + "\n Date de clôture : " + competition.getDateCloture() + "\n Type : Individuel \n ");
					}
				}
				);
	}
	
	public Option editDateEnd(Competition competition) {
		return new Option("Repousser la date de clôture", "d",
				() -> {		
					
						try {
							String dateCloture = InOut.getString("Entrer la nouvelle date de clôture (dd-mm-yyyy) : ");
							Date localDate = formatter.parse(dateCloture);
							competition.setDateCloture(localDate);
						} catch(ParseException e) {
							System.out.println("Veuillez respecter le format de la date 'dd-mm-yyyy' ! " + e);
						}
					
					System.out.println("La date a bien était repoussée");
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
		return new Option("Créer une équipe", "1", createTeamAction());
	}
	
	
	private Action createTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nomEquipe = InOut.getString("Entrer le nom de l'équipe : ");
				Equipe team = inscriptions.createEquipe (nomEquipe);
				System.out.println("L'équipe, " + nomEquipe + " a était créée avec succés");
				autoSave();
				Passerelle.save(team);
			}
		};
	}
	
	public Option listTeamOption() {
		return new Option("Lister les équipes", "2", listTeamAction());
	}
	
	private Action listTeamAction() {
		return new Action() {
			public void optionSelected() {
				System.out.println(inscriptions.getEquipes());
			}
		};
	}

	

	
//	private Menu sousMenuEquipe() {
//		List<Equipe> sousMenuEquipe = new List<Equipe>("Selectionne une équipe","3",
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
		return new List<Equipe>("Selectionner une équipe", "e",
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
//		//String nameTeam = InOut.getString("Nom de l'équipe : ");
//		//sousMenuEquipe.start();
//		return sousMenuEquipe;
//	}
//	
	public Option listMemberTeamOption(Equipe e) {
		return new Option("Afficher les joueurs de l'équipe", "a",
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
		return new Option("Ajouter " + p.getNom() + " " + p.getPrenom() + " à " + e.getNom(), "a",
				() -> {e.add(p);}
				);
	}
	
	public Option removeTeamOption() {
		return new Option("Supprimer l'équipe", "s", removeTeamAction());
	}
	
	private Action removeTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						c.delete();
						System.out.println(c.getNom() + ", a bien était supprimée");
						autoSave();
						deleteSuccess = true;
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a échoué, car l'équipe n'est pas répertoriée");
					
				}
			}
		};
	}
	
	public Option editNameTeamOption() {
		return new Option("Editer le nom d'une équipe", "5", editNameTeamAction());
	}
	
	private Action editNameTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				
				for(Candidat c : listTeams) {
					
					if(c.getNom().equals(nameTeam)) {
						String newName = InOut.getString("Nouveau de l'équipe : ");
						c.setNom(newName);
						autoSave();
						System.out.println("Le nouveau nom de l'équipe : " + nameTeam + "est : " + newName);
						break;
					}
				}
			}
		};
	}
	
	public Option removeGuyOfTeamOption() {
		return new Option("Supprimer un sportif d'une équipe", "6", removeGuyOfTeamAction());
	}
	
	private Action removeGuyOfTeamAction() {
		return new Action() {
			public void optionSelected() {
				String nameTeam = InOut.getString("Nom de l'équipe : ");
				boolean deleteSuccess = false;
				SortedSet<Candidat> listTeams = inscriptions.getCandidats();
				SortedSet<Personne> listGuys = inscriptions.getPersonnes();
				SortedSet<Equipe> listTeam = inscriptions.getEquipes();
				String nomPersonne = null;
				
				do {
					for(Candidat c : listTeams) {
					
						if(c.getNom().equals(nameTeam)) {
							nomPersonne = InOut.getString("Entrer le nom du sportif à supprimer : ");
							for(Personne p : listGuys) {
								if(p.getNom().equals(nomPersonne)) {
									for(Equipe t : listTeam) {
										if(t.toString().equals(c.toString())) {
											t.remove(p);
											System.out.println(p.getNom() + " " + p.getPrenom() + ", a bien était supprimée de : " + c.getNom());
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
						System.out.println("La suppression a échoué, car le sportif n'appartient pas  n'est pas répertoriée");
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
				System.out.println(pers.getNom() + " " + pers.getPrenom() + ", a était créé(e) avec succés" + " son mail est : " + pers.getMail());
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
						System.out.println(nomPersonne + " " + prenomPersonne + ", a bien était supprimé(e)");
						autoSave();
						deleteSuccess = true;
						Passerelle.delete(p);
						break;
					}
				}
				
				if(!deleteSuccess) {
					System.out.println("La suppression a échoué, car le sportif n'est pas inscrit");
					
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
						System.out.println("Vous vous apprêtez à modifier : " + nomPersonne + " " + prenomPersonne);
						nomPersonne  = InOut.getString("Entrer le nouveau nom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setNom(nomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le nom a bien était modifié");
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
						System.out.println("Vous vous apprêtez à modifier : " + nomPersonne + " " + prenomPersonne);
						prenomPersonne  = InOut.getString("Entrer le nouveau prenom : ");
						String checkEdit = InOut.getString(nomPersonne + " " + prenomPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setPrenom(prenomPersonne);
							System.out.println(nomPersonne + " " + prenomPersonne + ", le prenom a bien était modifié");
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
						System.out.println("Vous vous apprêtez le mail de " + nomPersonne + " " + prenomPersonne);
						mailPersonne  = InOut.getString("Entrer le nouveau mail : ");
						String checkEdit = InOut.getString(mailPersonne + ", vous validez la modification ? (Y/N) ");

						if(checkEdit.equals("y") || checkEdit.equals("Y") || checkEdit.equals("o") || checkEdit.equals("O")) {
							p.setMail(mailPersonne);
							System.out.println("Le mail de : " + nomPersonne + " " + prenomPersonne + "a bien été modifié");
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
//		return new Option("Ajouter un sportif dans une équipe", "5", addAGuyInTeamAction());
//	}
//	
//	private Action addAGuyInTeamAction(Equipe e) {
//		return new Action() {
//			public void optionSelected() {
//				SortedSet <Candidat> listTeams = inscriptions.getCandidats();
//				SortedSet <Equipe> listTeam = inscriptions.getEquipes();
//				//System.out.println(inscriptions.getEquipes());
//				//String selectTeam = InOut.getString("Selectionner l'équipe : ");
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
//											System.out.println("Le sportif : " + p.getNom() + " " + p.getPrenom() + " a rejoint l'équipe " + c.getNom());
//										}
//									}
//								}
//								else {
//									System.out.println("Il y a eu une erreur lors de l'ajout du sportif dans l'équipe :" + c.getNom());
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