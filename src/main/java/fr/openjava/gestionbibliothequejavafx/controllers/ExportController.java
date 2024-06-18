package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

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

                // Ajout des titres au sommaire avec hyperliens
                for (Bibliotheque.Livre livre : listLivres) {
                    XWPFParagraph bookTitleParagraph = document.createParagraph();
                    XWPFHyperlinkRun hyperlink = bookTitleParagraph.createHyperlinkRun("#page" + pageNumber);
                    hyperlink.setFontSize(14); // Configuration de la taille de la police à 14
                    hyperlink.setText(pageNumber + ". " + livre.getTitre());
                    hyperlink.setColor("0000FF"); // Couleur bleue pour les hyperliens
                    hyperlink.setUnderline(UnderlinePatterns.SINGLE); // Soulignement pour les hyperliens
                    hyperlink.addBreak();
                    pageNumber++;
                }
                document.createParagraph().createRun().addBreak(BreakType.PAGE);

                pageNumber = 1;
                for (Bibliotheque.Livre livre : listLivres) {
                    XWPFParagraph bookInfoParagraph = document.createParagraph();

                    bookInfoParagraph.setAlignment(ParagraphAlignment.CENTER);

                    XWPFRun bookInfoRun = bookInfoParagraph.createRun();

                    // Ajout du signet pour la page du livre
//                    bookInfoRun = bookInfoParagraph.createRun();
//                    bookInfoRun.setText("Page " + pageNumber);
//                    bookInfoRun.setFontSize(14);
                    CTP ctp = bookInfoParagraph.getCTP();
                    CTBookmark bookmark = ctp.addNewBookmarkStart();
                    bookmark.setId(BigInteger.valueOf(pageNumber));
                    bookmark.setName("page" + pageNumber);
                    ctp.addNewBookmarkEnd().setId(BigInteger.valueOf(pageNumber));

                    bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setText(livre.getTitre());
                    bookInfoRun.setFontSize(25);
                    bookInfoRun.addBreak();

                    bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setFontSize(14);
                    bookInfoRun.setText("");
                    bookInfoRun.addBreak();

                    bookInfoRun = bookInfoParagraph.createRun();
                    if (livre.getImage() != null && !livre.getImage().isEmpty()) {
                        try (InputStream inputStream = convertImageLinkToInputStream(livre.getImage())) {
                            bookInfoRun.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, livre.getImage(), 300, 300);
                        } catch (Exception ignored){
                            bookInfoRun.setText("erreur de chargement de l'image, corrompue ou imcompatible");
                        }
                    } else {
                        bookInfoRun.setText("invalidité du lien de image");
                    }
                    bookInfoRun.addBreak();

                    bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setFontSize(14);
                    bookInfoRun.setText("");
                    bookInfoRun.addBreak();

                    bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setFontSize(14);
                    String[] parta = {"auteur", "Présentation", "Parution", "Colonne", "Rangée"};
                    String[] partb = {livre.getAuteur().getNom() + " " + livre.getAuteur().getPrenom(), livre.getPresentation(), "" + livre.getParution(), "" + livre.getColonne(), "" + livre.getRangee()};
                    for (int i = 0; i < parta.length; i++) {
                        bookInfoRun = bookInfoParagraph.createRun();
                        bookInfoRun.setFontSize(20);
                        bookInfoRun.setText(parta[i] + ":");
                        bookInfoRun.addBreak();
                        bookInfoRun = bookInfoParagraph.createRun();
                        bookInfoRun.setFontSize(14);
                        bookInfoRun.setText(partb[i]);
                        bookInfoRun.addBreak();
                        bookInfoRun = bookInfoParagraph.createRun();
                        bookInfoRun.setFontSize(14);
                        bookInfoRun.setText("");
                        bookInfoRun.addBreak();
                    }

                    for(int i=0;i<10;i++){
                        bookInfoRun = bookInfoParagraph.createRun();
                        bookInfoRun.setFontSize(14);
                        bookInfoRun.setText("");
                        bookInfoRun.addBreak();
                    }

                    bookInfoRun = bookInfoParagraph.createRun();
                    bookInfoRun.setText("Page " + (pageNumber+2));
                    bookInfoRun.setFontSize(14);

                    pageNumber++;

                    bookInfoRun.addBreak(BreakType.PAGE);

                }

                //tableau des livre emprunté ou pas

                document.write(out);
                System.out.println("Fichier Word exporté avec succès !");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static InputStream convertImageLinkToInputStream(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() != 200) {
            throw new Exception("Failed to connect to the URL: " + imageUrl);
        }
        return connection.getInputStream();
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
