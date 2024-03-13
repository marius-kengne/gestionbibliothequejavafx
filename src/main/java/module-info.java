module fr.openjava.gestionbibliothequejavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens fr.openjava.gestionbibliothequejavafx to javafx.fxml;
    exports fr.openjava.gestionbibliothequejavafx;
    exports fr.openjava.gestionbibliothequejavafx.controllers;
    opens fr.openjava.gestionbibliothequejavafx.controllers to javafx.fxml;
}