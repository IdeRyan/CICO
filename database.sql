-- ========================
-- 1. TYPES ENUM
-- ========================

CREATE TYPE statut AS ENUM('NC','EC','T','A');
CREATE TYPE etat_article AS ENUM('BN','MY','MA');
CREATE TYPE typeMvt AS ENUM('ENTREE','SORTIE','TRANSFERT','CONSO');
CREATE TYPE etat_fiche AS ENUM('BROUILLON','VALIDE','ANNULE');

-- ========================
-- 2. TABLES DE BASE
-- ========================

-- Table des projets
CREATE TABLE projet(
    idProjet SERIAL PRIMARY KEY,
    titre VARCHAR(100) NOT NULL,
    lieu VARCHAR(50) NOT NULL,
    numeroDevis VARCHAR(50) NOT NULL,
    dateDevis DATE NOT NULL,
    numeroBC VARCHAR(50),
    dateBC DATE,
    delaiExecution INT NOT NULL,
    dateDebut DATE NOT NULL,
    dateFin DATE,
    avancement INT NOT NULL,
    responsable VARCHAR(60),
    statut statut NOT NULL,
    dernierMaj TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table des utilisateurs (personnel)
CREATE TABLE utilisateur(
    idUtilisateur SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,  -- login
    role VARCHAR(50) NOT NULL,           -- Admin / Utilisateur
    motDePasse VARCHAR(255) NOT NULL,    -- mot de passe hashé
    actif BOOLEAN NOT NULL DEFAULT TRUE, -- compte actif ou non
    dateCreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dernierLogin TIMESTAMP               -- pour suivi
);


-- Table des lieux (dépôt, chantier, etc.)
CREATE TABLE lieu(
    idLieu SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    typeLieu VARCHAR(50) -- ex: DEPOT, CHANTIER, UTILISATEUR
);

-- Table des articles
CREATE TABLE article(
    reference VARCHAR(100) PRIMARY KEY,
    designation VARCHAR(100) NOT NULL,
    unite VARCHAR(20) NOT NULL,
    etat etat_article NOT NULL,
    dateAchat DATE NOT NULL
);

-- ========================
-- 3. STOCK
-- ========================

CREATE TABLE stock(
    idStock SERIAL PRIMARY KEY,
    refArticle VARCHAR(100) NOT NULL,
    idLieu INT NOT NULL,
    quantite DECIMAL(8,2) NOT NULL DEFAULT 0,
    dernierMaj TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_stock_article FOREIGN KEY(refArticle)
        REFERENCES article(reference) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_stock_lieu FOREIGN KEY(idLieu)
        REFERENCES lieu(idLieu) ON UPDATE CASCADE ON DELETE RESTRICT,
    UNIQUE(refArticle, idLieu)
);

-- ========================
-- 4. FICHES DE MOUVEMENT
-- ========================

CREATE TABLE ficheMouvement(
    idFiche SERIAL PRIMARY KEY,
    numeroFiche VARCHAR(20) UNIQUE NOT NULL,
    dateCreation TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    etat etat_fiche NOT NULL DEFAULT 'BROUILLON',
    typeMouvement typeMvt NOT NULL, -- ENTREE, SORTIE, TRANSFERT, CONSO
    idProjet INT,
    idResponsable INT,
    referenceDocument VARCHAR(50),
    dernierMaj TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_fiche_projet FOREIGN KEY(idProjet)
        REFERENCES projet(idProjet) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_fiche_responsable FOREIGN KEY(idResponsable)
        REFERENCES utilisateur(idUtilisateur)
);

-- ========================
-- 5. LIGNES DE MOUVEMENT
-- ========================

CREATE TABLE ligneMouvement(
    idLigne SERIAL PRIMARY KEY,
    idFiche INT NOT NULL,
    refArticle VARCHAR(100) NOT NULL,
    quantite DECIMAL(8,2) NOT NULL,
    idLieuSource INT,
    idLieuDestination INT,
    idEmetteur INT,
    idDestinataire INT,
    observation VARCHAR(200),
    dateMouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ligne_fiche FOREIGN KEY(idFiche)
        REFERENCES ficheMouvement(idFiche) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_ligne_article FOREIGN KEY(refArticle)
        REFERENCES article(reference) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_ligne_source FOREIGN KEY(idLieuSource)
        REFERENCES lieu(idLieu) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_ligne_destination FOREIGN KEY(idLieuDestination)
        REFERENCES lieu(idLieu) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_ligne_emetteur FOREIGN KEY(idEmetteur)
        REFERENCES utilisateur(idUtilisateur),
    CONSTRAINT fk_ligne_destinataire FOREIGN KEY(idDestinataire)
        REFERENCES utilisateur(idUtilisateur)
);

-- ========================
-- 6. HISTORIQUE DE STOCK (OPTIONNEL)
-- ========================

CREATE TABLE historiqueStock(
    idHistorique SERIAL PRIMARY KEY,
    refArticle VARCHAR(100) NOT NULL,
    idLieu INT NOT NULL,
    quantite DECIMAL(8,2) NOT NULL,
    typeMouvement typeMvt NOT NULL,
    idFiche INT,
    dateMouvement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_hist_article FOREIGN KEY(refArticle)
        REFERENCES article(reference),
    CONSTRAINT fk_hist_lieu FOREIGN KEY(idLieu)
        REFERENCES lieu(idLieu),
    CONSTRAINT fk_hist_fiche FOREIGN KEY(idFiche)
        REFERENCES ficheMouvement(idFiche)
);
    