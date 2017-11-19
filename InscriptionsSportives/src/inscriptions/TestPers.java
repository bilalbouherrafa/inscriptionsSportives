package inscriptions;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

public class TestPers {

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
        fail("Not yet implemented");
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

        System.out.println("Get : " + prenom + " , Dent de plomb");
    }
    
    @Test
    public void testSetPrenom() {
        String prenom = "Dent de cuivre";

        Personne tony = inscriptions.createPersonne("tony", prenom, "azerty");

        assertEquals(tony.getPrenom(), prenom);

        System.out.println("Set : " + prenom + " , " + tony.getPrenom());


    }

    @Test
    public void testGetMail() {
        String mail = tony.getMail();

        assertEquals(mail, "azerty");

        System.out.println("GetMail : " + mail + " , " + tony.getMail());
    }

    @Test
    public void testSetMail() {
        String mail = "1234";

        Personne tony = inscriptions.createPersonne("tony", "Dent de cuivre", mail);

        assertEquals(tony.getMail(), mail);

        System.out.println("SetMail : " + mail + " , " + tony.getMail());
    }

    @Test
    public void testGetEquipes() {
    	Equipe a = inscriptions.createEquipe("test");
    	Personne crunchy = inscriptions.createPersonne("tony", "Dent de cuivre", "12");
    	a.add(crunchy);
    	//assertEquals(crunchy.getEquipes(), "[" + a + "]");
    	System.out.println("[" + a + "]");
    	System.out.println(crunchy.getEquipes());
    	
    }

    @Test
    public void testAddEquipe() {
        fail("Not yet implemented");
    }

    @Test
    public void testRemoveEquipe() {
        fail("Not yet implemented");
    }

}
