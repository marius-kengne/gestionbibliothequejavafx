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

/**
 * Contrôleur pour la gestion de la connexion des utilisateurs.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private String selectedRole;
    private final Connection connexion = Connexion.initConnexion(new Properties());

    /**
     * Méthode appelée lorsque le bouton de connexion est cliqué.
     *
     * @param event l'événement déclenché par le clic sur le bouton de connexion
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    @FXML
    private Label statusLabel;

    @FXML
    protected void onLoginButtonClicked(ActionEvent event) throws IOException {
        String login = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Username: " + login);
        System.out.println("Password: " + password);

        UserDAO userDao = new UserDAO(connexion);
        User loggedInUser = userDao.login(login, password);
        if (loggedInUser != null) {
            //System.out.println("Welcome, " + loggedInUser.getLogin() + "!");
            statusLabel.setText("Bienvenue, " + loggedInUser.getLogin() + "!");
            statusLabel.setStyle("-fx-text-fill: green;");
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            selectedRole = loggedInUser.getRole();
            BibliothequeController.setRole(selectedRole);
            selectMode();
        } else {
            //System.out.println("Login failed.");
            statusLabel.setText("Email ou Mot de passe INCORRECT. Veuillez reessayer !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
    }


    /**
     * Sélectionne le mode de travail après une connexion réussie.
     *
     * @throws IOException si une erreur d'entrée/sortie se produit
     */
    public void selectMode() throws IOException {
        System.out.println("########### mode de travail  : " + Utilities.getProperty("mode.connected"));
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/mode_selection.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 400, 450);
        stage.setTitle("Sélection du Mode de Travail");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void onRegisterButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }


}

