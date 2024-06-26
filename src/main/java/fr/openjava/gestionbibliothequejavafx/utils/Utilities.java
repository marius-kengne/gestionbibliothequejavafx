package fr.openjava.gestionbibliothequejavafx.utils;

import javafx.scene.control.Alert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

    private static final String CONFIG_FILE = "config.properties";
    private static Properties properties = new Properties();
    public static String XML_FILE_PATH = "src/main/resources/fr/openjava/gestionbibliothequejavafx/dataSource/Biblio.xml";
    public static String XML_BUFFER_FILE_PATH = "buffer.xml";
    public static String URL_DEFAULT = "https://d19rpgkrjeba2z.cloudfront.net/static/images/business/topic-cover/personal-care.png";

    static {
        try (InputStream input = Utilities.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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


    public static void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
            properties.store(output, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean getProperty(String key) {
        return Boolean.getBoolean(properties.getProperty(key));
    }
}
