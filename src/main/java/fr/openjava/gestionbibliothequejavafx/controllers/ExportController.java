package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExportController {
    private TableView<Bibliotheque.Livre> tableView;

    /**
     * Constructeur qui prend en parametre TableView<Bibliotheque.Livre> et permet d'itialiser la tableView avec la référence à la TableView
     */
    public ExportController(TableView<Bibliotheque.Livre> tableView){
        this.tableView = tableView;
    }
    public void exportWordDisplay(){
        if (tableView == null) {
            System.out.println(("Erreur: le tableau des livres est vide."));
        }

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez le dossier pour enregistrer votre fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Word", "*.docx"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(file)) {
                /** * Entête du document */
                XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
                XWPFParagraph headerParagraph = header.createParagraph();
                XWPFRun headerRun = headerParagraph.createRun();
                //headerRun.setText("Entête");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date exportDate = new Date();
                headerRun.setText("Entête du document - Date d'exportation : " + dateFormat.format(exportDate));

                /** * Titre du document */
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleRun = titleParagraph.createRun();
                titleRun.setBold(true);
                titleRun.setFontSize(16);
                titleRun.setText("Bienvenue dans notre Gestionnaire de Bibliothèque. Nous vous présentons nore sommaire.");

                /** * Ajout d'une page de garde */
                document.createParagraph().createRun().addBreak();
                XWPFParagraph coverPageParagraph = document.createParagraph();
                coverPageParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun coverPageRun = coverPageParagraph.createRun();
                coverPageRun.setBold(true);
                coverPageRun.setFontSize(16);
                coverPageRun.setText("Sommaire");

                /** * Sommaire des livres */
                document.createParagraph().createRun().addBreak();
                XWPFParagraph tableOfContentParagraph = document.createParagraph();
                XWPFRun tableOfContentRun = tableOfContentParagraph.createRun();
                tableOfContentRun.setBold(true);
                tableOfContentRun.setFontSize(14);
                tableOfContentRun.setText("La liste des livres de notre Bibliothèque:");


                /** * Données des livres de la TableView */
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

                /** * Enregistrer le document */
                document.write(out);
                System.out.println("Fichier Word exporté avec succès !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
