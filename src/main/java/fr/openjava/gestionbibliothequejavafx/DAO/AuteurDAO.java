package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque.Livre.Auteur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe DAO pour gérer les opérations de base de données liées aux auteurs.
 */
public class AuteurDAO {

    private static final Logger logger = Logger.getLogger(AuteurDAO.class.getName());

    private static final String INSERT_AUTEUR_SQL = "INSERT INTO auteurs (nom, prenom) VALUES (?, ?)";
    private static final String SELECT_AUTEUR_SQL = "SELECT * FROM auteurs WHERE nom = ? AND prenom = ?";

    private final Connection conn;

    /**
     * Constructeur de la classe AuteurDAO.
     *
     * @param conn la connexion à la base de données
     */
    public AuteurDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Enregistre un auteur dans la base de données.
     *
     * @param auteur l'auteur à enregistrer
     * @return l'auteur enregistré, ou null si l'enregistrement a échoué
     */
    public Auteur save(Auteur auteur) {
        try (PreparedStatement result = conn.prepareStatement(INSERT_AUTEUR_SQL)) {
            result.setString(1, auteur.getNom());
            result.setString(2, auteur.getPrenom());

            int affectedRows = result.executeUpdate();

            if (affectedRows > 0) {
                logger.info("Auteur créé avec succès!");
                return auteur;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erreur lors de la création de l'auteur: {0}", ex.toString());
        }

        return null;
    }

    /**
     * Récupère un auteur par son nom et son prénom.
     *
     * @param nom    le nom de l'auteur
     * @param prenom le prénom de l'auteur
     * @return l'auteur trouvé, ou null si aucun auteur correspondant n'a été trouvé
     */
    public Auteur getAuteur(String nom, String prenom) {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_AUTEUR_SQL)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Auteur auteur = new Auteur();
                    auteur.setNom(rs.getString("nom"));
                    auteur.setPrenom(rs.getString("prenom"));
                    logger.info("Auteur trouvé avec succès!");
                    return auteur;
                } else {
                    logger.info("Auteur inexistant.");
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erreur lors de la recherche de l'auteur: {0}", ex.getMessage());
        }

        return null;
    }

    /**
     * Récupère l'ID d'un auteur par son nom et son prénom.
     *
     * @param nom    le nom de l'auteur
     * @param prenom le prénom de l'auteur
     * @return l'ID de l'auteur, ou null si aucun auteur correspondant n'a été trouvé
     */
    public Integer getId(String nom, String prenom) {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_AUTEUR_SQL)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    logger.info("Auteur inexistant");
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Erreur lors de la recherche de l'auteur: {0}", ex.getMessage());
        }

        return null;
    }
}
