package mg.cico.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import mg.cico.models.Article;
import mg.cico.models.enums.Etat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleDAOH2Test {

    private Connection connection;
    private ArticleDAO articleDAO;

    @BeforeAll
    public void setupDatabase() throws Exception {
        // Connexion H2 en mémoire
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        articleDAO = new ArticleDAO(connection);

        // Création de la table
        String createTable = """
                CREATE TABLE article(
                    reference VARCHAR(50) PRIMARY KEY,
                    designation VARCHAR(100),
                    unite VARCHAR(20),
                    etat VARCHAR(20),
                    dateAchat DATE
                )
                """;
        connection.createStatement().execute(createTable);
    }

    @AfterAll
    public void closeDatabase() throws Exception {
        if (connection != null) connection.close();
    }

    @Test
    public void testAddAndGetAllArticle() {
        Article a = new Article();
        a.setReference("REF001");
        a.setDesignation("Article Test H2");
        a.setUnite("pcs");
        a.setEtat(Etat.BN);
        a.setDateAchat(LocalDate.now());

        articleDAO.addArticle(a);

        List<Article> articles = articleDAO.getAllArticle();
        assertFalse(articles.isEmpty(), "La liste des articles ne doit pas être vide");

        Article fetched = articles.get(0);
        assertEquals("REF001", fetched.getReference());
        assertEquals("Article Test H2", fetched.getDesignation());
        assertEquals("pcs", fetched.getUnite());
        assertEquals(Etat.BN, fetched.getEtat());
    }

    @Test
    public void testUpdateArticle() {
        Article a = new Article();
        a.setReference("REF002");
        a.setDesignation("Article Original");
        a.setUnite("kg");
        a.setEtat(Etat.BN);
        a.setDateAchat(LocalDate.now());
        articleDAO.addArticle(a);

        a.setDesignation("Article Modifié");
        a.setEtat(Etat.MA);
        articleDAO.updateArticle(a);

        Article updated = articleDAO.getAllArticle().stream()
                .filter(article -> article.getReference().equals("REF002"))
                .findFirst()
                .orElse(null);

        assertNotNull(updated);
        assertEquals("Article Modifié", updated.getDesignation());
        assertEquals(Etat.MA, updated.getEtat());
    }
}
