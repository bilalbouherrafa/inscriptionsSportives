package JUnit;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class TestPersonne {

    final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    final String input = "01-07-2018";
    final LocalDate localDate = LocalDate.parse(input, DATE_FORMAT);

    Inscriptions inscriptions = Inscriptions.getInscriptions();
    Competition foot = inscriptions.createCompetition("Mondial de foot", localDate, false);

    Personne tony = inscriptions.createPersonne("Tony", "Dent de plomb", "azerty");

    /*foot.add(tony);
    Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
    lesManouches.add(tony);*/

    @Test
    public void testDelete() {
        
    }

    @Test
    public void testToString() {
        fail("Not yet implemented");
    }

    @Test
    public void testPersonne() {
        String nom = tony.getNom();
        String prenom = tony.getPrenom();
        String mail = tony.getMail();

        assertEquals(nom, "Tony");
        assertEquals(prenom, "Dent de plomb");
        assertEquals(mail, "azerty");

        System.out.println("Personne : " + nom + " , " + prenom + " , " + mail);
    }

    @Test
    public void testGetPrenom() {
        String prenom = tony.getPrenom();

        assertEquals(prenom, "Dent de plomb");

    }
    
    @Test
    public void testSetPrenom() {
        String prenom = "Dent de cuivre";

        Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");

        assertEquals(tony.getPrenom(), prenom);




    }

    @Test
    public void testGetMail() {
        String mail = tony.getMail();

        assertEquals(mail, "azerty");


    }

    @Test
    public void testSetMail() {
        String mail = "1234";

        Personne bilz = inscriptions.createPersonne("Bilzer", "Le thuggy", mail);

        assertEquals("SetMail : " + mail + " , " + bilz.getMail(), bilz.getMail(), mail);

    }

    @Test
    public void testGetEquipes() {
    	
    	Equipe a = new Equipe(inscriptions, "test");
    	Personne crunchy = inscriptions.createPersonne("Gerz", "Le loup", "12");
    	a.add(crunchy);
    	Set<Equipe> equ = new TreeSet<Equipe>();
    	equ.add(a);
    	assertEquals(crunchy.getEquipes(), equ);
    	

    	
    }
    
    @Test
    public void testRemoveEquipe() {
    	Equipe a = inscriptions.createEquipe("a");
    	Equipe b = inscriptions.createEquipe("b");
    	
    	b.delete();
    	assertTrue(!inscriptions.getEquipes().contains(b));
    }

    @Test
    public void testAddEquipe() {
		Equipe a = inscriptions.createEquipe("a") ;
		foot.add(a);
		assertTrue(inscriptions.getEquipes().contains(a));
    }



}
