package fr.openjava.gestionbibliothequejavafx.DAO;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnexionTest {
    @Test
    void testInitConnexion() {
        Connection conn = Connexion.initConnexion(new Properties());
        assertNotNull(conn, "La connexion ne doit pas être nulle");
    }
}
