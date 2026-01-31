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

import mg.cico.models.LigneMouvement;

public class LigneMouvementDAO implements DAO<LigneMouvement>{

    private static final Logger logger = LoggerFactory.getLogger(LigneMouvementDAO.class);
    private final Connection c;

    public LigneMouvementDAO(Connection nc) {
        this.c = nc;
    }

    @Override
    public void save(LigneMouvement l) {
        String sql = """
            INSERT INTO ligne_mouvement
            (id_fiche, ref_article, quantite, id_lieu_source,
            id_lieu_destination, id_emetteur, id_destinataire,
            observation, date_mouvement)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, l.getIdFiche());
            ps.setString(2, l.getRefArcticle());
            ps.setDouble(3, l.getQuantite());
            ps.setInt(4, l.getIdLieuSource());
            ps.setInt(5, l.getIdLieuDestination());
            ps.setInt(6, l.getIdEmetteur());
            ps.setInt(7, l.getIdDestinataire());
            ps.setString(8, l.getObservation());
            ps.setTimestamp(9, Timestamp.valueOf(l.getDateMouvement()));

            ps.executeUpdate();
            logger.debug("LigneMouvement ajoutée pour fiche {}", l.getIdFiche());

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout LigneMouvement", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(LigneMouvement l) {
        String sql = """
            UPDATE ligne_mouvement SET
            ref_article=?, quantite=?, id_lieu_source=?,
            id_lieu_destination=?, id_emetteur=?,
            id_destinataire=?, observation=?, date_mouvement=?
            WHERE id_ligne=?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, l.getRefArcticle());
            ps.setDouble(2, l.getQuantite());
            ps.setInt(3, l.getIdLieuSource());
            ps.setInt(4, l.getIdLieuDestination());
            ps.setInt(5, l.getIdEmetteur());
            ps.setInt(6, l.getIdDestinataire());
            ps.setString(7, l.getObservation());
            ps.setTimestamp(8, Timestamp.valueOf(l.getDateMouvement()));
            ps.setInt(9, l.getIdLigne());

            ps.executeUpdate();
            logger.debug("LigneMouvement {} mise à jour", l.getIdLigne());

        } catch (SQLException e) {
            logger.error("Erreur mise à jour LigneMouvement", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int idLigne) {
        String sql = "DELETE FROM ligne_mouvement WHERE id_ligne=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idLigne);
            int rows = ps.executeUpdate();

            logger.debug("LigneMouvement {} supprimée", idLigne);
            return rows>0;

        } catch (SQLException e) {
            logger.error("Erreur suppression LigneMouvement", e);
            throw new RuntimeException(e);
        }
    }

    public LigneMouvement findById(int idLigne) {
        String sql = "SELECT * FROM ligne_mouvement WHERE id_ligne=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idLigne);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur findById LigneMouvement", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<LigneMouvement> findByFiche(int idFiche) {
        List<LigneMouvement> list = new ArrayList<>();
        String sql = "SELECT * FROM ligne_mouvement WHERE id_fiche=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idFiche);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            logger.error("Erreur findByFiche LigneMouvement", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<LigneMouvement> findAll() {
        List<LigneMouvement> list = new ArrayList<>();
        String sql = "SELECT * FROM ligne_mouvement";

        try (Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            logger.error("Erreur findAll LigneMouvement", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    private LigneMouvement map(ResultSet rs) throws SQLException {
        LigneMouvement l = new LigneMouvement();
        l.setIdLigne(rs.getInt("id_ligne"));
        l.setIdFiche(rs.getInt("id_fiche"));
        l.setRefArcticle(rs.getString("ref_article"));
        l.setQuantite(rs.getDouble("quantite"));
        l.setIdLieuSource(rs.getInt("id_lieu_source"));
        l.setIdLieuDestination(rs.getInt("id_lieu_destination"));
        l.setIdEmetteur(rs.getInt("id_emetteur"));
        l.setIdDestinataire(rs.getInt("id_destinataire"));
        l.setObservation(rs.getString("observation"));
        l.setDateMouvement(rs.getTimestamp("date_mouvement").toLocalDateTime());
        return l;
    }
}
