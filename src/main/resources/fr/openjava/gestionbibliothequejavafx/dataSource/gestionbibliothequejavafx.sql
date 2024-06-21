CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       login VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);



CREATE TABLE auteurs (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         nom VARCHAR(255) NOT NULL,
                         prenom VARCHAR(255) NOT NULL
);

CREATE TABLE livres (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        titre VARCHAR(255) NOT NULL,
                        auteur_id INT NOT NULL,
                        presentation TEXT NOT NULL,
                        parution YEAR NOT NULL,
                        colonne TINYINT UNSIGNED NOT NULL,
                        rangee TINYINT UNSIGNED NOT NULL,
                        image VARCHAR(255),
                        resume TEXT NOT NULL,
                        status VARCHAR(50) NOT NULL,
                        FOREIGN KEY (auteur_id) REFERENCES auteurs(id)
);