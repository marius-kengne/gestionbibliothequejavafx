package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.DAO.Connexion;
import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import fr.openjava.gestionbibliothequejavafx.models.User;
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


public class RegisterController {

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label statusLabel;

    private UserDAO userDAO;
    private final Connection connexion = Connexion.initConnexion(new Properties());


    public RegisterController() {
        userDAO = new UserDAO(connexion);
    }

    @FXML
    void onRegisterButtonClicked(ActionEvent event) {
        String lastName = lastNameField.getText();
        String firstName = firstNameField.getText();
        String login = emailField.getText();
        String password = passwordField.getText();

        if (lastName.isEmpty() || firstName.isEmpty() || login.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Veuillez renseigner tous les champs...");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        User newUser = new User();
        newUser.setLastName(lastName);
        newUser.setFirstName(firstName);
        newUser.setLogin(login);
        newUser.setPassword(password);
        newUser.setRole("user");
        newUser.setAdmin(false);

        User createdUser = userDAO.createUser(newUser);
        if (createdUser != null) {
            clearForm();
            statusLabel.setText("Utilisateur créé avec succès !");
            statusLabel.setStyle("-fx-text-fill: green;");

        } else {
            statusLabel.setText("Échec de la création de l'utilisateur !");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void clearForm() {
        lastNameField.clear();
        firstNameField.clear();
        emailField.clear();
        passwordField.clear();
        statusLabel.setText("");
    }

    @FXML
    void onBackToLoginButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 770, 450);
        stage.setScene(scene);
    }
}