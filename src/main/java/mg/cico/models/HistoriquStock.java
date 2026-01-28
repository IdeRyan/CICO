package mg.cico.models;

import java.time.LocalDateTime;

import mg.cico.models.enums.TypeMvt;

public class HistoriquStock {
    private int idHistorique;
    private String refArticle;
    private int idLieu;
    private double quantite;
    private TypeMvt typeMouvement;
    private int idFiche;
    private LocalDateTime dateMouvement;
    
    public int getIdHistorique() {
        return idHistorique;
    }
    public void setIdHistorique(int idHistorique) {
        this.idHistorique = idHistorique;
    }
    public String getRefArticle() {
        return refArticle;
    }
    public void setRefArticle(String refArticle) {
        this.refArticle = refArticle;
    }
    public int getIdLieu() {
        return idLieu;
    }
    public void setIdLieu(int idLieu) {
        this.idLieu = idLieu;
    }
    public double getQuantite() {
        return quantite;
    }
    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }
    public TypeMvt getTypeMouvement() {
        return typeMouvement;
    }
    public void setTypeMouvement(TypeMvt typeMouvement) {
        this.typeMouvement = typeMouvement;
    }
    public int getIdFiche() {
        return idFiche;
    }
    public void setIdFiche(int idFiche) {
        this.idFiche = idFiche;
    }
    public LocalDateTime getDateMouvement() {
        return dateMouvement;
    }
    public void setDateMouvement(LocalDateTime dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
    
}
