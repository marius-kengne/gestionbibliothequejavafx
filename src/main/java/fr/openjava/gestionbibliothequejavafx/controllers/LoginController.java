package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.utils.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void onLoginButtonClicked(ActionEvent event) throws IOException {
        String login = usernameField.getText();
        String password = passwordField.getText();

        // Authentication logic here (e.g., check username and password)
        System.out.println("Username: " + login);
        System.out.println("Password: " + password);

        UserDAO userDao = new UserDAO();
        User loggedInUser = userDao.login(login, password);
        if (loggedInUser != null) {
            System.out.println("Welcome, " + loggedInUser.getLogin() + "!");
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            selectMode();
        } else {
            System.out.println("Login failed.");
        }

    }

    public void selectMode() throws IOException {
        System.out.println("########### mode de travail  : " + Utilities.getProperty("mode.connected"));
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/mode_selection.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load(), 400, 450);
        stage.setTitle("Selection du Mode de Travail");
        stage.setScene(scene);
        stage.show();
    }

}
