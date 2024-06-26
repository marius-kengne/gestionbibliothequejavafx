package fr.openjava.gestionbibliothequejavafx.utils;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Utilities {

    private static final Logger logger = Logger.getLogger(Utilities.class.getName());

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();
    public static String XML_FILE_PATH = "src/main/resources/fr/openjava/gestionbibliothequejavafx/dataSource/Biblio.xml";
    public static String URL_DEFAULT = "https://d19rpgkrjeba2z.cloudfront.net/static/images/business/topic-cover/personal-care.png";

    static {
        try (InputStream input = Utilities.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ex) {
            logger.info(ex.getMessage());
        }
    }

    /**
     * Afficher les messages d'erreur de validationn du formulaire
     * @param title : le titre de la boite de diagogue,
     * @param message : le message d'erreur
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showAlertSuccess(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static boolean getProperty(String key) {
        return Boolean.getBoolean(properties.getProperty(key));
    }

}
