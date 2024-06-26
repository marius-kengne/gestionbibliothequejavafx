package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.DAO.Connexion;
import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contrôleur pour la gestion de la connexion des utilisateurs.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private static final Logger logger = Logger.getLogger(LoginController.class.getName());
    private String selectedRole;
    private final Connection connexion = Connexion.initConnexion(new Properties());

    @FXML
    private Label statusLabel;
    /**
     * Méthode appelée lorsque le bouton de connexion est cliqué.
     *
     * @param event l'événement déclenché par le clic sur le bouton de connexion
     */
    @FXML
    public void onLoginButtonClicked(ActionEvent event) {
        String login = usernameField.getText();
        String password = passwordField.getText();

        logger.log(Level.INFO, "Tentative de connexion pour l'utilisateur : {0}", login);

        UserDAO userDao = new UserDAO(connexion);
        User loggedInUser = userDao.login(login, password);
        if (loggedInUser != null) {
            statusLabel.setText("Bienvenue, " + loggedInUser.getLogin() + "!");
            statusLabel.setStyle("-fx-text-fill: green;");

            closeCurrentStage(event);

            selectedRole = loggedInUser.getRole();
            BibliothequeController.setRole(selectedRole);

            try {
                selectMode();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erreur lors de la sélection du mode de travail", e);
            }
        } else {
            statusLabel.setText("Email ou Mot de passe INCORRECT. Veuillez réessayer !");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    /**
     * Sélectionne le mode de travail après une connexion réussie.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    private void selectMode() throws IOException {
        logger.info("Sélection du mode de travail");
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/mode_selection.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Sélection du Mode de Travail");
        stage.setScene(new Scene(fxmlLoader.load(), 400, 450));
        stage.show();
    }

    /**
     * Gestion du clic sur le bouton de création de compte.
     *
     * @param event l'événement déclenché par le clic sur le bouton
     */
    @FXML
    private void onRegisterButtonClicked(ActionEvent event) {
        loadFXML("views/register.fxml", event);
    }

    /**
     * Gestion du clic sur le bouton de réinitialisation de mot de passe.
     *
     * @param event l'événement déclenché par le clic sur le bouton
     */
    @FXML
    private void onForgotPasswordButtonClicked(ActionEvent event) {
        loadFXML("views/forgot_password.fxml", event);
    }

    /**
     * Charge un fichier FXML et affiche la scène correspondante.
     *
     * @param fxmlPath chemin vers le fichier FXML à charger
     * @param event    l'événement déclenché par l'action utilisateur
     */
    private void loadFXML(String fxmlPath, ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erreur lors du chargement de la vue " + fxmlPath, e);
        }
    }

    /**
     * Ferme la fenêtre actuelle.
     *
     * @param event l'événement déclenché par l'action utilisateur
     */
    private void closeCurrentStage(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

}
