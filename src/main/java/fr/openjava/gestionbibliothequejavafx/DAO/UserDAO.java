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

        String query = "INSERT INTO users (login, password, role) VALUES (?, ?, ?)";

        try (PreparedStatement result = conn.prepareStatement(query)) {
            result.setString(1, user.getLogin());
            result.setString(2, user.getPassword());
            result.setString(3, user.getRole());

            int affectedRows = result.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User created successfully!");
                return user;
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
                System.out.println("Login successful!");
                return user;
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

}
