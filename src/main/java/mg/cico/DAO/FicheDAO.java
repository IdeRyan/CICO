package mg.cico.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mg.cico.models.FicheMouvement;
import mg.cico.utils.DBConnection;

public class FicheDAO {
    private final Connection c = DBConnection.startConnection();
    private final Logger logger = LoggerFactory.getLogger(ArticleDAO.class);

    public void addFiche(FicheMouvement f){
        String sql = "INSERT INTO fiche ficheMouvement VALUES(?,?,?,?)";
        try(PreparedStatement stmt = c.prepareStatement(sql) ) {
            stmt.setInt(1, f.getIdFiche());
            stmt.setObject(2, Date.valueOf(f.getDernierMaj()));
            stmt.setInt(3, f.getIdProjet());
            stmt.setString(4, f.getRef_article());
            logger.debug("Fiche {} created succesfully",f.getIdFiche());
        } catch (SQLException e) {
            logger.error("Error while creating fiche {}", f.getIdFiche() , e.getMessage());
        }
    }
}
