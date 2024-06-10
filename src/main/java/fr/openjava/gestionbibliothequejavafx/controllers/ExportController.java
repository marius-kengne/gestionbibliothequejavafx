package fr.openjava.gestionbibliothequejavafx.controllers;

import fr.openjava.gestionbibliothequejavafx.models.generated.Bibliotheque;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
                    bookTitleRun.setText((pageNumber+1) + ". " + livre.getTitre());
                    bookTitleRun.addBreak();
                    pageNumber++;
                }
                document.createParagraph().createRun().addBreak(BreakType.PAGE);

                for (Bibliotheque.Livre livre : listLivres) {
                    XWPFParagraph bookInfoParagraph = document.createParagraph();

                    bookInfoParagraph.setAlignment(ParagraphAlignment.CENTER);

                    XWPFRun bookInfoRun=bookInfoParagraph.createRun();
                    bookInfoRun.setText(livre.getTitre());
                    bookInfoRun.setFontSize(25);
                    bookInfoRun.addBreak();

                    bookInfoRun=bookInfoParagraph.createRun();bookInfoRun.setFontSize(14);
                    bookInfoRun.setText("");bookInfoRun.addBreak();

                    bookInfoRun = bookInfoParagraph.createRun();
                    if(livre.getImage()!=null){bookInfoRun.addPicture((InputStream)livre.getImage().lines(),1,"",300,300);}
                    else{bookInfoRun.setText("image non valide");}
                    bookInfoRun.addBreak();

                    bookInfoRun=bookInfoParagraph.createRun();bookInfoRun.setFontSize(14);
                    bookInfoRun.setText("");bookInfoRun.addBreak();

                    bookInfoRun=bookInfoParagraph.createRun();bookInfoRun.setFontSize(14);

                    String[]parta={"auteur","Présentation","Parution","Colonne","Rangée"},
                    partb={livre.getAuteur().getNom()+" "+livre.getAuteur().getPrenom(),livre.getPresentation(),""+livre.getParution(),""+livre.getColonne(),""+livre.getRangee()};
                    for(int i=0;i<parta.length;i++){
                        bookInfoRun=bookInfoParagraph.createRun();bookInfoRun.setFontSize(20);
                        bookInfoRun.setText(parta[i]+":");bookInfoRun.addBreak();
                        bookInfoRun=bookInfoParagraph.createRun();bookInfoRun.setFontSize(14);
                        bookInfoRun.setText(partb[i]);bookInfoRun.addBreak();

                    }

                    bookInfoRun.addBreak(BreakType.PAGE);

                    //XWPFTable bookInfoRun2=document.createTable();
                    //bookInfoRun2.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ff0000");bookInfoRun2.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ffffff");
                    //bookInfoRun2.setTopBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ff0000");bookInfoRun2.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ff0000");
                    //bookInfoRun2.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ff0000");bookInfoRun2.setRightBorder(XWPFTable.XWPFBorderType.SINGLE,1,0,"ff0000");
                    //XWPFTableRow row1=bookInfoRun2.getRow(0);
                    //XWPFParagraph paragraph=row1.getCell(0).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);XWPFRun run=paragraph.createRun();run.setText("Auteur : ");run.setFontSize(14);
                    //paragraph=row1.addNewTableCell().getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText(livre.getAuteur().getNom()+" "+livre.getAuteur().getPrenom());run.setFontSize(14);
                    //XWPFTableRow row2=bookInfoRun2.createRow();
                    //paragraph=row2.getCell(0).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText("Présentation : ");run.setFontSize(14);
                    //paragraph=row2.getCell(1).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText(livre.getPresentation());run.setFontSize(14);
                    //XWPFTableRow row3=bookInfoRun2.createRow();
                    //paragraph=row3.getCell(0).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText("Parution : ");run.setFontSize(14);
                    //paragraph=row3.getCell(1).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText(""+livre.getParution());run.setFontSize(14);
                    //XWPFTableRow row4=bookInfoRun2.createRow();
                    //paragraph=row4.getCell(0).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText("Colonne : ");run.setFontSize(14);
                    //paragraph=row4.getCell(1).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText(""+livre.getColonne());run.setFontSize(14);
                    //XWPFTableRow row5=bookInfoRun2.createRow();
                    //paragraph=row5.getCell(0).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText("Rangée : ");run.setFontSize(14);
                    //paragraph=row5.getCell(1).getParagraphs().get(0);paragraph.setAlignment(ParagraphAlignment.CENTER);run=paragraph.createRun();run.setText(""+livre.getRangee());run.setFontSize(14);

                }

                //tableau des livre emprunté ou pas

                document.write(out);
                System.out.println("Fichier Word exporté avec succès !");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void createCellWithText(XWPFTableCell cell, String text, int fontSize, ParagraphAlignment alignment) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        run.setText(text);
        run.setFontSize(fontSize);
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
