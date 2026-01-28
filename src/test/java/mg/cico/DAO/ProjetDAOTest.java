package mg.cico.DAO;

import mg.cico.models.Projet;
import mg.cico.models.criteria.ProjetSearchCriteria;
import mg.cico.models.enums.Statut;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProjetDAOH2Test {

    private ProjetDAO projetDAO;
    private Connection connection;

    @BeforeAll
    void setup() throws Exception {
        // Connexion H2 en mémoire
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");

        // Création de la table projet (H2 ne supporte pas ENUM, donc VARCHAR)
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE projet (
                    idProjet INT AUTO_INCREMENT PRIMARY KEY,
                    titre VARCHAR(100),
                    lieu VARCHAR(50),
                    numeroDevis VARCHAR(50),
                    dateDevis DATE,
                    numeroBC VARCHAR(50),
                    dateBC DATE,
                    delaiExecution INT,
                    dateDebut DATE,
                    dateFin DATE,
                    avancement INT,
                    responsable VARCHAR(50),
                    statut VARCHAR(10),
                    dernierMaj TIMESTAMP
                )
            """);
        }

        // Injection de la connexion H2 dans le DAO
        projetDAO = new ProjetDAO(connection);
    }

    @AfterAll
    void teardown() throws Exception {
        connection.close();
    }

    @Test
    void testCreateAndGetAllProjet() {
        Projet p = new Projet();
        p.setTitre("Test Projet H2");
        p.setLieu("Antananarivo");
        p.setNumeroDevis("DEV123");
        p.setDateDevis(LocalDate.now());
        p.setNumeroBC("BC123");
        p.setDateBC(LocalDate.now());
        p.setDelaiExecution(30);
        p.setDateDebut(LocalDate.now());
        p.setDateFin(LocalDate.now().plusDays(30));
        p.setAvancement(0);
        p.setResponsable("John Doe");
        p.setStatut(Statut.NC);

        projetDAO.createProjet(p);

        List<Projet> projets = projetDAO.getAllProjet();
        assertFalse(projets.isEmpty());
        boolean found = projets.stream().anyMatch(projet -> "Test Projet H2".equals(projet.getTitre()));
        assertTrue(found);
    }

    @Test
    void testUpdateProjet() {
        Projet p = new Projet();
        p.setTitre("Projet Update H2");
        p.setLieu("Tana");
        p.setNumeroDevis("DEV999");
        p.setDateDevis(LocalDate.now());
        p.setNumeroBC("BC999");
        p.setDateBC(LocalDate.now());
        p.setDelaiExecution(10);
        p.setDateDebut(LocalDate.now());
        p.setDateFin(LocalDate.now().plusDays(10));
        p.setAvancement(0);
        p.setResponsable("Alice");
        p.setStatut(Statut.NC);

        projetDAO.createProjet(p);

        Projet created = projetDAO.getAllProjet().stream()
                .filter(projet -> "Projet Update H2".equals(projet.getTitre()))
                .findFirst()
                .orElseThrow();

        created.setTitre("Projet Updated H2");
        created.setAvancement(50);
        projetDAO.updateProjet(created);

        Projet updated = projetDAO.getAllProjet().stream()
                .filter(projet -> projet.getIdProjet() == created.getIdProjet())
                .findFirst()
                .orElseThrow();

        assertEquals("Projet Updated H2", updated.getTitre());
        assertEquals(50, updated.getAvancement());
    }

    @Test
    void testSearchByCriteria() {
        Projet p = new Projet();
        p.setTitre("Recherche Test H2");
        p.setLieu("Fianarantsoa");
        p.setNumeroDevis("DEV777");
        p.setDateDevis(LocalDate.now());
        p.setNumeroBC("BC777");
        p.setDateBC(LocalDate.now());
        p.setDelaiExecution(15);
        p.setDateDebut(LocalDate.now());
        p.setDateFin(LocalDate.now().plusDays(15));
        p.setAvancement(20);
        p.setResponsable("Bob");
        p.setStatut(Statut.EC);

        projetDAO.createProjet(p);

        ProjetSearchCriteria criteria = new ProjetSearchCriteria();
        criteria.setKeyword("Recherche");

        List<Projet> results = projetDAO.searchByCriteria(criteria);
        assertFalse(results.isEmpty());
        assertEquals("Recherche Test H2", results.get(0).getTitre());
    }
}
