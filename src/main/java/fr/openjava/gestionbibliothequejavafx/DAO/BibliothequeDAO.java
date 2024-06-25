package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe DAO pour gérer les opérations de base de données liées aux livres.
 */
public class BibliothequeDAO {

    private final Connection conn = Connexion.initConnexion(new Properties());
    Connection connection = Connexion.initConnexion(new Properties());


    /**
     * Enregistre un livre dans la base de données.
     *
     * @param livre le livre à enregistrer
     * @param auteur l'auteur du livre
     * @return le livre enregistré, ou null si l'enregistrement a échoué
     */
    public Bibliotheque.Livre save(Bibliotheque.Livre livre, Bibliotheque.Livre.Auteur auteur){

        String query = "INSERT INTO livre (" +
                "titre, auteur_id, presentation, parution, colonne, rangee, image, resume, status" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement result = conn.prepareStatement(query)) {

            Bibliotheque.Livre.Auteur currentAuteur = new AuteurDAO(connection).save(auteur);

            if (currentAuteur != null){
                result.setString(1, livre.getTitre());
                result.setString(2, livre.getPresentation());
                result.setString(3, livre.getPresentation());
                result.setInt(4, livre.getParution());
                result.setInt(5, livre.getColonne());
                result.setInt(6, livre.getRangee());
                result.setString(7, livre.getImage());
                result.setString(8, livre.getResume());
                result.setString(8, livre.getStatus());

                int affectedRows = result.executeUpdate();

                if (affectedRows > 0) {
                    System.out.println("Livre created successfully!");
                    return livre;
                }
            }else {
                System.out.println("Echec de l'enregistrement du livre");
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("SQLException: " + ex.getMessage());
            }
        }

        return null;
    }


}
