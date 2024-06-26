package fr.openjava.gestionbibliothequejavafx.DAO.Mock;

import fr.openjava.gestionbibliothequejavafx.DAO.AuteurDAO;
import fr.openjava.gestionbibliothequejavafx.DAO.BibliothequeDAO;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BibliothequeDAOMockTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatementLivre;

    @Mock
    private PreparedStatement mockPreparedStatementAuteur;

    @InjectMocks
    private BibliothequeDAO bibliothequeDAO;

    @Mock
    private AuteurDAO mockAuteurDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);

        when(mockConnection.prepareStatement("INSERT INTO livres (titre, auteur_id, presentation, parution, colonne, rangee, image, resume, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"))
                .thenReturn(mockPreparedStatementLivre);
        when(mockConnection.prepareStatement("INSERT INTO auteurs (nom, prenom) VALUES (?, ?)"))
                .thenReturn(mockPreparedStatementAuteur);

        when(mockPreparedStatementLivre.executeUpdate()).thenReturn(1);
        when(mockPreparedStatementAuteur.executeUpdate()).thenReturn(1);

        Bibliotheque.Livre.Auteur mockAuteur = new Bibliotheque.Livre.Auteur();
        mockAuteur.setNom("Test Nom");
        mockAuteur.setPrenom("Test Prenom");
        when(mockAuteurDAO.save(any(Bibliotheque.Livre.Auteur.class))).thenReturn(mockAuteur);
    }

    @Test
    public void testSave() throws SQLException {
        Bibliotheque.Livre livre = new Bibliotheque.Livre();
        livre.setTitre("Test Livre");
        livre.setPresentation("Test Presentation");
        livre.setParution(2021);
        livre.setColonne((short) 1);
        livre.setRangee((short) 1);
        livre.setImage("test_image.jpg");
        livre.setResume("Test Resume");
        livre.setStatus("Disponible");

        Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
        auteur.setNom("Test Nom");
        auteur.setPrenom("Test Prenom");
        livre.setAuteur(auteur);

        Bibliotheque.Livre result = bibliothequeDAO.save(livre, auteur);

        assertNotNull(result, "Le livre ne doit pas être nul");


        verify(mockPreparedStatementLivre).setString(1, "Test Livre");
        //verify(mockPreparedStatementLivre).setString(2, "Test Nom Test Prenom");
        verify(mockPreparedStatementLivre).setString(3, "Test Presentation");
        verify(mockPreparedStatementLivre).setInt(4, 2021);
        verify(mockPreparedStatementLivre).setInt(5, 1);
        verify(mockPreparedStatementLivre).setInt(6, 1);
        verify(mockPreparedStatementLivre).setString(7, "test_image.jpg");
        verify(mockPreparedStatementLivre).setString(8, "Test Resume");
        verify(mockPreparedStatementLivre).setString(9, "Disponible");
        verify(mockPreparedStatementLivre, times(1)).executeUpdate();

        verify(mockPreparedStatementAuteur).setString(1, "Test Nom");
        verify(mockPreparedStatementAuteur).setString(2, "Test Prenom");
        verify(mockPreparedStatementAuteur, times(1)).executeUpdate();
    }
}
