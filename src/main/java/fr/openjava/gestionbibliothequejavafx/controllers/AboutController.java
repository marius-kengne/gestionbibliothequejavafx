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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contrôleur pour la fenêtre "À propos" de l'application.
 */
public class AboutController {

    private static final Logger logger = Logger.getLogger(AboutController.class.getName());

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

    /**
     * Liste des noms des contributeurs.
     */
    private final List<String> contributorNames = new ArrayList<>();

    /**
     * Liste des URL des photos des contributeurs.
     */
    private final List<String> contributorUrlPhotos = new ArrayList<>();

    /**
     * Initialise le contrôleur.
     * Configure les noms et les photos des contributeurs.
     */
    public void initialize() {
        try {
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

            loadImage(firstContributor, firstContributorName, contributorUrlPhotos.get(0), contributorNames.get(0));
            loadImage(secondContributor, secondContributorName, contributorUrlPhotos.get(1), contributorNames.get(1));
            loadImage(thirdContributor, thirdContributorName, contributorUrlPhotos.get(2), contributorNames.get(2));
            loadImage(fourContributor, fourContributorName, contributorUrlPhotos.get(3), contributorNames.get(3));
            loadImage(fiveContributor, fiveContributorName, contributorUrlPhotos.get(4), contributorNames.get(4));

            versionLabel.setText("version : v1.2");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erreur lors de l'initialisation du contrôleur À propos", e);
        }
    }

    /**
     * Charge une image à partir de l'URL donnée et met à jour le nom du contributeur.
     *
     * @param imageView  ImageView où charger l'image
     * @param nameLabel  Label où afficher le nom du contributeur
     * @param imageUrl   URL de l'image
     * @param contributorName Nom du contributeur
     */
    private void loadImage(ImageView imageView, Label nameLabel, String imageUrl, String contributorName) {
        try {
            URL url = new URL(imageUrl);
            Image image = new Image(url.openStream());
            imageView.setImage(image);
            nameLabel.setText(contributorName);
        } catch (MalformedURLException e) {
            logger.log(Level.SEVERE, "URL de l'image invalide : " + imageUrl, e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erreur de chargement de l'image : " + imageUrl, e);
        }
    }

    /**
     * Affiche la fenêtre "À propos".
     */
    public static void display() {
        Stage aboutStage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(GestionBibliothequeJavaFX.class.getResource("views/about.fxml"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erreur lors du chargement de la vue 'À propos'", e);
            throw new RuntimeException(e);
        }
        aboutStage.setTitle("À propos");
        aboutStage.setScene(new Scene(root, 600, 350));
        aboutStage.show();
    }
}
