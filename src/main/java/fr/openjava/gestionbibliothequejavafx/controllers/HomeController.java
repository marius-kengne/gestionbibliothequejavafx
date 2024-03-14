package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.ObjectFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.bind.*;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
public class HomeController {

    public HomeController(){
        System.out.println("################## Lancement");
    }

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private TableView<Bibliotheque.Livre> tableView;

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

    private ObservableList<Bibliotheque.Livre> livres = FXCollections.observableArrayList();

    /**
     * Méthode pour initialiser le contenu des colones du tableau
     */
    public void initialize() {
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        //auteurColumn.setCellValueFactory(new PropertyValueFactory<>("auteur"));
        auteurColumn.setCellValueFactory(cellData -> {
            /**
             * Récupérer l'objet Livre correspondant à la ligne actuelle
             * Récupérer l'objet Auteur du livre
             * Retourner une SimpleStringProperty contenant le nom complet de l'auteur
              */

            Bibliotheque.Livre livre = cellData.getValue();

            Bibliotheque.Livre.Auteur auteur = livre.getAuteur();

            //
            return new SimpleStringProperty(auteur.getNom() + " " + auteur.getPrenom());
        });
        presentationColumn.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        parutionColumn.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colonneColumn.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        rangeeColumn.setCellValueFactory(new PropertyValueFactory<>("rangee"));

        chargerDonneesDuXML();
    }

    /**
     * Méthode pour récupérer les données du fichier xml
     */
    private void chargerDonneesDuXML() {
        try {

            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            //unmarshaller.setValidating(true);
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(new File("src/main/resources/fr/openjava/gestionbibliothequejavafx/dataSource/Biblio.xml"));
            for (Bibliotheque.Livre livre : bibliotheque.getLivre()) {
                String data = livre.getAuteur().getNom() + " " + livre.getAuteur().getPrenom();
                Bibliotheque.Livre.Auteur auteur =  new Bibliotheque.Livre.Auteur();
                //auteur.setNom(livre.getAuteur().getNom());
                //auteur.setPrenom(livre.getAuteur().getPrenom());
                //livre.setAuteur(auteur);
                System.out.println("########################## nom " + livre.getAuteur().getNom());

                System.out.println("########################## prenom " + livre.getAuteur().getPrenom());
            }

            livres.addAll(bibliotheque.getLivre());
            tableView.setItems(livres);
             System.out.println("########################## OK " + livres);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }





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

    public void validerAction(ActionEvent event) throws JAXBException {
        // Récupérer les valeurs des champs
        String titre = titreTextArea.getText();
        String[] auteur = auteurTextArea.getText().split(" ");
        String presentation = presentationTextArea.getText();
        int parution = Integer.parseInt(parutionTextArea.getText());
        int colonne = Integer.parseInt(colonneTextArea.getText());
        int rangee = Integer.parseInt(rangeeTextArea.getText());

        // Créer un nouvel objet Livre
        Bibliotheque.Livre livre = new Bibliotheque.Livre();
        livre.setTitre(titre);

        Bibliotheque.Livre.Auteur auteurObj = new Bibliotheque.Livre.Auteur();
        auteurObj.setNom(auteur[0]);
        auteurObj.setPrenom(auteur[1]);
        livre.setAuteur(auteurObj);

        livre.setPresentation(presentation);
        livre.setParution(parution);
        livre.setColonne((short) colonne);
        livre.setRangee((short) rangee);

        // Ajouter ce livre au fichier XML
        ajouterLivreAuXML(livre);
    }

    private void ajouterLivreAuXML(Bibliotheque.Livre livre) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Charger le fichier XML existant
            File file = new File("livres.xml");
            Bibliotheque bibliotheque;
            if (file.exists()) {
                bibliotheque = (Bibliotheque) jaxbContext.createUnmarshaller().unmarshal(file);
            } else {
                bibliotheque = new ObjectFactory().createBibliotheque();
            }
            // Ajouter le livre à la liste des livres
            bibliotheque.getLivre().add(livre);

            // Enregistrer les modifications dans le fichier XML
            marshaller.marshal(bibliotheque, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}