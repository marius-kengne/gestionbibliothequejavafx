package fr.openjava.gestionbibliothequejavafx.controllers;
import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import fr.openjava.gestionbibliothequejavafx.models.generated.ObjectFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
public class BibliothequeController {

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
        String[] auteur = auteurTextArea.getText().split("\\s+");
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
