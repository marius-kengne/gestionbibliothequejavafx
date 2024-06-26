/**
 * Module de l'application GestionBibliothequeJavaFX.
 */
module fr.openjava.gestionbibliothequejavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml.bind;
    requires plexus.io;
    requires org.apache.poi.ooxml;
    requires java.sql;
    requires java.mail;
    requires org.slf4j;
    opens fr.openjava.gestionbibliothequejavafx to javafx.fxml;
    exports fr.openjava.gestionbibliothequejavafx;
    exports fr.openjava.gestionbibliothequejavafx.controllers;
    exports fr.openjava.gestionbibliothequejavafx.DAO;
    opens fr.openjava.gestionbibliothequejavafx.controllers to javafx.fxml;
    opens fr.openjava.gestionbibliothequejavafx.models.generated to java.xml.bind, javafx.base;

}