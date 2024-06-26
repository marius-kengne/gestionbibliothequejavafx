package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.DAO.Connexion;
import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class ResetPasswordController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private Label statusLabel;

    private final UserDAO userDAO;

    public ResetPasswordController() {
        Connection connexion = Connexion.initConnexion(new Properties());
        userDAO = new UserDAO(connexion);
    }

    @FXML
    protected void onResetPasswordButtonClicked() {
        String email = emailField.getText();
        String newPassword = newPasswordField.getText();

        if (email.isEmpty() || newPassword.isEmpty()) {
            statusLabel.setText("Veuillez remplir tous les champs.");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean isUpdated = userDAO.updatePassword(email, newPassword);
        if (isUpdated) {
            statusLabel.setText("Mot de passe réinitialisé avec succès !");
            statusLabel.setStyle("-fx-text-fill: green;");
            clearFields();
        } else {
            statusLabel.setText("Échec de la réinitialisation du mot de passe.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearFields() {
        emailField.clear();
        newPasswordField.clear();
    }

    @FXML
    void onBackToLoginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }

}
