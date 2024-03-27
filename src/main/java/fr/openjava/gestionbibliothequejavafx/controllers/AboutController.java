package fr.openjava.gestionbibliothequejavafx.controllers;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutController {
    public static void display() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("About");

        // Créez des ImageView pour chaque photo de développeur

        ImageView developer1 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer1.setFitWidth(50);
        developer1.setFitHeight(50);
        ImageView developer2 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer2.setFitWidth(50);
        developer2.setFitHeight(50);

        // Créez une boîte horizontale pour afficher les photos des développeurs
        HBox developersBox = new HBox();
        developersBox.getChildren().addAll(developer1, developer2); // Ajoutez les autres ImageView ici

        // Créez une boîte verticale pour organiser les éléments
        VBox layout = new VBox(10);

        layout.getChildren().addAll(developersBox);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
