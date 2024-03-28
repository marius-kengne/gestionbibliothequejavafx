package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.GestionBibliothequeJavaFX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AboutController {

    @FXML
    private ImageView firstContributor;
    @FXML
    private ImageView secondContributor;
    @FXML
    private ImageView thirdContributor;
    @FXML
    private ImageView fourContributor;
    @FXML
    private ImageView fiveContributor;

    @FXML
    private Label firstContributorName;
    @FXML
    private Label secondContributorName;
    @FXML
    private Label thirdContributorName;
    @FXML
    private Label fourContributorName;
    @FXML
    private Label fiveContributorName;
    @FXML
    private Label versionLabel;

    public List<String> contributorNames = new ArrayList<>();

    public List<String> contributorUrlPhotos = new ArrayList<>();

    public void initialize() {

        contributorNames.add("Marius");
        contributorUrlPhotos.add("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg");
        contributorNames.add("Sofiane");
        contributorUrlPhotos.add("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg");
        contributorNames.add("Fonsa");
        contributorUrlPhotos.add("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg");
        contributorNames.add("Pièrre");
        contributorUrlPhotos.add("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg");
        contributorNames.add("Killyan");
        contributorUrlPhotos.add("https://www.bod.fr/fileadmin/_processed_/2/0/csm_FR-easyCover_Roman-modern-1_d65ba05c25.jpg");

        firstContributor.setImage(new Image(contributorUrlPhotos.get(0)));
        secondContributor.setImage(new Image(contributorUrlPhotos.get(1)));
        thirdContributor.setImage(new Image(contributorUrlPhotos.get(2)));
        fourContributor.setImage(new Image(contributorUrlPhotos.get(3)));
        fiveContributor.setImage(new Image(contributorUrlPhotos.get(4)));

        firstContributorName.setText(contributorNames.get(0));
        secondContributorName.setText(contributorNames.get(1));
        thirdContributorName.setText(contributorNames.get(2));
        fourContributorName.setText(contributorNames.get(3));
        fiveContributorName.setText(contributorNames.get(4));
        versionLabel.setText("version : v1.2");
    }

    public static void display() {
        Stage aboutStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(GestionBibliothequeJavaFX.class.getResource("views/about.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        aboutStage.setTitle("About");
        aboutStage.setScene(new Scene(root, 600, 350));
        aboutStage.show();
    }


}
