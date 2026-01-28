package mg.cico.models;

import java.time.LocalDate;

import mg.cico.models.enums.Etat;

public class Article {
    private String reference;
    private String designation;
    private String unite;
    private Etat etat;
    private LocalDate dateAchat;
    
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getDesignation() {
        return designation;
    }
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    public String getUnite() {
        return unite;
    }
    public void setUnite(String unite) {
        this.unite = unite;
    }
    public Etat getEtat() {
        return etat;
    }
    public void setEtat(Object etat) {
        this.etat = (Etat)etat;
    }
    public LocalDate getDateAchat() {
        return dateAchat;
    }
    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }
    
}
