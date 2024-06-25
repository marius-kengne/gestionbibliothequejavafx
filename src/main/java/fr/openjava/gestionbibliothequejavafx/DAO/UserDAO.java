package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Classe d'accès aux données pour l'entité User, permettant de gérer les opérations
 * de création d'utilisateur et de connexion.
 */
public class UserDAO {

    private final Connection conn = Connexion.initConnexion(new Properties());

    private final Connection con;

    // Ajout d'un constructeur pour permettre l'injection de connexion
    public UserDAO() {
        this.con = conn;
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

}
