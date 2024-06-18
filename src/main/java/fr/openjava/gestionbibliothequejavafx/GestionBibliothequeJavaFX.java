package fr.openjava.gestionbibliothequejavafx;

import fr.openjava.gestionbibliothequejavafx.DAO.UserDAO;
import fr.openjava.gestionbibliothequejavafx.models.User;
import fr.openjava.gestionbibliothequejavafx.utils.Utilities;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class GestionBibliothequeJavaFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*
        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/home.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 850);
        stage.setTitle("Gestionnaire de la Bibliothèque!");
        stage.setScene(scene);
        stage.show();
         */
        //selectMode(stage);
        loginPage(stage);
    }

    public static void main(String[] args) {
        //launch();
        UserDAO userDao = new UserDAO();
        /*
        User newUser = new User();
        newUser.setLogin("testuser");
        newUser.setPassword("password123");
        newUser.setRole("user");

        userDao.createUser(newUser);
        */

        User loggedInUser = userDao.login("testuser", "password123");
        if (loggedInUser != null) {
            System.out.println("Welcome, " + loggedInUser.getLogin() + "!");
        } else {
            System.out.println("Login failed.");
        }

        Application.launch(GestionBibliothequeJavaFX.class);
    }


    public void loginPage(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 400, 350);
        stage.setTitle("Gestionnaire de la Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }
}