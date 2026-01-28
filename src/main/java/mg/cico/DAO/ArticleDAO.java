package mg.cico.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mg.cico.models.Article;
import mg.cico.models.criteria.ArticleSearchCriteria;
import mg.cico.models.enums.Etat;
import mg.cico.utils.DBConnection;

public class ArticleDAO {
    
    private final Connection c = DBConnection.startConnection();
    private final Logger logger = LoggerFactory.getLogger(ArticleDAO.class);

    // Ajouter un article
    public void addArticle(Article a){
        String sql = """
                INSERT INTO article(reference, designation, unite, etat, dateAchat)
                VALUES(?,?,?,?,?)
                """;
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, a.getReference());
            stmt.setString(2, a.getDesignation());
            stmt.setString(3, a.getUnite());
            stmt.setObject(4, a.getEtat().name());
            stmt.setDate(5, Date.valueOf(a.getDateAchat()));
            stmt.executeUpdate();
            logger.debug("Article {} ajouté avec succès", a.getDesignation());
        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de l'article {}", a.getDesignation(), e);
        }
    }

    // Récupérer tous les articles
    public List<Article> getAllArticle(){
        String sql = "SELECT * FROM article";
        List<Article> articles = new ArrayList<>();
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Article a = new Article();
                a.setReference(rs.getString("reference"));
                a.setDesignation(rs.getString("designation"));
                a.setUnite(rs.getString("unite"));
                a.setEtat(Etat.valueOf(rs.getString("etat")));
                a.setDateAchat(rs.getObject("dateAchat", LocalDate.class));
                articles.add(a);
            }

            logger.debug("Récupération de tous les articles réussie");
            return articles;
        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération des articles :", e);
            return Collections.emptyList();
        }
    }

    // Mettre à jour un article
    public void updateArticle(Article a){
        String sql = """
            UPDATE article 
            SET designation=?, unite=?, etat=?, dateAchat=?
            WHERE reference=?
            """;
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, a.getDesignation());
            stmt.setString(2, a.getUnite());
            stmt.setObject(3, a.getEtat().name());
            stmt.setDate(4, Date.valueOf(a.getDateAchat()));
            stmt.setString(5, a.getReference());
            stmt.executeUpdate();
            logger.debug("Article {} mis à jour avec succès", a.getReference());
        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour de l'article {}", a.getReference(), e);
        }
    }

    // Recherche par critères (sans idProjet ni quantité)
    public List<Article> searchByCriteria(ArticleSearchCriteria criteria) {

        List<Article> resultat = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM article WHERE 1=1");
        List<Object> params = new ArrayList<>();

        // Filtre par mot-clé
        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND (reference ILIKE ? OR designation ILIKE ? OR unite ILIKE ?)");
            for (int i = 0; i < 3; i++) {
                params.add("%" + criteria.getKeyword() + "%");
            }
        }

        // Filtre par état
        if (criteria.getEtat() != null) {
            sql.append(" AND etat = ?");
            params.add(criteria.getEtat().name());
        }

        // Filtre par dateAchat
        if (criteria.getDateAchatFrom() != null && criteria.getDateAchatTo() != null) {
            sql.append(" AND dateAchat BETWEEN ? AND ?");
            params.add(criteria.getDateAchatFrom());
            params.add(criteria.getDateAchatTo());
        } else if (criteria.getDateAchatFrom() != null) {
            sql.append(" AND dateAchat >= ?");
            params.add(criteria.getDateAchatFrom());
        } else if (criteria.getDateAchatTo() != null) {
            sql.append(" AND dateAchat <= ?");
            params.add(criteria.getDateAchatTo());
        }

        sql.append(" ORDER BY designation ASC");

        try (PreparedStatement stmt = c.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Article a = new Article();
                a.setReference(rs.getString("reference"));
                a.setDesignation(rs.getString("designation"));
                a.setUnite(rs.getString("unite"));
                a.setEtat(Etat.valueOf(rs.getString("etat")));
                a.setDateAchat(rs.getObject("dateAchat", LocalDate.class));
                resultat.add(a);
            }

            logger.debug("Recherche Article : {} résultat(s) trouvé(s)", resultat.size());
            return resultat;

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche des articles", e);
            return Collections.emptyList();
        }
    }
}
