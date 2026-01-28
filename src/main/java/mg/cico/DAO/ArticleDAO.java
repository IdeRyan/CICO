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

    public void addArticle(Article a){
        String sql = "INSERT INTO article VALUES(?,?,?,?,?,?,?)";
        try(PreparedStatement stmt = c.prepareStatement(sql) ) {
            stmt.setString(1, a.getReference());
            stmt.setString(2 , a.getDesignation());
            stmt.setString(3 , a.getUnite());
            stmt.setDouble(4 , a.getQuantite());
            stmt.setObject(5, a.getEtat());
            stmt.setObject(6, Date.valueOf(a.getDateAchat()));
            stmt.setInt(7, a.getIdProjet());
            logger.debug("Article {} added succesfully",a.getDesignation());
        } catch (SQLException e) {
            logger.error("Error while adding article {}", a.getDesignation() , e.getMessage());
        }
    }

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
                a.setQuantite(rs.getDouble("quantite"));
                a.setEtat(rs.getObject("etat"));
                a.setDateAchat((rs.getObject("dateAchat",java.time.LocalDate.class)));
                a.setIdProjet(rs.getInt("idProjet"));
                articles.add(a);
            }

            logger.debug("Getting all the article succesffully");
            return articles;
        } catch (SQLException e) {
            logger.error("Error while getting all the article :",e.getMessage());
            return Collections.emptyList();
        }
    }

    public void editDesignation(String d , Article a){
        String sql = "UPDATE article SET designation=? WHERE reference=?";
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, d);
            stmt.setString(2, a.getReference());
            stmt.execute();
            logger.debug("Designation of {} modified to {}",a.getReference(),d);
        } catch (SQLException e) {
            logger.error("Error while editing the designation of {} :",a.getReference(),e.getMessage());
        }
    }

    public void editQuantite(Double q , Article a){
        String sql = "UPDATE article SET quantite=? WHERE reference=?";
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setDouble(1, q);
            stmt.setString(2, a.getReference());
            stmt.execute();
            logger.debug("Qunatity of {} modified to {}",a.getReference(),q);
        } catch (SQLException e) {
            logger.error("Error while editing the quantity of {} :",a.getReference(),e.getMessage());
        }
    }

    public void editEtat(Etat et , Article a){
        String sql = "UPDATE article SET etat=? WHERE reference=?";
        try(PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setObject(1, et);
            stmt.setString(2, a.getReference());
            stmt.execute();
            logger.debug("State of {} modified to {}",a.getReference(),et);
        } catch (SQLException e) {
            logger.error("Error while editing the state of {} :",a.getReference(),e.getMessage());
        }
    }


    private void addQuantiteFilter(StringBuilder sql, List<Object> params, Double min, Double max) {

    if (min != null && max != null) {
        sql.append(" AND quantite BETWEEN ? AND ?");
        params.add(min);
        params.add(max);
    } else if (min != null) {
        sql.append(" AND quantite >= ?");
        params.add(min);
    } else if (max != null) {
        sql.append(" AND quantite <= ?");
        params.add(max);
    }
}

    private void addDateFilter(StringBuilder sql, List<Object> params, String column, LocalDate from, LocalDate to) {
        if (from != null && to != null) {
            sql.append(" AND ").append(column).append(" BETWEEN ? AND ?");
            params.add(from);
            params.add(to);
        } else if (from != null) {
            sql.append(" AND ").append(column).append(" = ?");
            params.add(from);
        }
    }
    public List<Article> searchByCriteria(ArticleSearchCriteria criteria) {

    List<Article> resultat = new ArrayList<>();
    StringBuilder sql = new StringBuilder("SELECT * FROM article WHERE 1=1");
    List<Object> params = new ArrayList<>();

    if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
        sql.append(" AND (reference ILIKE ? OR designation ILIKE ? OR unite ILIKE ?)");
        for (int i = 0; i < 3; i++) {
            params.add("%" + criteria.getKeyword() + "%");
        }
    }

    if (criteria.getEtat() != null) {
        sql.append(" AND etat = ?");
        params.add(criteria.getEtat().name());
    }
    
    if (criteria.getIdProjet() != null) {
        sql.append(" AND idProjet = ?");
        params.add(criteria.getIdProjet());
    }

    addQuantiteFilter(
        sql,
        params,
        criteria.getQuantiteMin(),
        criteria.getQuantiteMax()
    );

    addDateFilter(
        sql,
        params,
        "dateAchat",
        criteria.getDateAchatFrom(),
        criteria.getDateAchatTo()
    );

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
            a.setQuantite(rs.getDouble("quantite"));
            a.setEtat(Etat.valueOf(rs.getString("etat")));
            a.setDateAchat(rs.getObject("dateAchat", LocalDate.class));
            a.setIdProjet(rs.getInt("idProjet"));
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
