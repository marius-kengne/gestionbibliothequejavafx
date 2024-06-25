package fr.openjava.gestionbibliothequejavafx.DAO.Mock;

import fr.openjava.gestionbibliothequejavafx.DAO.AuteurDAO;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class AuteurDAOMockTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private AuteurDAO auteurDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        auteurDAO = new AuteurDAO(mockConnection);
    }

    @Test
    public void testSaveAndGetAuteur() throws SQLException {
        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
        auteur.setNom("TestNom");
        auteur.setPrenom("TestPrenom");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("nom")).thenReturn("TestNom");
        when(mockResultSet.getString("prenom")).thenReturn("TestPrenom");

        auteurDAO.save(auteur);

        Bibliotheque.Livre.Auteur result = auteurDAO.getAuteur("TestNom", "TestPrenom");

        assertNotNull(result, "Author non trouvé");
        assertEquals("TestNom", result.getNom());
        assertEquals("TestPrenom", result.getPrenom());
    }

    @Test
    public void testGetId() throws SQLException {
        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
        auteur.setNom("TestNom2");
        auteur.setPrenom("TestPrenom2");

        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("id")).thenReturn(123);

        auteurDAO.save(auteur);

        int id = auteurDAO.getId("TestNom2", "TestPrenom2");

        assertTrue(id > 0, "ID n'existe pas");
    }
}
