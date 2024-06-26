package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contrôleur pour la sélection du mode d'utilisation de l'application.
 */
public class ModeController {

    private static final Logger logger = Logger.getLogger(ModeController.class.getName());

    private String selectedMode;

    /**
     * Gère la sélection du mode local.
     *
     * @param event l'événement de l'action
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @FXML
    protected void onLocalModeSelected(ActionEvent event) throws IOException {
        selectedMode = "local";
        redirectToHome(event);
    }

    /**
     * Gère la sélection du mode connecté.
     *
     * @param event l'événement de l'action
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @FXML
    protected void onConnectedModeSelected(ActionEvent event) throws IOException {
        selectedMode = "connected";
        redirectToHome(event);
    }

    /**
     * Redirige vers la page d'accueil en enregistrant le mode sélectionné.
     *
     * @param event l'événement de l'action
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    private void redirectToHome(ActionEvent event) {
        try {
            // Enregistre le mode sélectionné dans une variable statique de BibliothequeController pour une utilisation ultérieure
            BibliothequeController.setMode(selectedMode);

            // Charge la page d'accueil
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/home.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 850);
            currentStage.setTitle("Gestionnaire de la Bibliothèque");
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erreur lors du chargement de la page d'accueil", e);
        }
    }
}
