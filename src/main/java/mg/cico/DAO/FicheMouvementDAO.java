package mg.cico.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mg.cico.models.FicheMouvement;
import mg.cico.models.enums.EtatFiche;
import mg.cico.models.enums.TypeMvt;

public class FicheMouvementDAO implements DAO<FicheMouvement>{

    private static final Logger logger = LoggerFactory.getLogger(FicheMouvementDAO.class);
    private final Connection c;

    public FicheMouvementDAO(Connection nc) {
        this.c = nc;
    }

    @Override
    public void save(FicheMouvement f) {
        String sql = """
            INSERT INTO fiche_mouvement
            (numero_fiche, date_creation, etat, type_mvt, id_projet,
            id_responsable, reference_doc, dernier_maj, ref_article)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f.getNumeroFiche());
            ps.setDate(2, Date.valueOf(f.getDateCreation()));
            ps.setString(3, f.getEtat().name());
            ps.setString(4, f.getTypeMvt().name());
            ps.setInt(5, f.getIdProjet());
            ps.setInt(6, f.getIdResponsable());
            ps.setString(7, f.getReferenceDoc());
            ps.setDate(8, Date.valueOf(f.getDernierMaj()));
            ps.setString(9, f.getRef_article());

            ps.executeUpdate();
            logger.debug("FicheMouvement {} ajoutée avec succès", f.getNumeroFiche());

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout de la FicheMouvement", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FicheMouvement f) {
        String sql = """
            UPDATE fiche_mouvement SET
            numero_fiche=?, etat=?, type_mvt=?, id_projet=?,
            id_responsable=?, reference_doc=?, dernier_maj=?, ref_article=?
            WHERE id_fiche=?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, f.getNumeroFiche());
            ps.setString(2, f.getEtat().name());
            ps.setString(3, f.getTypeMvt().name());
            ps.setInt(4, f.getIdProjet());
            ps.setInt(5, f.getIdResponsable());
            ps.setString(6, f.getReferenceDoc());
            ps.setDate(7, Date.valueOf(f.getDernierMaj()));
            ps.setString(8, f.getRef_article());
            ps.setInt(9, f.getIdFiche());

            ps.executeUpdate();
            logger.debug("FicheMouvement {} mise à jour", f.getIdFiche());

        } catch (SQLException e) {
            logger.error("Erreur lors de la mise à jour FicheMouvement", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean  delete(int idFiche) {
        String sql = "DELETE FROM fiche_mouvement WHERE id_fiche=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idFiche);
            int rows = ps.executeUpdate();
            logger.debug("FicheMouvement {} supprimée", idFiche);
            return rows>0;

        } catch (SQLException e) {
            logger.error("Erreur lors de la suppression FicheMouvement", e);
            throw new RuntimeException(e);
        }
    }

    public FicheMouvement findById(int idFiche) {
        String sql = "SELECT * FROM fiche_mouvement WHERE id_fiche=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idFiche);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur lors du findById FicheMouvement", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<FicheMouvement> findAll() {
        List<FicheMouvement> list = new ArrayList<>();
        String sql = "SELECT * FROM fiche_mouvement";

        try (Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            logger.error("Erreur lors du findAll FicheMouvement", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    private FicheMouvement map(ResultSet rs) throws SQLException {
        FicheMouvement f = new FicheMouvement();
        f.setIdFiche(rs.getInt("id_fiche"));
        f.setNumeroFiche(rs.getString("numero_fiche"));
        f.setDateCreation(rs.getDate("date_creation").toLocalDate());
        f.setEtat(EtatFiche.valueOf(rs.getString("etat")));
        f.setTypeMvt(TypeMvt.valueOf(rs.getString("type_mvt")));
        f.setIdProjet(rs.getInt("id_projet"));
        f.setIdResponsable(rs.getInt("id_responsable"));
        f.setReferenceDoc(rs.getString("reference_doc"));
        f.setDernierMaj(rs.getDate("dernier_maj").toLocalDate());
        f.setRef_article(rs.getString("ref_article"));
        return f;
    }
}
