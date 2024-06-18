package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuteurDAO {

    private final Connection conn = Connexion.initConnexion();

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


    public Bibliotheque.Livre.Auteur getAuteur(String nom, String prenom) {

        String sql = "SELECT * FROM auteur WHERE nom = ? AND prenom = ?";

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


    public String getId(String nom, String prenom) {

        String sql = "SELECT * FROM auteur WHERE nom = ? AND prenom = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("auteur trouvé avec success!");
                return rs.getString("id");
            } else {
                System.out.println("auteur inexistant.");
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
