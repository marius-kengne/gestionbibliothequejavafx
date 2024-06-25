package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class AuteurDAOTest {

    private AuteurDAO auteurDAO;
    Connection connection = Connexion.initConnexion(new Properties());

    @BeforeEach
    public void setUp() {
        auteurDAO = new AuteurDAO(connection);
    }

    @Test
    public void testSaveAndGetAuteur() {
        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
        auteur.setNom("TestNom");
        auteur.setPrenom("TestPrenom");

        auteurDAO.save(auteur);

        Bibliotheque.Livre.Auteur result = auteurDAO.getAuteur("TestNom", "TestPrenom");
        System.out.println("##########  auteur : " + result.toString());
        assertNotNull(result, "Author non trouvé");
        assertEquals("TestNom", result.getNom());
        assertEquals("TestPrenom", result.getPrenom());
    }


    @Test
    public void testGetId() {
        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
        auteur.setNom("TestNom2");
        auteur.setPrenom("TestPrenom2");

        auteurDAO.save(auteur);

        int id = auteurDAO.getId("TestNom2", "TestPrenom2");

        assertNotNull(id, "ID n'existe pas");
    }

}
