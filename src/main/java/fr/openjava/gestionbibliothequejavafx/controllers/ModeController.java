package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ModeController {

    private String selectedMode;

    @FXML
    protected void onLocalModeSelected(ActionEvent event) throws IOException {
        selectedMode = "local";
        redirectToHome(event);
    }

    @FXML
    protected void onConnectedModeSelected(ActionEvent event) throws IOException {
        selectedMode = "connected";
        redirectToHome(event);
    }

    private void redirectToHome(ActionEvent event) throws IOException {
        // Save selected mode to a static variable in HomeController for later use
        BibliothequeController.setMode(selectedMode);

        // Load home page
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/home.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        currentStage.setTitle("Gestionnaire de la Bibliothèque");
        currentStage.setScene(scene);
        currentStage.show();
    }
}
