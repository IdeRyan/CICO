package mg.cico.models;

import java.time.LocalDateTime;

public class LigneMouvement {
    private int idLigne;
    private int idFiche;
    private String refArcticle;
    private double quantite;
    private int idLieuSource;
    private int idLieuDestination;
    private int idEmetteur;
    private int idDestinataire;
    private String observation;
    private LocalDateTime dateMouvement;
    
    public int getIdLigne() {
        return idLigne;
    }
    public void setIdLigne(int idLigne) {
        this.idLigne = idLigne;
    }
    public int getIdFiche() {
        return idFiche;
    }
    public void setIdFiche(int idFiche) {
        this.idFiche = idFiche;
    }
    public String getRefArcticle() {
        return refArcticle;
    }
    public void setRefArcticle(String refArcticle) {
        this.refArcticle = refArcticle;
    }
    public double getQuantite() {
        return quantite;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public int getIdLieuSource() {
        return idLieuSource;
    }
    public void setIdLieuSource(int idLieuSource) {
        this.idLieuSource = idLieuSource;
    }
    public int getIdLieuDestination() {
        return idLieuDestination;
    }
    public void setIdLieuDestination(int idLieuDestination) {
        this.idLieuDestination = idLieuDestination;
    }
    public int getIdEmetteur() {
        return idEmetteur;
    }
    public void setIdEmetteur(int idEmetteur) {
        this.idEmetteur = idEmetteur;
    }
    public int getIdDestinataire() {
        return idDestinataire;
    }
    public void setIdDestinataire(int idDestinataire) {
        this.idDestinataire = idDestinataire;
    }
    public String getObservation() {
        return observation;
    }
    public void setObservation(String observation) {
        this.observation = observation;
    }
    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }
    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }

    
}
