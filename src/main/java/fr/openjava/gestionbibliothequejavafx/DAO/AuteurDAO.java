package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe DAO pour gérer les opérations de base de données liées aux auteurs.
 */
public class AuteurDAO {

    private final Connection conn;

    public AuteurDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Enregistre un auteur dans la base de données.
     *
     * @param auteur l'auteur à enregistrer
     * @return l'auteur enregistré, ou null si l'enregistrement a échoué
     */
    public Bibliotheque.Livre.Auteur save(Bibliotheque.Livre.Auteur auteur){

        String query = "INSERT INTO auteurs (nom, prenom) VALUES (?, ?)";

        try (PreparedStatement result = conn.prepareStatement(query)) {
            result.setString(1, auteur.getNom());
            result.setString(2, auteur.getPrenom());

            int affectedRows = result.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User created successfully!");
                return auteur;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return null;
    }

    /**
     * Récupère un auteur par son nom et son prénom.
     *
     * @param nom le nom de l'auteur
     * @param prenom le prénom de l'auteur
     * @return l'auteur trouvé, ou null si aucun auteur correspondant n'a été trouvé
     */
    public Bibliotheque.Livre.Auteur getAuteur(String nom, String prenom) {

        String sql = "SELECT * FROM auteurs WHERE nom = ? AND prenom = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Bibliotheque.Livre.Auteur auteur = new Bibliotheque.Livre.Auteur();
                auteur.setNom(rs.getString("nom"));
                auteur.setPrenom(rs.getString("prenom"));
                System.out.println("Login successful!");
                return auteur;
            } else {
                System.out.println("Invalid login or password.");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return null;
    }

    /**
     * Récupère l'ID d'un auteur par son nom et son prénom.
     *
     * @param nom le nom de l'auteur
     * @param prenom le prénom de l'auteur
     * @return l'ID de l'auteur, ou null si aucun auteur correspondant n'a été trouvé
     */
    public Integer getId(String nom, String prenom) {

        String sql = "SELECT * FROM auteurs WHERE nom = ? AND prenom = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("auteur trouvé avec success!");
                return rs.getInt("id");
            } else {
                System.out.println("auteur inexistant.");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return null;
    }
}
