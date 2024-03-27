package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportController {
    private List<Bibliotheque.Livre> listLivres;
    public ExportController(List<Bibliotheque.Livre> listLivres){
        this.listLivres = listLivres;
    }

    public void exportToWord() {
        System.out.println("here");

        if (listLivres == null || listLivres.isEmpty()) {
            System.out.println("Erreur: le tableau des livres est vide.");
            return;
        }

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Veuillez choisir le dossier pour enregistrer votre fichier");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Word", "*.docx"));
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (XWPFDocument document = new XWPFDocument(); FileOutputStream out = new FileOutputStream(file)) {
                // Page de garde
                PageDeGarde(document, "Bibliothèque - Retrouve tes livres");

                int pageNumber = 1;

                // Sommaire
                XWPFParagraph toc = document.createParagraph();
                toc.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun runToc = toc.createRun();
                runToc.setBold(true);
                runToc.setFontSize(18);
                runToc.setText("Sommaire");
                runToc.addBreak();

                // Ajout des titres au sommaire
                for (Bibliotheque.Livre livre : listLivres) {
                    XWPFParagraph bookTitleParagraph = document.createParagraph();
                    XWPFRun bookTitleRun = bookTitleParagraph.createRun();
                    bookTitleRun.setFontSize(14); // Configuration de la taille de la police à 14
                    bookTitleRun.setText(pageNumber + ". " + livre.getTitre());
                    bookTitleRun.addBreak();
                    pageNumber++;
                }
                document.createParagraph().createRun().addBreak(BreakType.PAGE);

                for (Bibliotheque.Livre livre : listLivres) {
                    XWPFParagraph bookInfoParagraph = document.createParagraph();
                    XWPFRun bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setFontSize(14);
                    bookInfoRun.setText("Titre: " + livre.getTitre() + "\n" +
                            "Auteur: " + livre.getAuteur().getNom() + " " + livre.getAuteur().getPrenom() + "\n" +
                            "Présentation: " + livre.getPresentation() + "\n" +
                            "Parution: " + livre.getParution() + "\n" +
                            "Colonne: " + livre.getColonne() + "\n" +
                            "Rangée: " + livre.getRangee());
                    bookInfoRun.addBreak(BreakType.PAGE);
                }

                document.write(out);
                System.out.println("Fichier Word exporté avec succès !");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void PageDeGarde(XWPFDocument document, String title) {
        XWPFParagraph coverPage = document.createParagraph();
        coverPage.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun runCoverPage = coverPage.createRun();
        runCoverPage.setBold(true);
        runCoverPage.setFontSize(20);
        runCoverPage.setText(title);
        runCoverPage.addBreak(org.apache.poi.xwpf.usermodel.BreakType.PAGE);
    }
}
