package fr.openjava.gestionbibliothequejavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Classe principale de l'application GestionBibliothequeJavaFX.
 * Cette classe étend Application et gère le démarrage de l'application ainsi que l'affichage de la page de connexion.
 */

public class GestionBibliothequeJavaFX extends Application {

    /**
     * Méthode principale démarrant l'application en affichant la page de connexion.
     *
     * @param stage la fenêtre principale de l'application
     * @throws IOException si une erreur survient lors du chargement de la page de connexion
     */
    @Override
    public void start(Stage stage) throws IOException {
        loginPage(stage);
    }

    /**
     * Méthode principale de lancement de l'application.
     * Elle instancie un utilisateur de test, essaie de le connecter et affiche un message en fonction du résultat.
     *
     * @param args les arguments de la ligne de commande (non utilisés ici)
     */
    public static void main(String[] args) {
        Application.launch(GestionBibliothequeJavaFX.class);
    }


    /**
     * Affiche la page de connexion en chargeant le fichier FXML correspondant.
     *
     * @param stage la fenêtre principale de l'application
     * @throws IOException si une erreur survient lors du chargement de la page de connexion depuis le fichier FXML
     */

    public void loginPage(Stage stage) throws IOException {
       FXMLLoader fxmlLoader = new FXMLLoader(GestionBibliothequeJavaFX.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 770, 450);
        stage.setTitle("Gestionnaire de la Bibliothèque");
        stage.setScene(scene);
        stage.show();
    }

}