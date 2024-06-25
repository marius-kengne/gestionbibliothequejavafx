package fr.openjava.gestionbibliothequejavafx.DAO;

import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {

    private UserDAO userDAO;
    Connection connection = Connexion.initConnexion(new Properties());

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO(connection);
    }

    @Test
    public void testCreateUser() {

        // initialisation du user
        User user = new User();
        user.setLastName("testCreate");
        user.setFirstName("testCreate");
        user.setLogin("testCreate@gmail.com");
        user.setPassword("testCreate");
        user.setRole("user");
        user.setAdmin(false);

        User createdUser = userDAO.createUser(user);

        assertNotNull(createdUser, "L'utilisateur devrait être créé avec succès");
        assertEquals(user.getLogin(), createdUser.getLogin());
        assertEquals(user.getRole(), createdUser.getRole());

    }


    @Test
    public void testLogin() {
        // Créer un utilisateur pour le tester
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setLogin("johndoe@gmail.com");
        user.setPassword("password");
        user.setRole("user");
        user.setAdmin(false);

        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);

        User loggedInUser = userDAO.login(user.getLogin(), user.getPassword());

        assertNotNull(loggedInUser, "L'utilisateur devrait être connecté avec succès");
        assertEquals(user.getLogin(), loggedInUser.getLogin());

        boolean deleted = userDAO.deleteUser(user.getLogin());
        assertTrue(deleted);
    }

    @Test
    public void testDeleteUser() {
        // initialisation du user
        User user = new User();
        user.setLastName("testDelete");
        user.setFirstName("testDelete");
        user.setLogin("testDelete@gmail.com");
        user.setPassword("testDelete");
        user.setRole("user");
        user.setAdmin(false);

        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);

        boolean deleted = userDAO.deleteUser(user.getLogin());
        assertTrue(deleted);
    }

}
