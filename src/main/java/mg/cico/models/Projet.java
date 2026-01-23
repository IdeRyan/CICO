package mg.cico.models;

import java.time.LocalDate;

public class Projet {
    private int idProjet;
    private String titre;
    private String numeroDevis;
    private LocalDate dateDevis;
    private String numeroBC;
    private LocalDate dateBC;
    private int delaiExecution;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int avancement;
    private String responsable;
    private String Statut;

    
    public Projet(int idProjet, String titre, String numeroDevis, LocalDate dateDevis, String numeroBC,
            LocalDate dateBC, int delaiExecution, LocalDate dateDebut, LocalDate dateFin, int avancement,
            String responsable, String statut) {
        this.idProjet = idProjet;
        this.titre = titre;
        this.numeroDevis = numeroDevis;
        this.dateDevis = dateDevis;
        this.numeroBC = numeroBC;
        this.dateBC = dateBC;
        this.delaiExecution = delaiExecution;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.avancement = avancement;
        this.responsable = responsable;
        Statut = statut;
    }

    
    public Projet(String titre, String numeroDevis, LocalDate dateDevis, String numeroBC, LocalDate dateBC,
            int delaiExecution, LocalDate dateDebut, LocalDate dateFin, int avancement, String responsable,
            String statut) {
        this.titre = titre;
        this.numeroDevis = numeroDevis;
        this.dateDevis = dateDevis;
        this.numeroBC = numeroBC;
        this.dateBC = dateBC;
        this.delaiExecution = delaiExecution;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.avancement = avancement;
        this.responsable = responsable;
        Statut = statut;
    }


    public int getIdProjet() {
        return idProjet;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    public String getNumeroDevis() {
        return numeroDevis;
    }
    public void setNumeroDevis(String numeroDevis) {
        this.numeroDevis = numeroDevis;
    }
    public LocalDate getDateDevis() {
        return dateDevis;
    }
    public void setDateDevis(LocalDate dateDevis) {
        this.dateDevis = dateDevis;
    }
    public String getNumeroBC() {
        return numeroBC;
    }
    public void setNumeroBC(String numeroBC) {
        this.numeroBC = numeroBC;
    }
    public LocalDate getDateBC() {
        return dateBC;
    }
    public void setDateBC(LocalDate dateBC) {
        this.dateBC = dateBC;
    }
    public int getDelaiExecution() {
        return delaiExecution;
    }
    public void setDelaiExecution(int delaiExecution) {
        this.delaiExecution = delaiExecution;
    }
    public LocalDate getDateDebut() {
        return dateDebut;
    }
    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }
    public LocalDate getDateFin() {
        return dateFin;
    }
    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }
    public int getAvancement() {
        return avancement;
    }
    public void setAvancement(int avancement) {
        this.avancement = avancement;
    }
    public String getResponsable() {
        return responsable;
    }
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getStatut() {
        return Statut;
    }

    public void setStatut(String Statut) {
        this.Statut = Statut;
    }

    
}
