package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import mg.cico.DAO.ArticleDAO;
import mg.cico.models.Article;
import mg.cico.models.criteria.ArticleSearchCriteria;

public class ArticleService {

    private final ArticleDAO dao;

    public ArticleService(Connection c) {
        this.dao = new ArticleDAO(c);
    }

    public void addArticle(Article a) {
        validate(a);
        dao.addArticle(a);
    }

    public void updateArticle(Article a) {
        if (a.getReference() == null || a.getReference().isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        validate(a);
        dao.updateArticle(a);
    }

    public List<Article> getAllArticles() {
        return dao.getAllArticle();
    }

    public List<Article> searchArticles(ArticleSearchCriteria criteria) {
        if (criteria == null)
            throw new IllegalArgumentException("Critères de recherche manquants");
        return dao.searchByCriteria(criteria);
    }

    private void validate(Article a) {
        if (a.getReference() == null || a.getReference().isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        if (a.getDesignation() == null || a.getDesignation().isBlank())
            throw new IllegalArgumentException("Désignation obligatoire");
        if (a.getUnite() == null || a.getUnite().isBlank())
            throw new IllegalArgumentException("Unité obligatoire");
        if (a.getEtat() == null)
            throw new IllegalArgumentException("État obligatoire");
        if (a.getDateAchat() == null || a.getDateAchat().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Date d'achat invalide");
    }
}
