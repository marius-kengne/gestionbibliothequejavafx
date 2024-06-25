package fr.openjava.gestionbibliothequejavafx.DAO.Mock;
import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
public class UserDAOMockTest {
    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    private UserDAO userDAO;

    @BeforeEach
    public void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        userDAO = new UserDAO(mockConnection);
    }

    @Test
    public void testCreateUser() throws SQLException {
        User user = new User();
        user.setLastName("testCreate");
        user.setFirstName("testCreate");
        user.setLogin("testCreate@gmail.com");
        user.setPassword("testCreate");
        user.setRole("user");
        user.setAdmin(false);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        User createdUser = userDAO.createUser(user);

        assertNotNull(createdUser, "L'utilisateur devrait être créé avec succès");
        assertEquals(user.getLogin(), createdUser.getLogin());
        assertEquals(user.getRole(), createdUser.getRole());
    }

    @Test
    public void testLogin() throws SQLException {
        User user = new User();
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setLogin("johndoe@gmail.com");
        user.setPassword("password");
        user.setRole("user");
        user.setAdmin(false);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("login")).thenReturn(user.getLogin());
        when(mockResultSet.getString("password")).thenReturn(user.getPassword());
        when(mockResultSet.getString("role")).thenReturn(user.getRole());

        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);

        User loggedInUser = userDAO.login(user.getLogin(), user.getPassword());

        assertNotNull(loggedInUser, "L'utilisateur devrait être connecté avec succès");
        assertEquals(user.getLogin(), loggedInUser.getLogin());

        boolean deleted = userDAO.deleteUser(user.getLogin());
        assertTrue(deleted);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        User user = new User();
        user.setLastName("testDelete");
        user.setFirstName("testDelete");
        user.setLogin("testDelete@gmail.com");
        user.setPassword("testDelete");
        user.setRole("user");
        user.setAdmin(false);

        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        User createdUser = userDAO.createUser(user);
        assertNotNull(createdUser);

        boolean deleted = userDAO.deleteUser(user.getLogin());
        assertTrue(deleted);
    }
}
