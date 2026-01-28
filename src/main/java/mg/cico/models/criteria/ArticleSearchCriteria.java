package mg.cico.models.criteria;

import java.time.LocalDate;

import mg.cico.models.enums.Etat;

public class ArticleSearchCriteria {
    
    private String keyword;

    private Etat etat;

    private LocalDate dateAchatFrom;
    private LocalDate dateAchatTo;

    private Double quantiteMin;
    private Double quantiteMax;

    private Integer idProjet;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public LocalDate getDateAchatFrom() {
        return dateAchatFrom;
    }

    public void setDateAchatFrom(LocalDate dateAchatFrom) {
        this.dateAchatFrom = dateAchatFrom;
    }

    public LocalDate getDateAchatTo() {
        return dateAchatTo;
    }

    public void setDateAchatTo(LocalDate dateAchatTo) {
        this.dateAchatTo = dateAchatTo;
    }

    public Double getQuantiteMin() {
        return quantiteMin;
    }

    public void setQuantiteMin(Double quantiteMin) {
        this.quantiteMin = quantiteMin;
    }

    public Double getQuantiteMax() {
        return quantiteMax;
    }

    public void setQuantiteMax(Double quantiteMax) {
        this.quantiteMax = quantiteMax;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    
}
