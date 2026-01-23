package mg.cico.DAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import mg.cico.models.Projet;
import mg.cico.utils.DBConnection;

public class ProjetDAO {
    final Connection c = DBConnection.startConnection();

    public void createProjet(Projet p){
        String query = "INSERT INTO projet VALUES(?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, p.getTitre());
            stmt.setString(2, p.getNumeroDevis());
            stmt.setDate(3, Date.valueOf(p.getDateDevis()));
            stmt.setString(4, p.getNumeroBC());
            stmt.setDate(5, Date.valueOf(p.getDateBC()));
            stmt.setInt(6, p.getDelaiExecution());
            stmt.setDate(7, Date.valueOf(p.getDateDebut()));
            stmt.setDate(7, Date.valueOf(p.getDateFin()));
            stmt.setInt(9, p.getAvancement());
            stmt.setString(10, p.getResponsable());
            stmt.execute();

        } catch (SQLException e) {
            System.out.println("Error while creating a project: "+e.getMessage());
        }
    }

    public void editTitre(String nouveauTitre, Projet p){
        String query = "UPDATE projet SET titre=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, nouveauTitre);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing the title: "+e.getMessage());
        }
    }

    public void editNumeroDevis(String nouveauNum, Projet p){
        String query = "UPDATE projet SET numeroDevis=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, nouveauNum);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing NumeroDevis: "+e.getMessage());
        }
    }

    public void editNumeroBC(String nouveauNum, Projet p){
        String query = "UPDATE projet SET numeroBC=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, nouveauNum);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing NumeroBC: "+e.getMessage());
        }
    }

    public void editDateBC(LocalDate nouvelleDate, Projet p){
        String query = "UPDATE projet SET dateBC=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(nouvelleDate));
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing dateBC: "+e.getMessage());
        }
    }

    public void editDelaiExecution(int nouveauDelai, Projet p){
        String query = "UPDATE projet SET delaiExecution=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setInt(1,nouveauDelai);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing delaiExecution: "+e.getMessage());
        }
    }

    public void editDateDebut(LocalDate nouvelleDate, Projet p){
        String query = "UPDATE projet SET dateDebut=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(nouvelleDate));
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing dateDebut: "+e.getMessage());
        }
    }

    public void editDateFin(LocalDate nouvelleDate, Projet p){
        String query = "UPDATE projet SET dateFin=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setDate(1, Date.valueOf(nouvelleDate));
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing dateFin: "+e.getMessage());
        }
    }

    public void editAvancement(int a, Projet p){
        String query = "UPDATE projet SET avancement=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setInt(1, a);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing avancement: "+e.getMessage());
        }
    }

    public void editResponsable(String nouvResponsable, Projet p){
        String query = "UPDATE projet SET responsable=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, nouvResponsable);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing responsable: "+e.getMessage());
        }
    }

    public void editStatut(String nouvStatut, Projet p){
        String query = "UPDATE projet SET statut=? WHERE idProjet=?";
        
        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, nouvStatut);
            stmt.setInt(2, p.getIdProjet());
            stmt.execute();
        } catch (SQLException e) {
            System.out.println("Error while editing statut: "+e.getMessage());
        }
    }

    public Projet findProjetById(int idProjet){
        String query = "SELECT * FROM projet WHERE idProjet=?";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setInt(1, idProjet);
            ResultSet rs = stmt.executeQuery();
            return new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut"));

        } catch (SQLException e) {
            System.out.println("Error while findProjetById: "+e.getMessage());
            return null;
        }

    }

    public Projet findProjetByTitre(String titre){
        String query = "SELECT * FROM projet WHERE titre=?";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, titre);
            ResultSet rs = stmt.executeQuery();
            return new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut"));

        } catch (SQLException e) {
            System.out.println("Error while findProjetByTitre: "+e.getMessage());
            return null;
        }

    }

    public Projet findProjetByNumeroDevis(String s){
        String query = "SELECT * FROM projet WHERE numeroDevis=?";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            return new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut"));

        } catch (SQLException e) {
            System.out.println("Error while findProjetByTitre: "+e.getMessage());
            return null;
        }

    }

    public Projet findProjetByNumeroDevis(String s){
        String query = "SELECT * FROM projet WHERE numeroDevis=?";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            return new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut"));

        } catch (SQLException e) {
            System.out.println("Error while findProjetByTitre: "+e.getMessage());
            return null;
        }

    }public Projet findProjetByNumeroDevis(String s){
        String query = "SELECT * FROM projet WHERE numeroDevis=?";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            return new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut"));

        } catch (SQLException e) {
            System.out.println("Error while findProjetByTitre: "+e.getMessage());
            return null;
        }

    }

    public List<Projet> getAllProjet(){
        String query = "SELECT * FROM projet";

        try {
            PreparedStatement stmt = this.c.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            List<Projet> list = new ArrayList<>();

            while(rs.next()){
                list.add(new Projet(rs.getInt("idProjet"), rs.getString("titre"), rs.getString("numeroDevis"), rs.getObject("dateDevis", java.time.LocalDate.class), rs.getString("numeroBC"), rs.getObject("dateBC", java.time.LocalDate.class), rs.getInt("delaiExecution"), rs.getObject("dateDebut", java.time.LocalDate.class), rs.getObject("dateFin", java.time.LocalDate.class), rs.getInt("avancement"), rs.getString("responsable"), rs.getString("Statut")));
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Error while findProjetByTitre: "+e.getMessage());
            return null;
        }
    }
}
