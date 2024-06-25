package fr.openjava.gestionbibliothequejavafx.DAO.Mock;

import fr.openjava.gestionbibliothequejavafx.DAO.Connexion;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DriverManager.class, Connexion.class})
public class ConnexionMockTest {
    @Mock
    private Properties mockProperties;

    @Mock
    private Connection mockConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInitConnexion() throws SQLException, ClassNotFoundException {
        // Arrange
        when(mockProperties.getProperty("jdbc.driver.class")).thenReturn("com.mysql.cj.jdbc.Driver");
        when(mockProperties.getProperty("jdbc.url")).thenReturn("jdbc:mysql://localhost:3306/testdb");
        when(mockProperties.getProperty("jdbc.login")).thenReturn("testuser");
        when(mockProperties.getProperty("jdbc.password")).thenReturn("testpassword");

        // Mocking DriverManager
        PowerMockito.mockStatic(DriverManager.class);
        when(DriverManager.getConnection(anyString(), anyString(), anyString())).thenReturn(mockConnection);

        // Act
        Connection connection = Connexion.initConnexion(mockProperties);

        // Assert
        assertNotNull(String.valueOf(connection), "La connexion ne doit pas être nulle");
        assertEquals(mockConnection, connection);

        verify(mockProperties).getProperty("jdbc.driver.class");
        verify(mockProperties).getProperty("jdbc.url");
        verify(mockProperties).getProperty("jdbc.login");
        verify(mockProperties).getProperty("jdbc.password");

        PowerMockito.verifyStatic(DriverManager.class);
        DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "test", "test");
    }
}
