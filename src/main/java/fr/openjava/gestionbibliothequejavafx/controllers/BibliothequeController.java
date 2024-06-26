package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.DAO.BibliothequeDAO;
import fr.openjava.gestionbibliothequejavafx.DAO.Connexion;
import fr.openjava.gestionbibliothequejavafx.models.generated.ObjectFactory;
import fr.openjava.gestionbibliothequejavafx.utils.Utilities;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.stage.Stage;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.stage.FileChooser;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cette classe de controller permet de gérer toutes les fonctionnalités de notre application
 * notament la création d'un livre, l'édition d'un livre, la suppression d'un livre, l'enregistrement du livre
 * et la mise à jour d'un livre
 */
public class BibliothequeController {

    @FXML
    private TableView<Bibliotheque.Livre> tableView = null;
    private final Properties props = new Properties();
    private static final Logger logger = Logger.getLogger(BibliothequeController.class.getName());
    @FXML
    private TableColumn<Bibliotheque.Livre, String> titreColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> auteurColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> presentationColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> parutionColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> colonneColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, Integer> rangeeColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> imageColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> resumeColumn;

    @FXML
    private TableColumn<Bibliotheque.Livre, String> statusColumn;

    private ObservableList<Bibliotheque.Livre> livres = FXCollections.observableArrayList();

    private List<Bibliotheque.Livre> allCurrentLivre = new ArrayList<>();
    @FXML
    private TextArea titreTextArea;

    @FXML
    private TextArea auteurTextArea;

    @FXML
    private TextArea presentationTextArea;

    @FXML
    private TextArea parutionTextArea;

    @FXML
    private TextArea colonneTextArea;

    @FXML
    private TextArea rangeeTextArea;

    @FXML
    private TextArea imageTextArea;

    @FXML
    private ImageView imageView;

    @FXML
    private TextArea resumeTextArea;
    private static final String ACTION_MESSAGE = "Erreur lors du chargement de la vue";

    @FXML
    private SplitMenuButton splitMenuButton;

    private String statusCurrentLivre = "";

    private static String mode = "";
    private static String role = "";
    private final Connection connexion = Connexion.initConnexion(new Properties());
    /**
     * Définit le mode actuel.
     *
     * @param selectedMode le mode sélectionné
     */
    public static void setMode(String selectedMode) {
        mode = selectedMode;
    }

    /**
     * Retourne le mode actuel.
     *
     * @return le mode actuel
     */
    public static String getMode() {
        return mode;
    }


    /**
     * Définit le rôle actuel.
     *
     * @param selectedRole le rôle sélectionné
     */
    public static void setRole(String selectedRole) {
        role = selectedRole;
    }

    /**
     * Retourne le rôle actuel.
     *
     * @return le rôle actuel
     */
    public static String getRole() {
        return role;
    }

    @FXML
    private Label modeLabel;
    
    @FXML
    private GridPane editgridpane;
    @FXML
    private HBox bteditandsupr;
    @FXML
    private Label addnewbook;
    @FXML
    private Menu editionmenu;

    @FXML
    public boolean hide(){
        boolean stt=true;
        try {
            try {
                editgridpane.setVisible(false);
                editgridpane.setManaged(false);
                stt=stt&editgridpane.isVisible()&&editgridpane.isManaged();
            } catch (Exception e) {
                logger.log(Level.SEVERE, ACTION_MESSAGE, e);
            }
            try {
                bteditandsupr.setVisible(false);
                bteditandsupr.setManaged(false);
                stt=stt&bteditandsupr.isVisible()&&bteditandsupr.isManaged();
            } catch (Exception e) {
                logger.log(Level.SEVERE, ACTION_MESSAGE, e);
            }
            try {
                addnewbook.setVisible(false);
                addnewbook.setManaged(false);
                stt=stt&addnewbook.isVisible()&&addnewbook.isManaged();
            } catch (Exception e) {
                logger.log(Level.SEVERE, ACTION_MESSAGE, e);
            }
            try {
                editionmenu.setVisible(false);
                stt=stt&editionmenu.isVisible();
            } catch (Exception e) {
                logger.log(Level.SEVERE, ACTION_MESSAGE, e);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return stt;
    }

    /**
     * Constructeur par défaut de BibliothequeController.
     */
    public BibliothequeController(){
        if(getRole().equals("admin")){logger.info("in admin");} else {
            logger.log(Level.SEVERE, "not in admin ");
        }
    }

    /**
     * Méthode pour initialiser le contenu des colones du tableau avec les données
     * présent dans le fichier XML initial
     * C'est cette méthode qui reflete le listing des livres via l'interface
     */
    public void initialize() {
        modeLabel.setText("Mode: " + mode);
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurColumn.setCellValueFactory(cellData -> {
            Bibliotheque.Livre livre = cellData.getValue();
            Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
            return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
        });
        presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));
        resumeColumn.setCellValueFactory(new PropertyValueFactory<>("resume"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        editLivreListener();
        setImageUrl();

        imageTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                imageView.setImage(new Image(newValue));
                imageView.setFitWidth(120);
                imageView.setFitHeight(100);
            }
        });

