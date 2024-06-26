package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe DAO pour gérer les opérations de base de données liées aux livres.
 */
public class BibliothequeDAO {

    private final Connection conn;

    /**
     * Ajout d'un constructeur pour permettre l'injection de connexion
     * @param conn objet connection pour l'interaction avec la BD
      */
    public BibliothequeDAO(Connection conn) {
        this.conn = conn;
    }
    /**
     * Enregistre un livre dans la base de données.
     *
     * @param livre le livre à enregistrer
     * @param auteur l'auteur du livre
     * @return le livre enregistré, ou null si l'enregistrement a échoué
     */
    public Bibliotheque.Livre save(Bibliotheque.Livre livre, Bibliotheque.Livre.Auteur auteur){

        String query = "INSERT INTO livres (" +
                "titre, auteur_id, presentation, parution, colonne, rangee, image, resume, status" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement result = conn.prepareStatement(query)) {

            Bibliotheque.Livre.Auteur currentAuteur = new AuteurDAO(conn).save(auteur);

            String query2 = "SELECT id FROM `auteurs` WHERE `prenom` LIKE ? AND `nom` LIKE ?";
            int idresult = 0;
            try (PreparedStatement result2 = conn.prepareStatement(query2)) {
                result2.setString(1, "%" + currentAuteur.getPrenom() + "%");
                result2.setString(2, "%" + currentAuteur.getNom() + "%");
                try (ResultSet rs = result2.executeQuery()) {
                    if (rs.next()) {
                        idresult = rs.getInt("id");
                        System.out.println("Check auteur successfully! ID: " + idresult);
                    } else {
                        System.out.println("Check auteur null!");
                    }
                }
            } catch (Exception e) {
                System.out.println("Error for the check auteur!\n" + e);
            }

            if (currentAuteur != null){
                result.setString(1, livre.getTitre());
                //result.setString(2, livre.getAuteur().getNom() + " " +livre.getAuteur().getPrenom());
                result.setString(2, ""+idresult);
                result.setString(3, livre.getPresentation());
                result.setInt(4, livre.getParution());
                result.setInt(5, livre.getColonne());
                result.setInt(6, livre.getRangee());
                result.setString(7, livre.getImage());
                result.setString(8, livre.getResume());
                result.setString(9, livre.getStatus());

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
        }

        return null;
    }


}
