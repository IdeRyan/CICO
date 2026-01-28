package mg.cico.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import mg.cico.models.enums.Statut;

public class Projet {
    private int idProjet;
    private String titre;
    private String lieu;
    private String numeroDevis;
    private LocalDate dateDevis;
    private String numeroBC;
    private LocalDate dateBC;
    private int delaiExecution;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int avancement;
    private String responsable;
    private Statut Statut;
    private LocalDateTime dernierMaj;
    
    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
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

    public Statut getStatut() {
        return Statut;
    }

    public void setStatut(Object Statut) {
        this.Statut = (Statut)Statut;
    }


    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public LocalDateTime getDernierMaj() {
        return dernierMaj;
    }

    public void setDernierMaj(LocalDateTime dernierMaj) {
        this.dernierMaj = dernierMaj;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    
}
