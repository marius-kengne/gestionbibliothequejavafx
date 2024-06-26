package fr.openjava.gestionbibliothequejavafx.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe utilitaire pour gérer la connexion à la base de données.
 */
public class Connexion {

    /**
     * Initialise une connexion à la base de données en utilisant les paramètres
     * de configuration définis dans le fichier 'conf.properties'.
     * @param props fichier de configuration
     * @return une connexion à la base de données, ou null si la connexion échoue
     */
    public static Connection initConnexion(Properties props){

        Connection conn = null;

        try (FileInputStream fis = new FileInputStream("conf.properties")) {
            props.load(fis);
        } catch (IOException e) {
            System.err.println("erreur de configuration du fichier: " + e.getMessage());
        }

        try {
            Class.forName(props.getProperty("jdbc.driver.class"));
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur du driver JDBC : " + e.getMessage());
        }

        String url = props.getProperty("jdbc.url");
        String login = props.getProperty("jdbc.login");
        String password = props.getProperty("jdbc.password");

        try {
            conn = DriverManager.getConnection(url, login, password);
            System.out.println("******  connection à la BD réussie *****");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        return conn;
    }
}