        for (MenuItem item : splitMenuButton.getItems()) {
            item.setOnAction(event -> {
                String status = ((MenuItem) event.getSource()).getText();
                splitMenuButton.setText(status);
                statusCurrentLivre = status;
                handleSelection(status);
            });
        }

        if(!getRole().equals("admin")){logger.info((!hide())?("les éléments sont masqués"):("les éléments ne sont pas masqués"));}
    }

    // Méthode pour gérer la sélection
    private void handleSelection(String selectedText) {
        if (selectedText.equals("disponible")) {
            logger.info("Article disponible");
        } else if (selectedText.equals("indisponible")) {
            logger.info("Article indisponible");
        }
    }

    /**
     * Méthode pour récupérer les données du fichier xml
     */
    private void chargerDonneesDuXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(new File(Utilities.XML_FILE_PATH));
            livres.addAll(bibliotheque.getLivre());
            tableView.setItems(livres);
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "not in admin ", e);
        }
    }

    /**
     * Methode pour remplacer l'url de l'image par l'image proprement dite
     */
    private void setImageUrl(){
        ImageView imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        imageColumn.setCellFactory(column -> new TableCell<Bibliotheque.Livre, String>() {

            @Override
            protected void updateItem(String url, boolean empty) {
                super.updateItem(url, empty);

                if (empty || url == null) {
                    setGraphic(null);
                } else {
                    if (url.isEmpty()){
                        Image image = new Image(Utilities.URL_DEFAULT);
                        imageView.setImage(image);
                        setGraphic(imageView);
                    }else {
                        try {
                            Image image = new Image(url);
                            imageView.setImage(image);
                            setGraphic(imageView);
                        }catch (Exception e){
                            Utilities.showAlert("Erreur", "L'url de l'image n'est pas valide");
                        }

                    }
                }
            }
        });
    }

    /**
     * Méthode pour récupérer les données du livre du formulaire et
     * l' enregistrer dans le fichier XML
     */
    public void saveLivre() {
        //Enregistrer le livre courant au fichier XML
        addLivreToXML();
    }

    /**
     * Méthode pour enregistrer les livres dans un autres fichier
     */
    public void saveInOtherLocation() {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer L'ensemble des livres de la Bibliothèque sous");

        // Ajouter une extension de fichier par défaut (facultatif)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers texte (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue et obtenir l'emplacement du fichier
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            createXMLFileWithLivre(file.getAbsolutePath());
            logger.log(Level.SEVERE, "Fichier enregistré à : ", file.getAbsolutePath());
        }

    }





    /**
     * Méthode pour importer un fichier contenant les livres dans l'application
     */
    public void ImportXMLFile() {

        if(getMode().equals("local")){

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choisir le fichier que vous souhaitez ouvrir");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers XML", "*.xml"));
            File file = fileChooser.showOpenDialog(tableView.getScene().getWindow());
            if (file != null) {
                imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
                titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
                auteurColumn.setCellValueFactory(cellData -> {
                    Bibliotheque.Livre livre = cellData.getValue();
                    Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
                    return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
                });
                presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
                parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
                colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
                rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));
                resumeColumn.setCellValueFactory(new PropertyValueFactory<>("resume"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

                try {
                    JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(new File(file.getAbsolutePath()));
                    livres.clear();
                    livres.addAll(bibliotheque.getLivre());
                    allCurrentLivre.clear();
                    allCurrentLivre.addAll(livres);
                    tableView.setItems(livres);
                    Utilities.showAlertSuccess("Confirmation", "Fichier importer avec success");
                } catch (JAXBException e) {
                    logger.log(Level.SEVERE, "erreur : ", e);
                }

                editLivreListener();
                setImageUrl();
            }

        }
        if(getMode().equals("connected")){

            try {
                imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
                titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
                auteurColumn.setCellValueFactory(cellData -> {
                    Bibliotheque.Livre livre = cellData.getValue();
                    Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
                    return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
                });
                presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
                parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
                colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
                rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));
                resumeColumn.setCellValueFactory(new PropertyValueFactory<>("resume"));
                statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            } catch (Exception e) {
                logger.info("erreur d'initiation de la structure du tableau:\n"+e);
            }

            
            String query = "SELECT * FROM `livres`";
            Object[] idresult = new Object[10];

            try(PreparedStatement result = connexion.prepareStatement(query)) {
                try (ResultSet rs=result.executeQuery()) {
                    livres.clear();
                    allCurrentLivre.clear();
                    while(true){
                        if (rs.next()) {
                            idresult[0]=rs.getInt("id");
                            idresult[7]=rs.getString("image");
                            idresult[1]=rs.getString("titre");
                            idresult[2]=rs.getInt("auteur_id");
                            idresult[3]=rs.getString("presentation");
                            idresult[4]=rs.getInt("parution");
                            idresult[5]=rs.getInt("colonne");
                            idresult[6]=rs.getInt("rangee");
                            idresult[8]=rs.getString("resume");
                            idresult[9]=rs.getString("status");
                            
                            String query2 = "SELECT * FROM `auteurs` WHERE `id` LIKE ?",nom="",prenom="";
                            try (PreparedStatement result2 = connexion.prepareStatement(query2)) {
                                result2.setInt(1, Integer.parseInt(""+idresult[2]));
                                try (ResultSet rs2 = result2.executeQuery()) {
                                    if (rs2.next()) {
                                        nom = rs2.getString("nom");
                                        prenom = rs2.getString("prenom");
                                    } else {
                                        logger.info("Check auteur null!");
                                    }
                                }
                            }
                            catch (Exception f) {
                                logger.info("erreur de la query de récupération du nom et prénom de l'auteur!\n" + f);
                            }

                            try {

                                ObservableList<Object> tableauDeLivres2 = FXCollections.observableArrayList();
                                Bibliotheque.Livre livre2 = new Bibliotheque.Livre();
                                Bibliotheque.Livre.Auteur auteur2 = new Bibliotheque.Livre.Auteur();
                                livre2.setImage((String) idresult[7]);
                                livre2.setTitre((String) idresult[1]);
                                auteur2.setNom(nom);
                                auteur2.setPrenom(prenom);
                                livre2.setAuteur(auteur2);
                                livre2.setPresentation((String) idresult[3]);
                                livre2.setParution((int) idresult[4]);
                                livre2.setColonne(Short.parseShort(idresult[5].toString()));
                                livre2.setRangee(Short.parseShort(idresult[6].toString()));
                                livre2.setResume((String) idresult[8]);
                                livre2.setStatus((String) idresult[9]);
                                List<Bibliotheque.Livre> livres2 = Collections.singletonList(livre2);
                                if (!livres2.isEmpty()) {
                                    Bibliotheque.Livre livreAjoute2 = livres2.get(0);
                                    tableauDeLivres2.add(livreAjoute2);
                                    logger.info(livreAjoute2.toString());
                                    livres.add(livres2.get(0));
                                    allCurrentLivre.add(livres2.get(0));
                                    tableView.setItems(livres);
                                }
                                else {
                                    logger.info("La liste des livres est vide.");
                                }

                            }catch (Exception f) {
                                logger.info("erreur d'ajout des livres dans le tableaux:\n"+f);
                            }

                            editLivreListener();
                            setImageUrl();

                        }else{
                            logger.info("ajout des livres terminé!");
                            Utilities.showAlertSuccess("Confirmation", "Fichier importer avec success");
                            break;
                        }
                    }
                }
            }
            catch(Exception e){
                logger.info("echec de l'ajout de la liste des livres:\n->"+e);
            }

        }
    }

    /**
     * Retourne une liste observable de livres.
     * @param filePath fichier xml
     * @return la liste des livres dans une collection observable
     */
    public ObservableList<Bibliotheque.Livre> getLivresInXML(String filePath) {
        try {
            // Créer le contexte JAXB
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Charger le fichier XML et obtenir la liste des livres
            File file = new File(filePath);
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(file);
            return FXCollections.observableArrayList(bibliotheque.getLivre());
        } catch (JAXBException e) {
            return FXCollections.observableArrayList();
        }
    }


    /**
     * Méthode pour ajouter un livre dans le tableau sans l'enregistrer dans le fichier XML
     */
    public void addLivreToTabview(){

        Bibliotheque.Livre currentLivre = new Bibliotheque.Livre();
        LocalDate currentDate = LocalDate.now();

        if (titreTextArea.getText().isEmpty() || auteurTextArea.getText().isEmpty() || presentationTextArea.getText().isEmpty() || rangeeTextArea.getText().isEmpty() || colonneTextArea.getText().isEmpty() || parutionTextArea.getText().isEmpty() ){
            Utilities.showAlert("Erreur de validation", "Tous les champs sont obligatoires !");
        }else {
            String titre = titreTextArea.getText();
            String[] auteur = auteurTextArea.getText().split(" ");
            if (auteur.length == 1){
                Utilities.showAlert("Erreur de validation", "Vous devez saisir le nom suivi du prénom de l'auteur");
            }
            String presentation = presentationTextArea.getText();

            try {
                int parution = Integer.parseInt(parutionTextArea.getText());
                if (!(parution <= currentDate.getYear())){
                    Utilities.showAlert("Erreur de validation", "L'année de parution ne peut pas être supérieur à l'année du jour");
                    return;
                }
                currentLivre.setParution(parution);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "L'année de parution doit être une année valide !");
                return;
            }

            try {
                int colonne = Integer.parseInt(colonneTextArea.getText());
                if (!(colonne <= 7 && colonne >= 0)){
                    Utilities.showAlert("Erreur de validation", "La colonne doit avoir une valeur minimum 0 et valeur maximum 7");
                    return;
                }
                currentLivre.setColonne((short) colonne);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La colonne doit être un nombre valide !");
                return;
            }

            try {
                int rangee = Integer.parseInt(rangeeTextArea.getText());
                if (!(rangee <= 5 && rangee >= 1)){
                    Utilities.showAlert("Erreur de validation", "La rangée doit avoir une valeur minimum 1 et valeur maximum 5");
                    return;
                }
                currentLivre.setRangee((short) rangee);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La rangée doit être un nombre valide !");
                return;
            }

            try {
                String resume = resumeTextArea.getText();
                if (resume.isEmpty()){
                    Utilities.showAlert("Erreur de validation", "Le résumé doit être rempli");
                    return;
                }
                currentLivre.setResume(resume);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "Le résumé doit être rempli");
                return;
            }

            try {
                String status = statusCurrentLivre;
                if (status.isEmpty()){
                    Utilities.showAlert("Erreur de validation", "Le status doit être rempli");
                    return;
                }
                currentLivre.setStatus(status);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "Le status doit être rempli");
                return;
            }

            //Créer un nouvel objet Livre et l'ajouter dans le tableau

            if (! imageTextArea.getText().isEmpty()){
                currentLivre.setImage(imageTextArea.getText());
            }
            currentLivre.setTitre(titre);
            Bibliotheque.Livre.Auteur currentAuteur = new Bibliotheque.Livre.Auteur();
            currentAuteur.setNom(auteur[0]);
            currentAuteur.setPrenom(auteur[1]);
            currentLivre.setAuteur(currentAuteur);
            currentLivre.setPresentation(presentation);

            if (! allCurrentLivre.contains(currentLivre)){
                allCurrentLivre.add(currentLivre);
            }
            livres.add(currentLivre);
            tableView.setItems(livres);
            clearForm();
        }

    }


    /**
     * Méthode pour supprimer un livre du tableau  et dans le fichier XML
     */
    public void deleteLivre(){
        // Obtenir la ligne sélectionnée
        Bibliotheque.Livre currentLivre = tableView.getSelectionModel().getSelectedItem();
        if (currentLivre != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de Suppression");
            alert.setHeaderText("Voulez-vous vraiment supprimer ce livre ?");
            alert.setContentText(currentLivre.toString());

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Supprimer l'objet de la liste
                ObservableList<Bibliotheque.Livre> allLivres = tableView.getItems();
                allLivres.remove(currentLivre);

                // Supprimer l'objet du fichier XML
                if (allCurrentLivre.contains(currentLivre)){
                    allCurrentLivre.remove(currentLivre);
                    Utilities.showAlertSuccess("Confirmation", "Le livre a été supprimé");
                }
                clearForm();
                deleteLivreToXML(currentLivre);
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Aucun livre selectionné");
            alert.showAndWait();
        }

    }


    /**
     * Méthode pour supprimer un livre dans le fichier XML
     */
    private void deleteLivreToXML(Bibliotheque.Livre currentLivre) {

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque currentBibliotheque;
            if (file.exists()) {
                currentBibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                currentBibliotheque = new ObjectFactory().createBibliotheque();
            }

            Iterator<Bibliotheque.Livre> iterator = currentBibliotheque.getLivre().iterator();
            while (iterator.hasNext()) {
                Bibliotheque.Livre e = iterator.next();
                if (e.getTitre().equalsIgnoreCase(currentLivre.getTitre())
                        && e.getAuteur().getNom().equalsIgnoreCase(currentLivre.getAuteur().getNom())
                        && e.getAuteur().getPrenom().equals(currentLivre.getAuteur().getPrenom())
                        && e.getParution() == currentLivre.getParution()) {
                    iterator.remove();
                }else {
                    logger.info("################## currentLivre : " + e.toString());
                }
            }

            marshaller.marshal(currentBibliotheque, file);
            Utilities.showAlertSuccess("Confirmation", "Le livre a bien été supprimé");

        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Erreur :", e);
        }
    }

    /**
     * Méthode permettant d'ajouter un livre dans le fichier XML
     */
    private void addLivreToXML() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            // Ajouter le livre à la liste des livres
            Iterator<Bibliotheque.Livre> iterator = bibliotheque.getLivre().iterator();
            List<Bibliotheque.Livre> copyAllCurrentLivre = new ArrayList<>();
            copyAllCurrentLivre.addAll(allCurrentLivre);
            Iterator<Bibliotheque.Livre> iterator2 = copyAllCurrentLivre.iterator();
            while (iterator2.hasNext()) {
                Bibliotheque.Livre thelivre = iterator2.next();

                while (iterator.hasNext()) {
                    Bibliotheque.Livre e = iterator.next();
                    if ((e.getTitre().equalsIgnoreCase(thelivre.getTitre())
                            && e.getAuteur().getNom().equalsIgnoreCase(thelivre.getAuteur().getNom())
                            && e.getAuteur().getPrenom().equals(thelivre.getAuteur().getPrenom())
                            && e.getParution() == thelivre.getParution())) {

                        iterator2.remove();
                        livres.remove(iterator2);
                        tableView.setItems(livres);
                        Utilities.showAlert("Echec d'enregistrement","Vous avez deux livres avec des informations identiques !");
                        return;

                    }
                }
            }

            bibliotheque.getLivre().addAll(allCurrentLivre);

            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, file);
            Utilities.showAlertSuccess("Confirmation", "Le livre a bien été enregistré");
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Erreur :", e);
        }
    }


    /**
     * Méthode permettant de mettre à jour un livre dans le fichier XML
     */
    public void updateLivreToXML() {

        Bibliotheque.Livre currentLivre = new Bibliotheque.Livre();
        LocalDate currentDate = LocalDate.now();

        if (titreTextArea.getText().isEmpty() || auteurTextArea.getText().isEmpty() || presentationTextArea.getText().isEmpty() || rangeeTextArea.getText().isEmpty() || colonneTextArea.getText().isEmpty() || parutionTextArea.getText().isEmpty() ){
            Utilities.showAlert("Erreur de validation", "Tous les champs sont obligatoires !");
            return;
        }else {
            String titre = titreTextArea.getText();
            String[] auteur = auteurTextArea.getText().split(" ");
            if (auteur.length == 1){
                Utilities.showAlert("Erreur de validation", "Vous devez saisir le nom suivi du prénom de l'auteur");
                return;
            }
            String presentation = presentationTextArea.getText();

            try {
                int parution = Integer.parseInt(parutionTextArea.getText());
                if (!(parution <= currentDate.getYear())){
                    Utilities.showAlert("Erreur de validation", "L'année de parution ne peut pas être supérieur à l'année du jour");
                    return;
                }
                currentLivre.setParution(parution);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "L'année de parution doit être une année valide !");
                return;
            }

            try {
                int colonne = Integer.parseInt(colonneTextArea.getText());
                if (!(colonne <= 7 && colonne >= 0)){
                    Utilities.showAlert("Erreur de validation", "La colonne doit avoir une valeur minimum 0 et valeur maximum 7");
                    return;
                }
                currentLivre.setColonne((short) colonne);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La colonne doit être un nombre valide !");
                return;
            }

            try {
                int rangee = Integer.parseInt(rangeeTextArea.getText());
                if (!(rangee <= 5 && rangee >= 1)){
                    Utilities.showAlert("Erreur de validation", "La rangée doit avoir une valeur minimum 1 et valeur maximum 5");
                    return;
                }
                currentLivre.setRangee((short) rangee);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "La rangée doit être un nombre valide !");
                return;
            }

            try {
                String resume = resumeTextArea.getText();
                if (resume.isEmpty()){
                    Utilities.showAlert("Erreur de validation", "Le résumé doit être rempli");
                    return;
                }
                currentLivre.setResume(resume);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "Le résumé doit être rempli");
                return;
            }

            try {
                String status = statusCurrentLivre;
                if (status.isEmpty()){
                    Utilities.showAlert("Erreur de validation", "Le status doit être rempli");
                    return;
                }
                currentLivre.setStatus(status);
            } catch (NumberFormatException e) {
                Utilities.showAlert("Erreur de validation", "Le status doit être rempli");
                return;
            }

            // Créer un nouvel objet Livre et l'ajouter dans le tableau

            if (imageTextArea.getText() != null && ! imageTextArea.getText().isEmpty()){
                currentLivre.setImage(imageTextArea.getText());
            }
            currentLivre.setTitre(titre);
            Bibliotheque.Livre.Auteur currentAuteur = new Bibliotheque.Livre.Auteur();
            currentAuteur.setNom(auteur[0]);
            currentAuteur.setPrenom(auteur[1]);
            currentLivre.setAuteur(currentAuteur);
            currentLivre.setPresentation(presentation);

        }

        // Mise à jour du fichier XML

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }

            // Ajouter le livre à la liste des livres
            //Iterator<Bibliotheque.Livre> iterator = bibliotheque.getLivre().iterator();
            Iterator<Bibliotheque.Livre> iterator = allCurrentLivre.iterator();
            List<Bibliotheque.Livre> copyAllCurrentLivre = new ArrayList<>();
            //copyAllCurrentLivre.addAll(allCurrentLivre);

            while (iterator.hasNext()) {
                Bibliotheque.Livre e = iterator.next();
                if ((e.getTitre().equalsIgnoreCase(currentLivre.getTitre())
                        && e.getAuteur().getNom().equalsIgnoreCase(currentLivre.getAuteur().getNom())
                        && e.getAuteur().getPrenom().equals(currentLivre.getAuteur().getPrenom())
                        && e.getParution() == currentLivre.getParution())) {
                    copyAllCurrentLivre.add(currentLivre);
                } else {
                    copyAllCurrentLivre.add(e);
                }
            }

            bibliotheque.getLivre().clear();
            bibliotheque.getLivre().addAll(copyAllCurrentLivre);

            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, file);
            Utilities.showAlertSuccess("Confirmation", "Le livre a bien été mise à jour");
            //reloadDataToXML();
            livres.clear();
            livres.addAll(copyAllCurrentLivre);
            tableView.setItems(livres);
            clearForm();
        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Erreur :", e);
        }
    }


    /**
     * Recharger les données du fichier XML pour être pris en compte
     * dans la vue de listing des livres
     */
    public void reloadDataToXML(){
        livres.clear();
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        auteurColumn.setCellValueFactory(cellData -> {
            Bibliotheque.Livre livre = cellData.getValue();
            Bibliotheque.Livre.Auteur auteur = livre.getAuteur();
            return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
        });
        presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));

        chargerDonneesDuXML();
    }

    /**
     * Méthode pour recuperer les valeurs des champs d'un livre lors de sa selection
     * et l'afficher dans le formulaire
     */
    public void editLivreListener() {
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez les valeurs sélectionnées et affichez-les dans les champs de texte
                imageTextArea.setText(newSelection.getImage());
                titreTextArea.setText(newSelection.getTitre());
                Bibliotheque.Livre.Auteur auteur = newSelection.getAuteur();
                auteurTextArea.setText(auteur.getNom().concat(" ").concat(auteur.getPrenom()));
                presentationTextArea.setText(newSelection.getPresentation());
                parutionTextArea.setText(Integer.toString(newSelection.getParution()));
                colonneTextArea.setText(Short.toString(newSelection.getColonne()));
                rangeeTextArea.setText(Short.toString(newSelection.getRangee()));
                resumeTextArea.setText(newSelection.getResume());
                statusCurrentLivre = newSelection.getStatus();

                splitMenuButton.setText(statusCurrentLivre);
                //statusTextArea.setText(newSelection.getStatus());
            }
        });
    }

    /**
     * Méthode pour créer un nouveau fichier XML et enregistrer les livres
     */
    private void createXMLFileWithLivre(String pathOfFile) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            // Ajouter le livre à la liste des livres
            Iterator<Bibliotheque.Livre> iterator = bibliotheque.getLivre().iterator();
            List<Bibliotheque.Livre> copyAllCurrentLivre = new ArrayList<>();
            copyAllCurrentLivre.addAll(allCurrentLivre);
            Iterator<Bibliotheque.Livre> iterator2 = copyAllCurrentLivre.iterator();
            while (iterator2.hasNext()) {
                Bibliotheque.Livre thelivre = iterator2.next();

                while (iterator.hasNext()) {
                    Bibliotheque.Livre e = iterator.next();
                    if ((e.getTitre().equalsIgnoreCase(thelivre.getTitre())
                            && e.getAuteur().getNom().equalsIgnoreCase(thelivre.getAuteur().getNom())
                            && e.getAuteur().getPrenom().equals(thelivre.getAuteur().getPrenom())
                            && e.getParution() == thelivre.getParution())) {

                        iterator2.remove();
                        livres.remove(iterator2);

                        //Utilities.showAlert("Echec d'enregistrement","Vous avez deux livres avec des informations identiques !");
                        //return;

                    }
                }
            }
            tableView.setItems(livres);

            bibliotheque.getLivre().addAll(allCurrentLivre);
            File currentFile = new File(pathOfFile);
            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, currentFile);
            Utilities.showAlertSuccess("Confirmation", "Le fichier a bien été enregistré");

        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Erreur :", e);
        }

    }


    /**
     * Méthode pour exporter les données dans un fichier Word.
     */
    public void ExportWordFileOld(){
        if (tableView == null) {
            logger.info("Erreur: le tableau des livres est vide.");
        }

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez le dossier pour enregistrer votre fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Word", "*.docx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (XWPFDocument document = new XWPFDocument();
                 FileOutputStream out = new FileOutputStream(file)) {

                XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                XWPFParagraph headerParagraph = header.createParagraph();
                XWPFRun headerRun = headerParagraph.createRun();
                //headerRun.setText("Entête");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date exportDate = new Date();
                headerRun.setText("Entête du document - Date d'exportation : " + dateFormat.format(exportDate));

                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("Bienvenue dans notre Gestionnaire de Bibliothèque. Nous vous présentons nore sommaire.");

                document.createParagraph().createRun().addBreak();
                XWPFParagraph coverPageParagraph = document.createParagraph();
                coverPageParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun coverPageRun = coverPageParagraph.createRun();
                coverPageRun.setBold(true);
                coverPageRun.setFontSize(16);
                coverPageRun.setText("Sommaire");

                document.createParagraph().createRun().addBreak();
                XWPFParagraph tableOfContentParagraph = document.createParagraph();
                XWPFRun tableOfContentRun = tableOfContentParagraph.createRun();
                tableOfContentRun.setBold(true);
                tableOfContentRun.setFontSize(14);
                tableOfContentRun.setText("La liste des livres de notre Bibliothèque:");


                ObservableList<Bibliotheque.Livre> livres = tableView.getItems();
                XWPFTable gridPaneTable = document.createTable();
                for (Bibliotheque.Livre livre : livres) {
                    XWPFTableRow row = gridPaneTable.createRow();
                    XWPFTableCell cell = row.createCell();
                    /** * Concaténer les données de chaque livre */
                    String bookData = "Titre: " + livre.getTitre() + "\n" +
                            "Auteur: " + livre.getAuteur().getNom() + " " + livre.getAuteur().getPrenom() + "\n" +
                            "Présentation: " + livre.getPresentation() + "\n" +
                            "Parution: " + livre.getParution() + "\n" +
                            "Colonne: " + livre.getColonne() + "\n" +
                            "Rangée: " + livre.getRangee();
                    cell.setText(bookData);
                }

                document.write(out);
                logger.info("Fichier Word exporté avec succès !");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Erreur :", e);
            }
        }
    }

    /**
     >>>>>>> 6114c6de1078b930d89b9b52760e139140c3bc37
     * méthode pour quitter l'application
     */
    public void quitApplication(){
        allCurrentLivre.clear();
        System.exit(0);
    }

    /**
     *  Méthode pour afficher les informations de l'application
     */
    @FXML
    private void aboutAppplication(){
        AboutController.display();
    }

    /**
     * Methode pour afficher la fonctionnalité Export avec le sommaire
     */
    public void ExportWordFile(){
        ExportController exportController = new ExportController(allCurrentLivre);
        exportController.exportToWord();
    }

    /**
     * Methode pour vider le contenu des champs du formulaire
     */
    public void clearForm(){
        titreTextArea.clear();
        auteurTextArea.clear();
        presentationTextArea.clear();
        parutionTextArea.clear();
        colonneTextArea.clear();
        rangeeTextArea.clear();
        resumeTextArea.clear();
        resumeTextArea.clear();
        splitMenuButton.setText("");
        imageTextArea.clear();
        imageView.setImage(null);
    }


    /**
     * Methode pour gérer les enregistrement en mode Local
     */
    public void localconnectsync() {

        BibliothequeDAO bdao = new BibliothequeDAO(connexion);

        for(Bibliotheque.Livre livres:allCurrentLivre){
            Bibliotheque.Livre data=livres;
            boolean valid=true;
            String query = "SELECT * FROM `livres`";
            Object[] idresult = new Object[10];
            try(PreparedStatement result = connexion.prepareStatement(query)) {
                try (ResultSet rs=result.executeQuery()) {
                    Object[]verif= new Object[9];int i=0;

                    logger.info("Livre à ajouter :\n"+data.toString().replace("Infos du Livre : ",""));
                    for(String livre:data.toString().replace("Infos du Livre : ","").split(",")){verif[i]=livre.split("=")[1];i++;}
                    while(true){
                        if (rs.next()) {
                            idresult[0]=rs.getInt("id");
                            idresult[1]=rs.getString("titre");
                            idresult[2]=rs.getInt("auteur_id");
                            idresult[3]=rs.getString("presentation");
                            idresult[4]=rs.getInt("parution");
                            idresult[5]=rs.getInt("colonne");
                            idresult[6]=rs.getInt("rangee");
                            idresult[7]=rs.getString("image");
                            idresult[8]=rs.getString("resume");
                            idresult[9]=rs.getString("status");
                            valid=!(
                                    verif[1].equals("'"+idresult[1]+"'")&&verif[3].equals("'"+idresult[3]+"'")&&
                                            (("'"+verif[4]+"'").equals("'"+idresult[4]+"'"))&&(("'"+verif[5]+"'").equals("'"+idresult[5]+"'"))&&(("'"+verif[6]+"'").equals("'"+idresult[6]+"'"))&&
                                            verif[7].equals(idresult[8])&&verif[8].equals(idresult[9])
                            );
                        }else{break;
                        }
                    }
                    if(valid){bdao.save(data,data.getAuteur());}else{
                        logger.info("livre déjà existant!");
                    }
                }
            }catch(Exception e){
                logger.info("echec de la vérification de la lise des éléments:\n->"+e);
            }
        }

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            File file = new File(Utilities.XML_FILE_PATH);
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            List<Bibliotheque.Livre> livresExistant = bibliotheque.getLivre();
            List<Bibliotheque.Livre> livresNonDupliques = new ArrayList<>();
            for (Bibliotheque.Livre nouveauLivre : allCurrentLivre) {
                boolean duplique = false;
                for (Bibliotheque.Livre livreExistant : livresExistant) {
                    if (livreExistant.getTitre().equalsIgnoreCase(nouveauLivre.getTitre()) &&
                            livreExistant.getAuteur().getNom().equalsIgnoreCase(nouveauLivre.getAuteur().getNom()) &&
                            livreExistant.getAuteur().getPrenom().equalsIgnoreCase(nouveauLivre.getAuteur().getPrenom()) &&
                            livreExistant.getParution() == nouveauLivre.getParution()) {
                        duplique = true;
                        break;
                    }
                }
                if (!duplique) {
                    livresNonDupliques.add(nouveauLivre);
                }
            }
            livresExistant.addAll(livresNonDupliques);
            marshaller.marshal(bibliotheque, file);
            Utilities.showAlertSuccess("Confirmation", "Le livre a bien été enregistré");

        } catch (JAXBException e) {
            logger.log(Level.SEVERE, "Erreur : {0}", e);
        }

    }


}