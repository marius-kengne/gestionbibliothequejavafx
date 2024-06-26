package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe d'accès aux données pour l'entité User, permettant de gérer les opérations
 * de création d'utilisateur et de connexion.
 */
public class UserDAO {

    private static final String INSERT_USER_SQL = "INSERT INTO users (lastName, firstName, login, password, role, isAdmin) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_SQL = "SELECT login, password, role, isAdmin FROM users WHERE login = ? AND password = ?";
    private static final String DELETE_USER_SQL = "DELETE FROM users WHERE login = ?";
    private static final String UPDATE_PASSWORD_SQL = "UPDATE users SET password = ? WHERE login = ?";

    private final Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public User createUser(User user) {
        try (PreparedStatement result = conn.prepareStatement(INSERT_USER_SQL)) {
            result.setString(1, user.getLastName());
            result.setString(2, user.getFirstName());
            result.setString(3, user.getLogin());
            result.setString(4, user.getPassword());
            result.setString(5, user.getRole());
            result.setBoolean(6, user.isAdmin());

            int affectedRows = result.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilisateur créé avec succès!");
                return user;
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la création de l'utilisateur: " + ex.getMessage());
        }

        return null;
    }

    public User login(String login, String password) {
        try (PreparedStatement pstmt = conn.prepareStatement(SELECT_USER_SQL)) {
            pstmt.setString(1, login);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(rs.getString("role"));
                    user.setAdmin(rs.getBoolean("isAdmin"));
                    System.out.println("Utilisateur connecté avec succès!");
                    return user;
                } else {
                    System.out.println("Email ou mot de passe invalide!");
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la connexion de l'utilisateur: " + ex.getMessage());
        }

        return null;
    }

    public boolean deleteUser(String login) {
        try (PreparedStatement pstmt = conn.prepareStatement(DELETE_USER_SQL)) {
            pstmt.setString(1, login);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Utilisateur supprimé avec succès!");
                return true;
            } else {
                System.out.println("Aucun utilisateur trouvé avec ce login.");
            }
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + ex.getMessage());
        }

        return false;
    }

    public boolean updatePassword(String login, String newPassword) {
        try (PreparedStatement pstmt = conn.prepareStatement(UPDATE_PASSWORD_SQL)) {
            pstmt.setString(1, newPassword);
            pstmt.setString(2, login);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException ex) {
            System.err.println("Erreur lors de la mise à jour du mot de passe: " + ex.getMessage());
        }

        return false;
    }
}
