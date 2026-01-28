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

import mg.cico.models.Projet;
import mg.cico.models.criteria.ProjetSearchCriteria;
import mg.cico.models.enums.Statut;

public class ProjetDAO {

    private static final Logger logger = LoggerFactory.getLogger(ProjetDAO.class);
    private final Connection c ;
    public ProjetDAO(Connection nc){
        this.c = nc;
    }

    public void createProjet(Projet p) {
        String sql = """
                        INSERT INTO projet (
                            titre, lieu, numeroDevis, dateDevis,
                            numeroBC, dateBC, delaiExecution,
                            dateDebut, dateFin, avancement,
                            responsable, statut
                        )
                        VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
                    """;

        try {
            PreparedStatement stmt = this.c.prepareStatement(sql);
            stmt.setString(1, p.getTitre());
            stmt.setString(2, p.getLieu());
            stmt.setString(3, p.getNumeroDevis());
            stmt.setDate(4, Date.valueOf(p.getDateDevis()));
            stmt.setString(5, p.getNumeroBC());
            stmt.setDate(6, Date.valueOf(p.getDateBC()));
            stmt.setInt(7, p.getDelaiExecution());
            stmt.setDate(8, Date.valueOf(p.getDateDebut()));
            stmt.setDate(9, Date.valueOf(p.getDateFin()));
            stmt.setInt(10, p.getAvancement());
            stmt.setString(11, p.getResponsable());
            stmt.setObject(12, p.getStatut().name());
            stmt.execute();

            logger.debug("Projet créé avec succès: {}", p.getTitre());
        } catch (SQLException e) {
            logger.error("Erreur lors de la création du projet: {}", p.getTitre(), e);
        }
    }
    private Projet mapProjet(ResultSet rs) throws SQLException {
    Projet p = new Projet();
    p.setIdProjet(rs.getInt("idProjet"));
    p.setTitre(rs.getString("titre"));
    p.setNumeroDevis(rs.getString("numeroDevis"));
    p.setDateDevis(rs.getObject("dateDevis", LocalDate.class));
    p.setNumeroBC(rs.getString("numeroBC"));
    p.setDateBC(rs.getObject("dateBC", LocalDate.class));
    p.setDelaiExecution(rs.getInt("delaiExecution"));
    p.setDateDebut(rs.getObject("dateDebut", LocalDate.class));
    p.setDateFin(rs.getObject("dateFin", LocalDate.class));
    p.setAvancement(rs.getInt("avancement"));
    p.setResponsable(rs.getString("responsable"));
    p.setLieu(rs.getString("lieu"));
    p.setDernierMaj(rs.getObject("dernierMaj", java.time.LocalDateTime.class));

    String statut = rs.getString("statut");
    p.setStatut(statut != null ? Statut.valueOf(statut) : null);

    return p;
}

public void updateProjet(Projet p) {
    String sql = """
        UPDATE projet SET
            titre = ?,
            lieu = ?,
            numeroDevis = ?,
            dateDevis = ?,
            numeroBC = ?,
            dateBC = ?,
            delaiExecution = ?,
            dateDebut = ?,
            dateFin = ?,
            avancement = ?,
            responsable = ?,
            statut = ?,
            dernierMaj = CURRENT_TIMESTAMP
        WHERE idProjet = ?
    """;

    try (PreparedStatement stmt = c.prepareStatement(sql)) {

        stmt.setString(1, p.getTitre());
        stmt.setString(2, p.getLieu());
        stmt.setString(3, p.getNumeroDevis());
        stmt.setDate(4, p.getDateDevis() != null ? Date.valueOf(p.getDateDevis()) : null);
        stmt.setString(5, p.getNumeroBC());
        stmt.setDate(6, p.getDateBC() != null ? Date.valueOf(p.getDateBC()) : null);
        stmt.setInt(7, p.getDelaiExecution());
        stmt.setDate(8, p.getDateDebut() != null ? Date.valueOf(p.getDateDebut()) : null);
        stmt.setDate(9, p.getDateFin() != null ? Date.valueOf(p.getDateFin()) : null);
        stmt.setInt(10, p.getAvancement());
        stmt.setString(11, p.getResponsable());
        stmt.setString(12, p.getStatut().name());
        stmt.setInt(13, p.getIdProjet());

        stmt.executeUpdate();

    } catch (SQLException e) {
        logger.error("Erreur update projet id={}", p.getIdProjet(), e);
        throw new RuntimeException(e);
    }
}


    public List<Projet> getAllProjet() {
        String query = "SELECT * FROM projet";
        List<Projet> list = new ArrayList<>();

        try (PreparedStatement stmt = this.c.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(this.mapProjet(rs));
            }

            logger.debug("Récupération de tous les projets: {} projets trouvés", list.size());
            return list;

        } catch (SQLException e) {
            logger.error("Erreur lors de la récupération de tous les projets", e);
            return Collections.emptyList();
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

    public List<Projet> searchByCriteria(ProjetSearchCriteria criteria) {
        List<Projet> resultat = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM projet WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (criteria.getKeyword() != null && !criteria.getKeyword().isBlank()) {
            sql.append(" AND (titre ILIKE ? OR numeroDevis ILIKE ? OR numeroBC ILIKE ? OR responsable ILIKE ?)");
            for (int i = 0; i < 4; i++) {
                params.add("%" + criteria.getKeyword() + "%");
            }
        }

        if (criteria.getIdProjet() != null) {
            sql.append(" AND idProjet = ?");
            params.add(criteria.getIdProjet());
        }

        addDateFilter(sql, params, "dateDevis", criteria.getDateDevisFrom(), criteria.getDateDevisTo());
        addDateFilter(sql, params, "dateBC", criteria.getDateBCFrom(), criteria.getDateBCTo());
        addDateFilter(sql, params, "dateDebut", criteria.getDateDebutFrom(), criteria.getDateDebutTo());
        addDateFilter(sql, params, "dateFin", criteria.getDateFinFrom(), criteria.getDateFinTo());

        sql.append(" ORDER BY dateDebut DESC");

        try (PreparedStatement stmt = c.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                resultat.add(this.mapProjet(rs));
            }

            logger.debug("Résultat de recherche: {} projets trouvés", resultat.size());
            return resultat;

        } catch (SQLException e) {
            logger.error("Erreur lors de la recherche des projets", e);
            return Collections.emptyList();
        }
    }


}
