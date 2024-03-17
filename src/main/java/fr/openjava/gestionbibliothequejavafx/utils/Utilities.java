package fr.openjava.gestionbibliothequejavafx.utils;

import javafx.scene.control.Alert;

public class Utilities {
    public static String XML_FILE_PATH = "src/main/resources/fr/openjava/gestionbibliothequejavafx/dataSource/Biblio.xml";

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
}
