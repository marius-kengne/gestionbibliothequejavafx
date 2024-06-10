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
        contributorUrlPhotos.add("https://cdn.discordapp.com/attachments/1220296723574951957/1222934659965653083/92979836-icone-de-visage-anonyme-de-profil-personne-silhouette-grise-avatar-par-defaut-masculin-photo.jpg?ex=661805a7&is=660590a7&hm=f92190367b9b79fe2d6e9d32854a51a576df56eb23db4c4b1e6f44b602deb8aa&");
        contributorNames.add("Sofiane");
        contributorUrlPhotos.add("https://cdn.discordapp.com/attachments/1220296723574951957/1222934659965653083/92979836-icone-de-visage-anonyme-de-profil-personne-silhouette-grise-avatar-par-defaut-masculin-photo.jpg?ex=661805a7&is=660590a7&hm=f92190367b9b79fe2d6e9d32854a51a576df56eb23db4c4b1e6f44b602deb8aa&");
        contributorNames.add("Fonsa");
        contributorUrlPhotos.add("https://cdn.discordapp.com/attachments/1167057421450100807/1222933921407303730/20210105_153246.jpg?ex=661804f7&is=66058ff7&hm=52c51c430ebca5ca9b2c5e134d5e8fa230e1cc3a59b1dff1524720c5c8c0dd42&");
        contributorNames.add("Pierre");
        contributorUrlPhotos.add("https://cdn.discordapp.com/attachments/1220296723574951957/1222934659965653083/92979836-icone-de-visage-anonyme-de-profil-personne-silhouette-grise-avatar-par-defaut-masculin-photo.jpg?ex=661805a7&is=660590a7&hm=f92190367b9b79fe2d6e9d32854a51a576df56eb23db4c4b1e6f44b602deb8aa&");
        contributorNames.add("Killyan");
        contributorUrlPhotos.add("https://cdn.discordapp.com/attachments/1197539327941554277/1222933745691005041/20240328_164135.jpg?ex=661804cd&is=66058fcd&hm=26c814f8b765c83763bd17fae88f2623930368ab0173d59e905f87cd146cf731&");

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
