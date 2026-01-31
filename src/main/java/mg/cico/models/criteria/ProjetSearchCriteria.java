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

    public String getKeyword() { return keyword; }
    public Integer getIdProjet() { return idProjet; }
    public LocalDate getDateDevisFrom() { return dateDevisFrom; }
    public LocalDate getDateDevisTo() { return dateDevisTo; }
    public LocalDate getDateBCFrom() { return dateBCFrom; }
    public LocalDate getDateBCTo() { return dateBCTo; }
    public LocalDate getDateDebutFrom() { return dateDebutFrom; }
    public LocalDate getDateDebutTo() { return dateDebutTo; }
    public LocalDate getDateFinFrom() { return dateFinFrom; }
    public LocalDate getDateFinTo() { return dateFinTo; }

    public ProjetSearchCriteria() {}

    public static class Builder {
        private final ProjetSearchCriteria criteria = new ProjetSearchCriteria();

        public Builder keyword(String keyword) { criteria.keyword = keyword; return this; }
        public Builder idProjet(Integer idProjet) { criteria.idProjet = idProjet; return this; }
        public Builder dateDevisFrom(LocalDate d) { criteria.dateDevisFrom = d; return this; }
        public Builder dateDevisTo(LocalDate d) { criteria.dateDevisTo = d; return this; }
        public Builder dateBCFrom(LocalDate d) { criteria.dateBCFrom = d; return this; }
        public Builder dateBCTo(LocalDate d) { criteria.dateBCTo = d; return this; }
        public Builder dateDebutFrom(LocalDate d) { criteria.dateDebutFrom = d; return this; }
        public Builder dateDebutTo(LocalDate d) { criteria.dateDebutTo = d; return this; }
        public Builder dateFinFrom(LocalDate d) { criteria.dateFinFrom = d; return this; }
        public Builder dateFinTo(LocalDate d) { criteria.dateFinTo = d; return this; }

        public ProjetSearchCriteria build() { return criteria; }
    }
}
