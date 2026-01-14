CREATE TABLE projet(
    idProjet SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    numeroDevis VARCHAR(50) NOT NULL,
    dateDevis DATE NOT NULL,
    numeroBC VARCHAR(50),
    dateBC DATE ,
    delaiExecution INT NOT NULL,
    dateDebut DATE NOT NULL,
    dateFin DATE,
    avancement INT NOT NULL,
    responsable VARCHAR(60)
);

CREATE TYPE etat_article AS ENUM('BN','MY', 'MA');

CREATE TABLE article (
    reference VARCHAR(100) PRIMARY KEY,
    designation VARCHAR(100) NOT NULL,
    unite VARCHAR(20) NOT NULL,
    quantite DECIMAL(8,2),
    etat etat_article NOT NULL,
    dateAchat DATE NOT NULL,
    idProjet INT NOT NULL,
    CONSTRAINT fk_article_projet FOREIGN KEY(idProjet)
        REFERENCES projet(idProjet)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE ficheMouvement (
    idFiche SERIAL PRIMARY KEY,
    dernierMaj DATE NOT NULL,
    idProjet INT NOT NULL,
    ref_article VARCHAR(100) NOT NULL,
    CONSTRAINT fk_fiche_projet FOREIGN KEY(idProjet)
        REFERENCES projet(idProjet)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_fiche_article FOREIGN KEY(ref_article)
        REFERENCES article(reference)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


CREATE TYPE typeMvt AS ENUM ('E','S');

CREATE TABLE mouvement(
    idMouvement SERIAL PRIMARY KEY,
    dateMouvement DATE NOT NULL,
    typeMouvement typeMvt NOT NULL,
    referenceDoc VARCHAR(50),
    observation VARCHAR(200),
    idFiche INT NOT NULL,
    CONSTRAINT fk_Mvt_fiche FOREIGN KEY(idFiche)
        REFERENCES ficheMouvement(idFiche)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

