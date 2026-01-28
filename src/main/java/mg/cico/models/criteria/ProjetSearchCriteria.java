package mg.cico.models.criteria;

import java.time.LocalDate;

public class ProjetSearchCriteria {
    private String keyword;
    private Integer idProjet;
    private LocalDate dateDevisFrom;
    private LocalDate dateDevisTo;
    private LocalDate dateBCFrom;
    private LocalDate dateBCTo;
    private LocalDate dateDebutFrom;
    private LocalDate dateDebutTo;
    private LocalDate dateFinFrom;
    private LocalDate dateFinTo;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(Integer idProjet) {
        this.idProjet = idProjet;
    }

    public LocalDate getDateDevisFrom() {
        return dateDevisFrom;
    }

    public void setDateDevisFrom(LocalDate dateDevisFrom) {
        this.dateDevisFrom = dateDevisFrom;
    }

    public LocalDate getDateDevisTo() {
        return dateDevisTo;
    }

    public void setDateDevisTo(LocalDate dateDevisTo) {
        this.dateDevisTo = dateDevisTo;
    }

    public LocalDate getDateBCFrom() {
        return dateBCFrom;
    }

    public void setDateBCFrom(LocalDate dateBCFrom) {
        this.dateBCFrom = dateBCFrom;
    }

    public LocalDate getDateBCTo() {
        return dateBCTo;
    }

    public void setDateBCTo(LocalDate dateBCTo) {
        this.dateBCTo = dateBCTo;
    }

    public LocalDate getDateDebutFrom() {
        return dateDebutFrom;
    }

    public void setDateDebutFrom(LocalDate dateDebutFrom) {
        this.dateDebutFrom = dateDebutFrom;
    }

    public LocalDate getDateDebutTo() {
        return dateDebutTo;
    }

    public void setDateDebutTo(LocalDate dateDebutTo) {
        this.dateDebutTo = dateDebutTo;
    }

    public LocalDate getDateFinFrom() {
        return dateFinFrom;
    }

    public void setDateFinFrom(LocalDate dateFinFrom) {
        this.dateFinFrom = dateFinFrom;
    }

    public LocalDate getDateFinTo() {
        return dateFinTo;
    }

    public void setDateFinTo(LocalDate dateFinTo) {
        this.dateFinTo = dateFinTo;
    }
    
    
}
