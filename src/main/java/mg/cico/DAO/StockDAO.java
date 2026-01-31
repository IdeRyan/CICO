package mg.cico.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mg.cico.models.Stock;

public class StockDAO implements DAO<Stock>{

    private static final Logger logger = LoggerFactory.getLogger(StockDAO.class);
    private final Connection c;

    public StockDAO(Connection nc) {
        this.c = nc;
    }

    @Override
    public void save(Stock s) {
        String sql = """
            INSERT INTO stock
            (ref_article, id_lieu, quantite, dernier_maj)
            VALUES (?, ?, ?, ?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getRefArticle());
            ps.setInt(2, s.getIdLieu());
            ps.setDouble(3, s.getQuantite());
            ps.setTimestamp(4, Timestamp.valueOf(s.getDernierMaj()));

            ps.executeUpdate();
            logger.debug("Stock ajouté pour article {}", s.getRefArticle());

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout du stock", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Stock s) {
        String sql = """
            UPDATE stock SET
            quantite=?, dernier_maj=?
            WHERE ref_article=? AND id_lieu=?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDouble(1, s.getQuantite());
            ps.setTimestamp(2, Timestamp.valueOf(s.getDernierMaj()));
            ps.setString(3, s.getRefArticle());
            ps.setInt(4, s.getIdLieu());

            ps.executeUpdate();
            logger.debug("Stock mis à jour pour article {} (lieu {})",
                    s.getRefArticle(), s.getIdLieu());

        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour du stock", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean  delete(int idStock) {
        String sql = "DELETE FROM stock WHERE id_stock=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idStock);
            int rows = ps.executeUpdate();

            logger.debug("Stock {} supprimé", idStock);
            return rows>0;
        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression du stock", e);
            throw new RuntimeException(e);
        }
    }

    public Stock findById(int idStock) {
        String sql = "SELECT * FROM stock WHERE id_stock=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idStock);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors du findById Stock", e);
            throw new RuntimeException(e);
        }
        return null;
    }


    public Stock findByArticleAndLieu(String refArticle, int idLieu) {
        String sql = "SELECT * FROM stock WHERE ref_article=? AND id_lieu=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, refArticle);
            ps.setInt(2, idLieu);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur findByArticleAndLieu Stock", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Stock> findAll() {
        List<Stock> list = new ArrayList<>();
        String sql = "SELECT * FROM stock";

        try (Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            logger.error("Erreur lors du findAll Stock", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    private Stock map(ResultSet rs) throws SQLException {
        Stock s = new Stock();
        s.setIdStock(rs.getInt("id_stock"));
        s.setRefArticle(rs.getString("ref_article"));
        s.setIdLieu(rs.getInt("id_lieu"));
        s.setQuantite(rs.getDouble("quantite"));
        s.setDernierMaj(rs.getTimestamp("dernier_maj").toLocalDateTime());
        return s;
    }
}
