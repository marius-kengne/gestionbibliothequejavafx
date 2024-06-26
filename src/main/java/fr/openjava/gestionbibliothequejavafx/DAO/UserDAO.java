package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;

import java.sql.*;

/**
 * Classe d'accès aux données pour l'entité User, permettant de gérer les opérations
 * de création d'utilisateur et de connexion.
 */
public class UserDAO {

    //private final Connection conn = Connexion.initConnexion(new Properties());

    private final Connection conn;

    // Ajout d'un constructeur pour permettre l'injection de connexion
    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Crée un nouvel utilisateur dans la base de données.
     *
     * @param user l'utilisateur à créer
     * @return l'utilisateur créé si l'opération réussit, sinon null
     */
    public User createUser(User user){

        String query = "INSERT INTO users (lastName, firstName, login, password, role, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement result = conn.prepareStatement(query)) {
            result.setString(1, user.getLastName());
            result.setString(2, user.getFirstName());
            result.setString(3, user.getLogin());
            result.setString(4, user.getPassword());
            result.setString(5, user.getRole());
            result.setBoolean(6, user.isAdmin());

            int affectedRows = result.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilisateur crée avec succes!");
                return user;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return null;
    }


    /**
     * Vérifie les informations de connexion d'un utilisateur dans la base de données.
     *
     * @param login    le nom d'utilisateur
     * @param password le mot de passe
     * @return l'utilisateur correspondant aux informations de connexion si elles sont valides, sinon null
     */
    public User login(String login, String password) {

        String sql = "SELECT * FROM users WHERE login = ? AND password = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                System.out.println("Utisateur connecté avec succès!");
                return user;
            } else {
                System.out.println("Email ou mot de passe Invalide!");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return null;
    }



    /**
     * Supprime un utilisateur de la base de données en fonction de son login.
     *
     * @param login le login de l'utilisateur à supprimer
     * @return true si l'utilisateur est supprimé avec succès, sinon false
     */
    public boolean deleteUser(String login) {
        String sql = "DELETE FROM users WHERE login = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, login);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilisateur supprimé avec succès!");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce login.");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return false;
    }



    /**
     * Modifie le mot de passe d'un utilisateur de la base de données en fonction de son login et son nouveau de passe.
     *
     * @param login le login de l'utilisateur
     * @param newPassword nouveau mot de passe de l'utilisateur
     * @return true si l'utilisateur est supprimé avec succès, sinon false
     */
    public boolean updatePassword(String login, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE login = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, login);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return false;
    }

}