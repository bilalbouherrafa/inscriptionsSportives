package inscriptions;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortNatural;

import Persistance.Passerelle;


/**
 * Représente une personne physique pouvant s'inscrire à une compétition.
 */

@Entity
public class Personne extends Candidat
{

	
	@OneToMany(targetEntity=Personne.class, mappedBy = "equipes", fetch=FetchType.EAGER)
	@Cascade(value = { CascadeType.ALL })
	@SortNatural
	private Set<Equipe> equipes;
	
	private static final long serialVersionUID = 4434646724271327254L;
	private String prenom, mail;
	

	Personne(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom);
		this.prenom = prenom;
		this.mail = mail;
		equipes = new TreeSet<>();
	}
	private Personne(){
		super();
	}

	/**
	 * Retourne le prénom de la personne.
	 * @return
	 */
	
	public String getPrenom()
	{
		return prenom;
	}

	/**
	 * Modifie le prénom de la personne.
	 * @param prenom
	 */
	
	public void setPrenom(String prenom)
	{
		this.prenom = prenom;
	}

	/**
	 * Retourne l'adresse électronique de la personne.
	 * @return
	 */
	
	public String getMail()
	{
		return mail;
	}

	/**
	 * Modifie l'adresse électronique de la personne.
	 * @param mail
	 */
	
	public void setMail(String mail)
	{
		this.mail = mail;
	}

	/**
	 * Retoure les équipes dont cette personne fait partie.
	 * @return
	 */
	
	public Set<Equipe> getEquipes()
	{
		return Collections.unmodifiableSet(equipes);
	}
	
	boolean add(Equipe equipe)
	{
		return equipes.add(equipe);
	}

	boolean remove(Equipe equipe)
	{
		return equipes.remove(equipe);
	}
	
	@Override
	public void delete()
	{	
		
		
		for (Equipe e : equipes)
			e.remove(this);
		super.delete();
	}
	
	@Override
	public String toString()
	{
		return super.toString() + " membre de " + equipes.toString();
	}
}
