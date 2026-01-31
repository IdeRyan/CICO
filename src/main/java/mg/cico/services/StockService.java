package mg.cico.services;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import mg.cico.DAO.StockDAO;
import mg.cico.models.Stock;

public class StockService {

    private final StockDAO dao;

    public StockService(Connection c) {
        this.dao = new StockDAO(c);
    }

    public void addStock(Stock s) {
        if (s.getRefArticle() == null || s.getRefArticle().isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        if (s.getIdLieu() <= 0)
            throw new IllegalArgumentException("ID lieu invalide");
        if (s.getDernierMaj() == null)
            s.setDernierMaj(LocalDateTime.now());

        dao.save(s);
    }

    public void updateStock(Stock s) {
        if (s.getRefArticle() == null || s.getRefArticle().isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        if (s.getIdLieu() <= 0)
            throw new IllegalArgumentException("ID lieu invalide");
        s.setDernierMaj(LocalDateTime.now());

        dao.update(s);
    }

    public void deleteStock(int idStock) {
        if (idStock <= 0)
            throw new IllegalArgumentException("ID stock invalide");
        dao.delete(idStock);
    }

    public Stock getStockById(int idStock) {
        if (idStock <= 0)
            throw new IllegalArgumentException("ID stock invalide");
        return dao.findById(idStock);
    }

    public Stock getStockByArticleAndLieu(String refArticle, int idLieu) {
        if (refArticle == null || refArticle.isBlank())
            throw new IllegalArgumentException("Référence article obligatoire");
        if (idLieu <= 0)
            throw new IllegalArgumentException("ID lieu invalide");
        return dao.findByArticleAndLieu(refArticle, idLieu);
    }

    public List<Stock> getAllStocks() {
        return dao.findAll();
    }
}
