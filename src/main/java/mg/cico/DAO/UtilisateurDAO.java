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

import mg.cico.models.Utilisateur;
import mg.cico.models.enums.Role;

public class UtilisateurDAO {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurDAO.class);
    private final Connection c;

    public UtilisateurDAO(Connection nc) {
        this.c = nc;
    }

    /* ===================== CREATE ===================== */
    public void save(Utilisateur u) {
        String sql = """
            INSERT INTO utilisateur
            (nom, email, role, mot_de_passe, actif, date_creation, dernier_login)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getRole().name());
            ps.setString(4, u.getMotDePasse());
            ps.setBoolean(5, u.isActif());
            ps.setTimestamp(6, Timestamp.valueOf(u.getDateCreation()));
            ps.setTimestamp(7,
                u.getDernierLogin() != null
                    ? Timestamp.valueOf(u.getDernierLogin())
                    : null
            );

            ps.executeUpdate();
            logger.debug("Utilisateur {} ajouté", u.getEmail());

        } catch (SQLException e) {
            logger.error("Erreur lors de l'ajout utilisateur", e);
            throw new RuntimeException(e);
        }
    }

    /* ===================== UPDATE ===================== */
    public void update(Utilisateur u) {
        String sql = """
            UPDATE utilisateur SET
            nom=?, email=?, role=?, actif=?, dernier_login=?
            WHERE id_utilisateur=?
        """;

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getRole().name());
            ps.setBoolean(4, u.isActif());
            ps.setTimestamp(5,
                u.getDernierLogin() != null
                    ? Timestamp.valueOf(u.getDernierLogin())
                    : null
            );
            ps.setInt(6, u.getIdUtilisateur());

            ps.executeUpdate();
            logger.debug("Utilisateur {} mis à jour", u.getIdUtilisateur());

        } catch (SQLException e) {
            logger.error("Erreur mise à jour utilisateur", e);
            throw new RuntimeException(e);
        }
    }

    /* ===================== UPDATE PASSWORD ===================== */
    public void updatePassword(int idUtilisateur, String motDePasse) {
        String sql = "UPDATE utilisateur SET mot_de_passe=? WHERE id_utilisateur=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, motDePasse);
            ps.setInt(2, idUtilisateur);
            ps.executeUpdate();

            logger.debug("Mot de passe utilisateur {} mis à jour", idUtilisateur);

        } catch (SQLException e) {
            logger.error("Erreur mise à jour mot de passe", e);
            throw new RuntimeException(e);
        }
    }

    /* ===================== DELETE (LOGIQUE) ===================== */
    public void deactivate(int idUtilisateur) {
        String sql = "UPDATE utilisateur SET actif=false WHERE id_utilisateur=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();

            logger.debug("Utilisateur {} désactivé", idUtilisateur);

        } catch (SQLException e) {
            logger.error("Erreur désactivation utilisateur", e);
            throw new RuntimeException(e);
        }
    }

    /* ===================== FIND BY ID ===================== */
    public Utilisateur findById(int idUtilisateur) {
        String sql = "SELECT * FROM utilisateur WHERE id_utilisateur=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUtilisateur);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur findById Utilisateur", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /* ===================== FIND BY EMAIL ===================== */
    public Utilisateur findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email=?";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (SQLException e) {
            logger.error("Erreur findByEmail Utilisateur", e);
            throw new RuntimeException(e);
        }
        return null;
    }

    /* ===================== FIND ALL ===================== */
    public List<Utilisateur> findAll() {
        List<Utilisateur> list = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try (Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            logger.error("Erreur findAll Utilisateur", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    /* ===================== MAPPING ===================== */
    private Utilisateur map(ResultSet rs) throws SQLException {
        Utilisateur u = new Utilisateur();
        u.setIdUtilisateur(rs.getInt("id_utilisateur"));
        u.setNom(rs.getString("nom"));
        u.setEmail(rs.getString("email"));
        u.setRole(Role.valueOf(rs.getString("role")));
        u.setMotDePasse(rs.getString("mot_de_passe"));
        u.setActif(rs.getBoolean("actif"));
        u.setDateCreation(rs.getTimestamp("date_creation").toLocalDateTime());

        Timestamp dl = rs.getTimestamp("dernier_login");
        u.setDernierLogin(dl != null ? dl.toLocalDateTime() : null);

        return u;
    }
}
