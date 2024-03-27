package fr.openjava.gestionbibliothequejavafx.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
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

        ImageView developer1 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer1.setFitWidth(50);
        developer1.setFitHeight(50);
        Label label1= new Label("test");
        VBox layout1 = new VBox();
        layout1.getChildren().addAll(developer1,label1);

        ImageView developer2 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer2.setFitWidth(50);
        developer2.setFitHeight(50);
        Label label2= new Label("test");
        VBox layout2 = new VBox();
        layout2.getChildren().addAll(developer2,label2);

        ImageView developer3 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer3.setFitWidth(50);
        developer3.setFitHeight(50);
        Label label3= new Label("test");
        VBox layout3 = new VBox();
        layout3.getChildren().addAll(developer3,label3);

        ImageView developer4 = new ImageView(new Image("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg"));
        developer4.setFitWidth(50);
        developer4.setFitHeight(50);
        Label label4= new Label("test");
        VBox layout4 = new VBox();
        layout4.getChildren().addAll(developer4,label4);

        ImageView developer5 = new ImageView(new Image("https://user.oc-static.com/upload/2020/04/03/15859234226006_compo_comprendre_le_web_revemp-01.png"));
        developer5.setFitWidth(50);
        developer5.setFitHeight(50);
        Label label5= new Label("tu est nul");
        VBox layout5 = new VBox();
        layout5.getChildren().addAll(developer5,label5);

        // Créez une boîte horizontale pour organiser les éléments
        HBox layout = new HBox();
        layout.getChildren().addAll(layout1,layout2,layout3,layout4,layout5);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
